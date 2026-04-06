#!/bin/bash

# Nepali Land Converter - Linux Build Script
# Creates standalone Linux packages (DEB and AppImage) using jpackage

set -e

SCRIPT_DIR="$( cd "$(dirname "$0")" && pwd )"
PROJECT_DIR="$SCRIPT_DIR"

echo "======================================"
echo "Building Nepali Land Converter for Linux"
echo "======================================"

# Check if JAVA_HOME is set
if [ -z "$JAVA_HOME" ]; then
    echo "Looking for Java 25..."
    JAVA_HOME=$(update-alternatives --list java 2>/dev/null | grep 25 | head -1) || true
    if [ -z "$JAVA_HOME" ]; then
        JAVA_HOME=$(/usr/libexec/java_home -v 25 2>/dev/null) || true
    fi

    if [ -z "$JAVA_HOME" ]; then
        echo "Error: Java 25 not found"
        echo "Please install Java 25 or set JAVA_HOME"
        exit 1
    fi
fi

echo "Using Java: $JAVA_HOME"
"$JAVA_HOME/bin/java" -version

# Step 1: Clean and build Maven project
echo ""
echo "Step 1: Building Maven project..."
cd "$PROJECT_DIR"
./mvnw clean package -DskipTests -q

# Step 2: Create output directory
echo "Step 2: Setting up build directories..."
mkdir -p "$PROJECT_DIR/build-output"

# Step 3: Create a custom JDK runtime using jlink
echo "Step 3: Creating custom JDK runtime..."
RUNTIME_DIR="$PROJECT_DIR/target/custom-jdk-linux"
rm -rf "$RUNTIME_DIR"

"$JAVA_HOME/bin/jlink" \
    --module-path "$JAVA_HOME/jmods" \
    --add-modules java.base,java.logging,java.desktop,java.xml,java.prefs,java.instrument,jdk.unsupported,jdk.jfr \
    --output "$RUNTIME_DIR" \
    --strip-debug \
    --compress=2 \
    --no-header-files \
    --no-man-pages

echo "Runtime created at: $RUNTIME_DIR"

# Step 4: Copy JAR to staging directory
echo "Step 4: Preparing JAR for packaging..."
STAGING_DIR="$PROJECT_DIR/target/staging-jars"
rm -rf "$STAGING_DIR"
mkdir -p "$STAGING_DIR"
cp "$PROJECT_DIR/target/nepali-land-converter-1.0-all.jar" "$STAGING_DIR/"

# Step 5: Create DEB package (Debian/Ubuntu)
echo "Step 5: Creating Debian DEB package..."
"$JAVA_HOME/bin/jpackage" \
    --input "$STAGING_DIR" \
    --name nepali-land-converter \
    --main-jar nepali-land-converter-1.0-all.jar \
    --main-class com.nepalilandconverter.MainApplication \
    --type deb \
    --app-version 1.0 \
    --java-options "-Xmx512m" \
    --icon "$PROJECT_DIR/src/main/resources/images/icon.png" \
    --vendor "Nepali Land Converter" \
    --linux-menu-group "Utilities" \
    --dest "$PROJECT_DIR/build-output"

echo "DEB package created!"

# Step 6: Create RPM package (Fedora/RHEL)
# Only if rpmbuild is available
if command -v rpmbuild &> /dev/null; then
    echo "Step 6: Creating RPM package..."
    "$JAVA_HOME/bin/jpackage" \
        --input "$STAGING_DIR" \
        --name nepali-land-converter \
        --main-jar nepali-land-converter-1.0-all.jar \
        --main-class com.nepalilandconverter.MainApplication \
        --type rpm \
        --app-version 1.0 \
        --java-options "-Xmx512m" \
        --icon "$PROJECT_DIR/src/main/resources/images/icon.png" \
        --vendor "Nepali Land Converter" \
        --linux-menu-group "Utilities" \
        --dest "$PROJECT_DIR/build-output"

    echo "RPM package created!"
else
    echo "Step 6: RPM build tools not found, skipping RPM package"
fi

echo ""
echo "======================================"
echo "Build Completed Successfully!"
echo "======================================"
echo ""
echo "Deliverables:"
echo "  DEB Package: $PROJECT_DIR/build-output/nepali-land-converter-1.0_amd64.deb"
if command -v rpmbuild &> /dev/null; then
    echo "  RPM Package: $PROJECT_DIR/build-output/nepali-land-converter-1.0-1.x86_64.rpm"
fi
echo ""
echo "Installation Instructions:"
echo ""
echo "For Debian/Ubuntu:"
echo "  sudo dpkg -i nepali-land-converter-1.0_amd64.deb"
echo "  OR"
echo "  sudo apt install ./nepali-land-converter-1.0_amd64.deb"
echo ""
if command -v rpmbuild &> /dev/null; then
    echo "For Fedora/RHEL:"
    echo "  sudo rpm -i nepali-land-converter-1.0-1.x86_64.rpm"
    echo "  OR"
    echo "  sudo dnf install ./nepali-land-converter-1.0-1.x86_64.rpm"
    echo ""
fi
echo "Then launch:"
echo "  nepali-land-converter"
echo "  OR"
echo "  Applications > Utilities > Nepali Land Converter"
echo ""

# Cleanup
echo "Cleaning up temporary files..."
rm -rf "$STAGING_DIR" "$RUNTIME_DIR"

echo "Done!"
echo "  ./mvnw jpackage:jpackage"
echo ""
echo "To install the package:"
echo "  sudo apt install ./nepali-land-converter_1.0_amd64.deb"
echo ""

