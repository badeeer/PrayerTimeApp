# UI/UX Design for Prayer Time App

## App Screens

### 1. Splash Screen
- App logo and name
- Brief loading animation
- Transitions to City Selection or Main Screen based on whether city is already selected

### 2. City Selection Screen
- Search bar for finding cities
- Scrollable list of cities (both Arabic and French names)
- Option to sort by name or region
- "Select" button for each city

### 3. Main Screen (Prayer Times)
- Current date (Gregorian and Hijri)
- Selected city name with option to change
- Today's prayer times in card format:
  - Fajr
  - Sunrise
  - Dhuhr
  - Asr
  - Maghrib
  - Isha
- Each prayer card shows:
  - Prayer name
  - Prayer time
  - Time remaining until prayer
  - Toggle for notification

### 4. Settings Screen
- Notification settings:
  - Enable/disable all notifications
  - Individual prayer notification toggles
  - Notification timing (5, 10, 15, 30 minutes before)
  - Notification sound selection
- Display settings:
  - 12/24 hour format
  - Language selection (Arabic/French/English)
  - Theme selection (Light/Dark/System)
- About section with app information

## Color Scheme
- Primary: #4CAF50 (Green - representing Islam)
- Secondary: #2196F3 (Blue - for UI elements)
- Background: #FFFFFF (Light mode) / #121212 (Dark mode)
- Text: #212121 (Dark text) / #FFFFFF (Light text for dark mode)
- Accent: #FFC107 (Yellow/Gold - for highlights)

## Typography
- Primary Font: Roboto for Latin text
- Arabic Font: Noto Sans Arabic
- Prayer Names: Medium weight
- Times: Bold weight
- Regular text: Regular weight

## Iconography
- Prayer icons for each prayer time
- Notification bell icon
- Settings gear icon
- Location/city icon
- Calendar icon for date

## Animations
- Smooth transitions between screens
- Subtle animation for upcoming prayer highlight
- Notification toggle animation
- Pull-to-refresh animation for updating prayer times

## Accessibility Considerations
- High contrast mode
- Support for screen readers
- Adjustable text size
- Touch targets of appropriate size

## Offline State
- Clear indication when app is in offline mode
- Display cached prayer times with last updated timestamp
- Disable features that require internet connection
