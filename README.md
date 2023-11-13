# Bird Watching App
Authors by: 
ST10081895-Kyle Newmark 
ST10114669-Aiden Bosman
ST10082034-Jordan Titus


## Project Description

The Bird Watching App is a mobile application for bird enthusiasts to explore bird hotspots, record their sightings, and access information about different bird species. This README provides an overview of the project structure, key components, and functionality.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Components and Features](#components-and-features)
- [Opening Android Studio as a ZIP File](#opening-android-studio-as-a-zip-file)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Before using the Bird Watching App, ensure you have the following prerequisites:

- Android Studio
- Android device or emulator
- Google Maps API key
- Mapbox API key
- API key for eBird
- Android API 34 to run 

## Installation

Follow these steps to set up the Bird Watching App on your local environment:

1. Clone the repository:

   ```bash
   git clone https://github.com/AidenBosman124/OPSC7312_POE_Task2
   ```

2. Open the project in Android Studio.

3. Add your Google Maps API key and Mapbox API key in the respective files. Replace `"YOUR_MAPS_API_KEY"` and `"YOUR_MAPBOX_API_KEY"` with your actual keys.

4. Build and run the app on an Android device or emulator.

## Usage

The Bird Watching App allows users to:

- Log in and create an account to access personalized features.
- Explore nearby bird hotspots and view information about each location.
- Adjust measurement units between kilometers and miles.
- Select the maximum distance for bird hotspot searches.
- View a Google Map with bird hotspot markers.
- Tap on markers to view hotspot details and get directions.
- Record bird sightings and access bird species information.
- Switch between different app sections using the navigation drawer.

## Components and Features

### `MainPage` Activity

The `MainPage` activity serves as the central hub for the app and includes:

- Navigation to different sections (Settings, Birds, Create Checklists, Checklists, Hotspots).
- Access to a Google Map for exploring bird hotspots.
- A button to navigate to the `MapsActivity` for more map functionality.

### `MapsActivity`

The `MapsActivity` provides Google Maps functionality, including:

- Displaying the user's current location on the map.
- Adding markers for bird hotspots within a specified distance.
- Calculating and displaying directions to selected hotspots.

### `Nearbyfrag` Fragment

The `Nearbyfrag` fragment allows users to:

- Select a maximum distance for hotspot searches using a SeekBar.
- View a list of nearby bird hotspots on a map.
- Tap on hotspot markers to get directions to the selected hotspot.

### `Settingsfrag` Fragment

The `Settingsfrag` fragment enables users to:

- Toggle measurement units between kilometers and miles.
- Adjust the maximum distance for hotspot searches.
- Set their preferences for the app.

### `UserDataClass`

The `UserDataClass` represents user data and includes functions for user authentication and registration. It also provides password validation and user registration functionality.

### `StartPage` Activity

The `StartPage` activity allows users to:

- Log in with their existing account or register a new account.

## Opening Android Studio as a ZIP File

If you have downloaded Android Studio as a ZIP file and need to extract and set it up, follow these steps:

1. **Download Android Studio as a ZIP File**:
   - Download Android Studio as a ZIP file from the [Android Studio download page](https://developer.android.com/studio).

2. **Extract the ZIP File**:
   - Locate the downloaded ZIP file on your computer.
   - Right-click on the ZIP file and select "Extract" or "Extract All" (depending on your operating system).
   - Choose a destination folder where you want to extract Android Studio.

3. **Launch Android Studio**:
   - Navigate to the folder where you extracted Android Studio.
   - Inside the extracted folder, locate the `bin` directory.
   - Run the `studio.sh` (Linux/macOS) or `studio64.exe` (Windows) script to launch Android Studio.

4. **Complete the Android Studio Setup Wizard**:
   - Follow the on-screen instructions provided by the Android Studio Setup Wizard to configure and set up Android Studio.

Please note that the specific names of scripts and executable files may vary depending on your operating system. Always make sure you're using the correct file for your OS when launching Android Studio.

## Contributing

Contributions to the Bird Watching App are welcome. To contribute, follow these guidelines:

1. Fork the repository.

2. Create a new branch for your feature or bug fix.

3. Make changes and commit them with descriptive messages.

4. Push your changes to your fork.

5. Create a pull request to the main repository.

