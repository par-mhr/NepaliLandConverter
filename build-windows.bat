@echo off
REM Windows Build Script for Nepali Land Converter
REM This script builds and packages the application for Windows

echo.
echo ========================================
echo Nepali Land Converter - Windows Build
echo ========================================
echo.

REM Check if Maven is available
where mvnw >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven wrapper not found!
    echo Please ensure you are in the project root directory.
    exit /b 1
)

REM Clean and build
echo Building application...
call mvnw clean package -DskipTests

if %errorlevel% neq 0 (
    echo Error: Build failed!
    exit /b 1
)

echo.
echo ========================================
echo Build Complete!
echo ========================================
echo.
echo The application has been packaged successfully.
echo.
echo To run the application:
echo   mvnw javafx:run
echo.
echo To create a Windows installer (.msi):
echo   mvnw jpackage:jpackage
echo.
pause

