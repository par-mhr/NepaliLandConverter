# Nepali Land Converter

A cross-platform JavaFX application for converting between different land measurement units commonly used in Nepal.

## Features

**11 Different Land Measurement Units**
- Square Feet
- Square Meter
- Ropani-Aana-Paisa-Daam
- Ropani
- Aana
- Paisa
- Daam
- Bigha-Kattha-Dhur
- Bigha
- Kattha
- Dhur

**Multi-Platform Support**
- macOS 
- Windows (
- Linux (Debian-based distributions)

**User-Friendly Interface**
- Light and Dark theme support (follows system preference)
- Real-time conversions
- Copy results to clipboard with a single click
- Non-resizable window with fixed dimensions and always on top checkbox

**Developer-Friendly**
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
git clone https://github.com/par-mhr/NepaliLandConverter.git
cd NepaliLandConverter
./build-standalone-macos.sh
```

### Windows

1. **Download** the latest `.msi` installer from [Releases](https://github.com/parasmahajan10/NepaliLandConverter/releases)
2. **Run** the installer and follow the setup wizard
3. **Launch** from Start Menu → Nepali Land Converter

Or build from source:
```bash
git clone https://github.com/par-mhr/NepaliLandConverter.git
cd NepaliLandConverter
./build-windows.sh
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
git clone https://github.com/par-mhr/NepaliLandConverter.git
cd NepaliLandConverter
./build-linux.sh
```

## Building from Source

### Prerequisites

- Java 25 or later (OpenJDK recommended)
- Maven 3.6.0 or later
- Git

### Build Instructions

1. **Clone the repository**
```bash
git clone https://github.com/par-mhr/NepaliLandConverter.git
cd NepaliLandConverter
```

2. **Build the application**
```bash

#### macOS (requires macOS system)
```bash
#./build-standalone-macos.sh
```

#### Windows (requires Windows system)
```bash
./build-windows.sh
```

#### Linux (requires Linux system)
```bash
./build-linux.sh
```

## Usage

1. **Select Conversion Type**: Choose from the left panel
2. **Enter Values**: Input the area values in the selected unit
3. **View Results**: Results appear in the right panel in all other units
4. **Copy Results**: Click the green copy button to copy any result to clipboard
5. **Change Theme**: Use the theme toggle to switch between light and dark mode
6. **Always On Top**: Check the checkbox to keep the window on top of other windows
7. **About**: Click the info button for application details


## Development

### Technologies Used

- **JavaFX 25.0.2** - UI Framework
- **JFoenix 9.0.10** - Material Design components
- **Maven 3.6+** - Build tool
- **Java 25.0.2** - Programming language

### Code Style

- Follow Google Java Style Guide
- Use meaningful variable names
- Add comments for complex logic
- Keep methods focused and small


## Contributing

Contributions are welcome! The UI is still a rough draft and can be significantly improved. Please feel free to suggest enhancements or submit pull requests.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

**Paras Maharjan**
- Email: paras.maharjan10@gmail.com/paras.maharjan@live.com
- GitHub: [@par-mhr](https://github.com/par-mhr)

## Acknowledgments

- Inspired by the original "Soft Area Converter" app
- Thanks to the JavaFX and OpenJDK communities
- Icons from Icons8

## Support

For bugs, feature requests, or questions:
- Open an issue on GitHub
- Email: paras.maharjan10@gmail.com/paras.maharjan@live.com

---

**Version**: 1.0  
**Release Date**: April 2026  


