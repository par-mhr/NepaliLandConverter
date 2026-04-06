# Nepali Land Converter

A cross-platform JavaFX application for converting between different land measurement units commonly used in Nepal.

## Features

✅ **11 Different Land Measurement Units**
- Square Feet
- Ropani
- Kattha
- Dhur
- Bigha
- Aana
- Paisa
- Daam
- Hectare
- Square Meter
- Acre

✅ **Multi-Platform Support**
- macOS (native app bundle)
- Windows (installer)
- Linux (Debian-based distributions)

✅ **User-Friendly Interface**
- Light and Dark theme support (follows system preference)
- Real-time conversions
- Copy results to clipboard with a single click
- Non-resizable window with fixed dimensions

✅ **Developer-Friendly**
- Open source (MIT License)
- Clean JavaFX codebase
- Maven-based build system
- Cross-platform compatibility

## System Requirements

### macOS
- macOS 10.12 or later
- Apple Silicon (M1/M2) or Intel processor

### Windows
- Windows 7 or later
- 64-bit system

### Linux (Debian)
- Ubuntu 18.04 or later
- Debian 10 or later

## Installation

### macOS

1. **Download** the latest `.dmg` installer from [Releases](https://github.com/parasmahajan10/NepaliLandConverter/releases)
2. **Open** the DMG file and drag the app to Applications folder
3. **Launch** from Applications or Spotlight (Cmd+Space, type "Nepali Land Converter")

Or build from source:
```bash
git clone https://github.com/parasmahajan10/NepaliLandConverter.git
cd NepaliLandConverter
./run-macos.sh
```

### Windows

1. **Download** the latest `.msi` installer from [Releases](https://github.com/parasmahajan10/NepaliLandConverter/releases)
2. **Run** the installer and follow the setup wizard
3. **Launch** from Start Menu → Nepali Land Converter

Or build from source:
```bash
git clone https://github.com/parasmahajan10/NepaliLandConverter.git
cd NepaliLandConverter
mvnw javafx:run
```

### Linux (Debian/Ubuntu)

1. **Download** the latest `.deb` package from [Releases](https://github.com/parasmahajan10/NepaliLandConverter/releases)
2. **Install** the package:
```bash
sudo apt install ./nepali-land-converter_1.0_amd64.deb
```
3. **Launch** from Applications menu or terminal:
```bash
nepali-land-converter
```

Or build from source:
```bash
git clone https://github.com/parasmahajan10/NepaliLandConverter.git
cd NepaliLandConverter
./mvnw javafx:run
```

## Building from Source

### Prerequisites

- Java 20 or later (OpenJDK recommended)
- Maven 3.6.0 or later
- Git

### Build Instructions

1. **Clone the repository**
```bash
git clone https://github.com/parasmahajan10/NepaliLandConverter.git
cd NepaliLandConverter
```

2. **Build the application**
```bash
./mvnw clean package
```

3. **Run the application**
```bash
./mvnw javafx:run
```

### Creating Installers

#### macOS (requires macOS system)
```bash
# Create DMG installer
./mvnw clean javafx:run
# Or use the prepared script
./run-macos.sh
```

#### Windows (requires Windows system)
```bash
# Create MSI installer
mvnw clean package
```

#### Linux (requires Linux system)
```bash
# Create DEB package
./mvnw clean package
```

## Usage

1. **Select Conversion Type**: Choose from the left panel
2. **Enter Values**: Input the area values in the selected unit
3. **View Results**: Results appear in the right panel in all other units
4. **Copy Results**: Click the green copy button to copy any result to clipboard
5. **Change Theme**: Use the theme toggle to switch between light and dark mode
6. **Always On Top**: Check the checkbox to keep the window on top of other windows
7. **About**: Click the info button for application details

## Conversion Formula

All conversions are based on the following relationships:

- 1 Kattha = 1800 Square Feet
- 1 Ropani = 5292 Square Feet
- 1 Bigha = 14520 Square Feet
- 1 Hectare = 107639.1 Square Feet
- 1 Acre = 43560 Square Feet
- 1 Square Meter = 10.764 Square Feet

## Project Structure

```
NepaliLandConverter/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/nepalilandconverter/
│       │       ├── MainApplication.java      # Entry point
│       │       ├── MainController.java       # UI controller
│       │       ├── AreaCalculator.java       # Conversion logic
│       │       ├── ThemeManager.java         # Theme management
│       │       └── AboutWindow.java          # About dialog
│       └── resources/
│           ├── com/nepalilandconverter/
│           │   ├── main-view.fxml           # Main UI layout
│           │   ├── style.css                # Base styles
│           │   ├── light-theme.css          # Light theme
│           │   └── dark-theme.css           # Dark theme
│           └── images/
│               ├── icon.png                 # App icon
│               ├── icon.icns                # macOS icon
│               └── copy_white.png           # Copy button icon
├── pom.xml                                   # Maven configuration
├── run-macos.sh                             # macOS run script
└── create-icon.sh                           # Icon creation script
```

## Development

### Technologies Used

- **JavaFX 20.0.1** - UI Framework
- **JFoenix 9.0.10** - Material Design components
- **Maven 3.6+** - Build tool
- **Java 20** - Programming language

### Code Style

- Follow Google Java Style Guide
- Use meaningful variable names
- Add comments for complex logic
- Keep methods focused and small

### Testing

```bash
# Run tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report
```

## Troubleshooting

### App won't start on macOS
- Check Java 20+ is installed: `java -version`
- Try running: `./run-macos.sh`

### Conversions seem incorrect
- Ensure all input values are valid numbers
- Check decimal points are correct
- Verify the input unit is selected

### Theme not working
- Restart the application
- Check system theme settings

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

**Paras Maharjan**
- Email: paras.maharjan10@gmail.com
- GitHub: [@par-mhr](https://github.com/par-mhr)

## Acknowledgments

- Inspired by the original "Soft Area Converter" app
- Thanks to the JavaFX and OpenJDK communities
- Icons from Icons8

## Support

For bugs, feature requests, or questions:
- Open an issue on GitHub
- Email: paras.maharjan10@gmail.com

---

**Version**: 1.0  
**Release Date**: April 2026  
**Status**: Production Ready ✅

