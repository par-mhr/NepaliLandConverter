#!/bin/sh
# Maven Wrapper Script for Nepali Land Converter
BASE_DIR=$(dirname "$0")
cd "$BASE_DIR" || exit 1
# Simple wrapper that uses system maven
if command -v mvn &> /dev/null; then
    mvn "$@"
else
    echo "Error: Maven is not installed!"
    echo "Please install Maven from https://maven.apache.org/install.html"
    echo "Or run: brew install maven (on macOS)"
    exit 1
fi
