# Prayer Time Notification App Architecture

## Overview
This document outlines the architecture for a mobile application that provides prayer time notifications for cities in Morocco. The app will fetch data from the Morocco Prayer Times API and display prayer times to users while also sending notifications for upcoming prayers.

## Data Source
The app will use the Morocco Prayer Times API (https://github.com/ZakariaMahmoud/Morocco-Prayer-Times-API) which provides:
- List of Moroccan cities with IDs
- Prayer times for specific dates
- Current day prayer times

API Endpoints:
- `/api/cities` - Get all cities
- `/api/cities/:id` - Get specific city by ID
- `/api/:city/:year/:month/:day` - Get prayer times for specific date
- `/api/:city/today` - Get prayer times for current day

## App Components

### 1. Core Components
- **MainActivity**: Main entry point for the application
- **CitySelectionActivity**: Allows users to select their city
- **PrayerTimesActivity**: Displays prayer times for the selected city
- **SettingsActivity**: Allows users to configure notification preferences

### 2. Data Layer
- **PrayerTimeRepository**: Manages data operations and caching
- **ApiService**: Handles API communication
- **DatabaseHelper**: Manages local storage of prayer times and settings
- **PreferenceManager**: Manages user preferences

### 3. Notification System
- **NotificationManager**: Handles creating and displaying notifications
- **AlarmManager**: Schedules notifications for prayer times
- **NotificationReceiver**: Receives scheduled alarms and triggers notifications

### 4. UI Components
- **PrayerTimeAdapter**: Displays prayer times in a list
- **CityAdapter**: Displays cities in a selectable list
- **TimeFormatter**: Formats time values for display

## Data Flow
1. User selects a city from the list
2. App fetches prayer times from API or local cache
3. Prayer times are displayed to the user
4. Notification alarms are scheduled for upcoming prayers
5. Notifications are triggered at the appropriate times

## Offline Support
- The app will cache prayer times for the selected city
- Cached data will be used when the device is offline
- Data will be refreshed when the app is opened with an internet connection

## Settings and Preferences
- City selection
- Notification on/off for each prayer
- Notification sound selection
- Notification timing (minutes before prayer)
- Language preference (Arabic/French)

## Technical Stack
- **Language**: Java/Kotlin
- **Network**: Retrofit for API communication
- **Database**: Room/SQLite for local storage
- **UI**: Native Android components
- **Background Processing**: WorkManager/AlarmManager

## Security Considerations
- API requests will use HTTPS
- No sensitive user data will be collected
- All data will be stored locally on the device

## Performance Considerations
- Minimize network requests by caching data
- Optimize battery usage for background notifications
- Ensure smooth UI performance with efficient data loading
