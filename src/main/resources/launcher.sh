#!/bin/bash

# Get the directory where this script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
APP_DIR="$( dirname "$DIR" )"
JAR="$APP_DIR/nepali-land-converter-1.0-all.jar"

# Find Java in the standard locations
if [ -z "$JAVA_HOME" ]; then
    JAVA=$(/usr/libexec/java_home -v 20 2>/dev/null || which java)
else
    JAVA="$JAVA_HOME/bin/java"
fi

if [ -z "$JAVA" ] || [ ! -f "$JAVA" ]; then
    echo "Error: Java not found. Please install Java 20 or later."
    exit 1
fi

# Run the application with proper settings
exec "$JAVA" \
    -Xdock:name="Nepali Land Converter" \
    -Xdock:icon="$APP_DIR/icon.png" \
    -Xmx512m \
    -Dapple.laf.useScreenMenuBar=true \
    -Dcom.apple.mrj.application.apple.menu.about.name="Nepali Land Converter" \
    -Dapple.awt.application.name="Nepali Land Converter" \
    --add-modules javafx.controls,javafx.fxml \
    --module-path /Library/Java/JavaVirtualMachines/openjdk-20.0.1/Contents/Home/lib \
    -cp "$JAR" \
    com.nepalilandconverter.MainApplication "$@"

