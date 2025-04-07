# Developer Documentation

## Project Structure

The Prayer Time Notification App follows a standard Android project structure:

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/prayertime/app/
│   │   │   ├── adapter/
│   │   │   │   └── PrayerTimeAdapter.java
│   │   │   ├── api/
│   │   │   │   └── ApiService.java
│   │   │   ├── model/
│   │   │   │   ├── City.java
│   │   │   │   └── PrayerTime.java
│   │   │   ├── receiver/
│   │   │   │   ├── BootReceiver.java
│   │   │   │   └── NotificationReceiver.java
│   │   │   ├── repository/
│   │   │   │   └── PrayerTimeRepository.java
│   │   │   ├── util/
│   │   │   │   └── NotificationUtil.java
│   │   │   ├── MainActivity.java
│   │   │   ├── CitySelectionActivity.java
│   │   │   ├── PrayerTimesActivity.java
│   │   │   └── SettingsActivity.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml
│   │   │   │   └── item_prayer_time.xml
│   │   │   └── values/
│   │   │       ├── colors.xml
│   │   │       └── strings.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle
└── build.gradle
```

## Architecture

The app follows the Repository pattern with a clean architecture approach:

1. **UI Layer**: Activities and Adapters
2. **Data Layer**: Repository and API Service
3. **Model Layer**: Data models (City, PrayerTime)
4. **Utility Layer**: Helper classes and receivers

## Key Components

### Data Models
- `City.java`: Represents a city in Morocco with ID and names
- `PrayerTime.java`: Contains prayer times for a specific day

### API Integration
- `ApiService.java`: Retrofit interface for the Morocco Prayer Times API
- `PrayerTimeRepository.java`: Manages data operations and caching

### Notification System
- `NotificationUtil.java`: Schedules and manages prayer time notifications
- `NotificationReceiver.java`: Handles displaying notifications
- `BootReceiver.java`: Reschedules notifications after device reboot

### UI Components
- `MainActivity.java`: Main entry point showing prayer times
- `PrayerTimeAdapter.java`: Displays prayer times in a RecyclerView

## Building the Project

### Prerequisites
- Android Studio 4.0+
- JDK 8+
- Android SDK 21+

### Build Steps
1. Open the project in Android Studio
2. Sync Gradle files
3. Build the project (Build > Make Project)
4. Run on emulator or device (Run > Run 'app')

### Creating a Release APK
1. In Android Studio, select Build > Generate Signed Bundle/APK
2. Choose APK and click Next
3. Create or select a keystore file
4. Enter keystore and key details
5. Select release build type
6. Click Finish

## API Documentation

The app uses the Morocco Prayer Times API with the following endpoints:

- `/api/cities`: Get all cities
- `/api/cities/:id`: Get specific city by ID
- `/api/:city/:year/:month/:day`: Get prayer times for specific date
- `/api/:city/today`: Get prayer times for current day

## Adding Features

### Adding a New Screen
1. Create a new Activity class
2. Create a corresponding layout XML file
3. Add the activity to AndroidManifest.xml
4. Add navigation to the new activity

### Modifying Prayer Time Display
1. Update the PrayerTimeAdapter class
2. Modify the item_prayer_time.xml layout

### Changing Notification Behavior
1. Update the NotificationUtil class
2. Modify the NotificationReceiver class

## Testing

### Unit Tests
Create tests in the `src/test/` directory for:
- Repository logic
- Data parsing
- Utility functions

### UI Tests
Create tests in the `src/androidTest/` directory for:
- Activity navigation
- UI interactions
- End-to-end flows

## Troubleshooting

### Common Build Issues
- Gradle sync failures: Update Gradle and Android Gradle Plugin
- Dependency conflicts: Check for version mismatches
- Compilation errors: Ensure Java/Kotlin compatibility

### Runtime Issues
- API connection failures: Check network permissions and API endpoint
- Notification issues: Verify notification permissions and scheduling
- Database errors: Check schema migrations and queries
