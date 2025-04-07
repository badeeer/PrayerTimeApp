# Prayer Time Notification App

A mobile application that provides prayer time notifications for cities in Morocco. The app fetches data from the Morocco Prayer Times API and displays prayer times to users while also sending notifications for upcoming prayers.

## Features

- View prayer times for all cities in Morocco
- Receive notifications before prayer times
- Offline support with local caching
- Customizable notification settings
- Clean and intuitive user interface

## Screenshots

(Screenshots will be added here)

## Technical Details

### Data Source
The app uses the [Morocco Prayer Times API](https://github.com/ZakariaMahmoud/Morocco-Prayer-Times-API) which provides:
- List of Moroccan cities with IDs
- Prayer times for specific dates
- Current day prayer times

### Architecture
The app follows a clean architecture approach with:
- Repository pattern for data management
- MVVM architecture for UI components
- Service layer for API communication
- Local caching for offline support

### Libraries Used
- Retrofit for API communication
- Room for local database storage
- AndroidX components for UI
- WorkManager for background tasks

## Installation

1. Download the APK file from the releases section
2. Enable installation from unknown sources in your device settings
3. Install the APK file
4. Open the app and select your city

## Building from Source

1. Clone this repository
2. Open the project in Android Studio
3. Build and run the project

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [Morocco Prayer Times API](https://github.com/ZakariaMahmoud/Morocco-Prayer-Times-API) for providing the prayer time data
- Ministry of Endowments and Islamic Affairs in Morocco (habous.gov.ma) for the original data source
