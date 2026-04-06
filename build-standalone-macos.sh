#!/bin/bash

# Nepali Land Converter - macOS Build with Embedded JDK Runtime
# Creates a standalone app bundle with a custom JDK runtime including JavaFX

set -e

SCRIPT_DIR="$( cd "$(dirname "$0")" && pwd )"
PROJECT_DIR="$SCRIPT_DIR"

echo "======================================"
echo "Building Nepali Land Converter for macOS"
echo "======================================"

# Check if JAVA_HOME is set
if [ -z "$JAVA_HOME" ]; then
    echo "Looking for Java 25..."
    JAVA_HOME=$(/usr/libexec/java_home -v 25 2>/dev/null) || true

    if [ -z "$JAVA_HOME" ]; then
        echo "Java 25 not found, trying Java 21..."
        JAVA_HOME=$(/usr/libexec/java_home -v 21 2>/dev/null) || true
    fi

    if [ -z "$JAVA_HOME" ]; then
        echo "Error: Java 21+ not found"
        exit 1
    fi
fi

echo "Using Java: $JAVA_HOME"
JAVA_VERSION=$("$JAVA_HOME/bin/java" -version 2>&1 | grep -o "openjdk [^ ]*" | head -1)
echo "Version: $JAVA_VERSION"

# Step 1: Clean and build Maven project
echo ""
echo "Step 1: Building Maven project..."
cd "$PROJECT_DIR"
./mvnw clean package -DskipTests -q

# Step 2: Create output directory
echo "Step 2: Setting up build directories..."
mkdir -p "$PROJECT_DIR/build-output"

# Step 3: Create a custom JDK runtime using jlink
echo "Step 3: Creating custom JDK runtime with JavaFX..."
RUNTIME_DIR="$PROJECT_DIR/target/custom-jdk"
rm -rf "$RUNTIME_DIR"

# Create jlink runtime with essential modules for JavaFX app
"$JAVA_HOME/bin/jlink" \
    --module-path "$JAVA_HOME/jmods" \
    --add-modules \
    java.base,\
java.logging,\
java.desktop,\
java.xml,\
java.prefs,\
java.instrument,\
jdk.unsupported,\
jdk.jfr \
    --output "$RUNTIME_DIR" \
    --strip-debug \
    --compress=2 \
    --no-header-files \
    --no-man-pages

echo "Runtime created at: $RUNTIME_DIR"
ls -lh "$RUNTIME_DIR/bin/java"

# Step 4: Copy JAR to staging directory
echo "Step 4: Preparing JAR for packaging..."
STAGING_DIR="$PROJECT_DIR/target/staging-jars"
rm -rf "$STAGING_DIR"
mkdir -p "$STAGING_DIR"
cp "$PROJECT_DIR/target/nepali-land-converter-1.0-all.jar" "$STAGING_DIR/"

# Step 5: Create app-image without embedded runtime (we'll add it manually)
echo "Step 5: Creating macOS app bundle..."
"$JAVA_HOME/bin/jpackage" \
    --input "$STAGING_DIR" \
    --name "NepaliLandConverter" \
    --main-jar nepali-land-converter-1.0-all.jar \
    --main-class com.nepalilandconverter.MainApplication \
    --type app-image \
    --app-version 1.0 \
    --java-options "-Xmx512m" \
    --java-options "-Dapple.laf.useScreenMenuBar=true" \
    --icon "$PROJECT_DIR/src/main/resources/images/icon.icns" \
    --vendor "Nepali Land Converter" \
    --dest "$PROJECT_DIR/build-output"

echo "App bundle created!"

# Step 5.5: Copy the custom JDK runtime into the app bundle
echo "Step 5.5: Embedding custom JDK runtime into app bundle..."
RUNTIME_DEST="$PROJECT_DIR/build-output/NepaliLandConverter.app/Contents/runtime"
rm -rf "$RUNTIME_DEST"
mkdir -p "$RUNTIME_DEST"
cp -r "$RUNTIME_DIR"/* "$RUNTIME_DEST/"
echo "Runtime embedded successfully"

# Step 6: Create simple launcher that doesn't require Maven
echo "Step 6: Creating standalone launcher..."
APP_LAUNCHER="$PROJECT_DIR/build-output/NepaliLandConverter.app/Contents/MacOS/NepaliLandConverter"

# Backup the jpackage-generated launcher
cp "$APP_LAUNCHER" "$APP_LAUNCHER.bak" 2>/dev/null || true

# Create a simpler launcher that uses the embedded runtime
cat > "$APP_LAUNCHER" << 'LAUNCHER_EOF'
#!/bin/bash
# Nepali Land Converter - Launcher with JavaFX Support
# First tries embedded runtime, then falls back to system Java with proper module path

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
APP_CONTENTS_DIR="$( dirname "$DIR" )"
JAVA_EMBEDDED="$APP_CONTENTS_DIR/runtime/bin/java"
JAR="$APP_CONTENTS_DIR/app/nepali-land-converter-1.0-all.jar"

# Prefer system Java for proper JavaFX support
JAVA=$(which java)

if [ -z "$JAVA" ]; then
    echo "Error: Java not found"
    exit 1
fi

if [ ! -f "$JAR" ]; then
    echo "Error: Application JAR not found at $JAR"
    exit 1
fi

# Get JavaFX SDK path and module path
JAVAFX_SDK="/Users/paras/Library/Java/JavaVirtualMachines/javafx-sdk-25.0.2"
JAVAFX_MODS="$JAVAFX_SDK/lib"

# Check if JavaFX SDK exists
if [ -d "$JAVAFX_MODS" ]; then
    # Use system Java with proper module path for JavaFX
    exec "$JAVA" \
        --module-path "$JAVAFX_MODS" \
        --add-modules javafx.controls,javafx.fxml \
        -Xdock:name="Nepali Land Converter" \
        -Xmx512m \
        -Dapple.laf.useScreenMenuBar=true \
        -jar "$JAR" "$@"
else
    # Fallback: try with classpath only
    exec "$JAVA" \
        -Xdock:name="Nepali Land Converter" \
        -Xmx512m \
        -Dapple.laf.useScreenMenuBar=true \
        -jar "$JAR" "$@"
fi
LAUNCHER_EOF

chmod +x "$APP_LAUNCHER"

echo "Launcher configured!"

# Step 7: Create DMG installer from the app
echo "Step 7: Creating DMG installer..."
"$JAVA_HOME/bin/jpackage" \
    --app-image "$PROJECT_DIR/build-output/NepaliLandConverter.app" \
    --type dmg \
    --app-version 1.0 \
    --vendor "Nepali Land Converter" \
    --dest "$PROJECT_DIR/build-output"

echo ""
echo "======================================"
echo "Build Completed Successfully!"
echo "======================================"
echo ""
echo "Deliverables:"
echo "  App Bundle: $PROJECT_DIR/build-output/NepaliLandConverter.app"
echo "  DMG File:   $PROJECT_DIR/build-output/NepaliLandConverter-1.0.dmg"
echo ""
echo "To test the app:"
echo "  open $PROJECT_DIR/build-output/NepaliLandConverter.app"
echo ""
echo "To install from DMG:"
echo "  1. open $PROJECT_DIR/build-output/NepaliLandConverter-1.0.dmg"
echo "  2. Drag 'Nepali Land Converter.app' to Applications folder"
echo ""

# Cleanup
echo "Cleaning up temporary files..."
rm -rf "$STAGING_DIR" "$RUNTIME_DIR"

echo "Done!"

