#!/bin/bash

# Nepali Land Converter - Windows Build Script
# Creates a standalone Windows executable installer using jpackage

set -e

SCRIPT_DIR="$( cd "$(dirname "$0")" && pwd )"
PROJECT_DIR="$SCRIPT_DIR"

echo "======================================"
echo "Building Nepali Land Converter for Windows"
echo "======================================"

# Check if running on Windows (Git Bash, WSL, or MSYS2)
if [[ "$OSTYPE" != "msys" && "$OSTYPE" != "win32" && "$OSTYPE" != "cygwin" ]]; then
    echo "Note: This script is designed to run on Windows or WSL"
    echo "If running on macOS/Linux, install on Windows and run this script there"
fi

# Check if JAVA_HOME is set
if [ -z "$JAVA_HOME" ]; then
    echo "Looking for Java 25..."
    # On Windows, Java might be in Program Files
    if [ -d "C:/Program Files/Java/jdk-25" ]; then
        JAVA_HOME="C:/Program Files/Java/jdk-25"
    elif command -v java &> /dev/null; then
        JAVA_HOME=$(dirname $(dirname $(command -v java)))
    else
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
RUNTIME_DIR="$PROJECT_DIR/target/custom-jdk-windows"
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

# Step 5: Create Windows EXE installer
echo "Step 5: Creating Windows EXE installer..."
"$JAVA_HOME/bin/jpackage" \
    --input "$STAGING_DIR" \
    --name "NepaliLandConverter" \
    --main-jar nepali-land-converter-1.0-all.jar \
    --main-class com.nepalilandconverter.MainApplication \
    --type exe \
    --app-version 1.0 \
    --java-options "-Xmx512m" \
    --icon "$PROJECT_DIR/src/main/resources/images/icon.png" \
    --vendor "Nepali Land Converter" \
    --dest "$PROJECT_DIR/build-output"

echo "EXE installer created!"

# Step 6: Also create MSI installer (if on Windows)
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" || "$OSTYPE" == "cygwin" ]]; then
    echo "Step 6: Creating Windows MSI installer..."
    "$JAVA_HOME/bin/jpackage" \
        --input "$STAGING_DIR" \
        --name "NepaliLandConverter" \
        --main-jar nepali-land-converter-1.0-all.jar \
        --main-class com.nepalilandconverter.MainApplication \
        --type msi \
        --app-version 1.0 \
        --java-options "-Xmx512m" \
        --icon "$PROJECT_DIR/src/main/resources/images/icon.png" \
        --vendor "Nepali Land Converter" \
        --dest "$PROJECT_DIR/build-output"

    echo "MSI installer created!"
fi

echo ""
echo "======================================"
echo "Build Completed Successfully!"
echo "======================================"
echo ""
echo "Deliverables:"
echo "  EXE Installer: $PROJECT_DIR/build-output/NepaliLandConverter-1.0.exe"
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" || "$OSTYPE" == "cygwin" ]]; then
    echo "  MSI Installer: $PROJECT_DIR/build-output/NepaliLandConverter-1.0.msi"
fi
echo ""
echo "To install:"
echo "  1. Download the EXE file"
echo "  2. Run the installer"
echo "  3. Follow the installation wizard"
echo ""

# Cleanup
echo "Cleaning up temporary files..."
rm -rf "$STAGING_DIR" "$RUNTIME_DIR"

echo "Done!"

