# Music App

This is a **Music Streaming App** built using **Android Kotlin**. The app allows users to explore music, manage playlists, and stream songs using the **ExoPlayer** library. It follows **MVVM architecture** for better separation of concerns and uses **Firebase** for user authentication and database management.

## Features

- **User Authentication**: 
  - Users can create an account or log in using Firebase Authentication.

- **Home Screen**:
  - Displays sections like **New Releases**, **Recently Played**, **Most Played**, and **Trending** songs.
  - Each section shows a limited number of songs to maintain a clean UI.

- **Music Playback**:
  - Full music playback functionality is implemented using **ExoPlayer**.
  - Users can play, pause, and skip tracks seamlessly.

- **Playlist Management**:
  - Users can create and manage playlists.
  - Add or remove songs from playlists, and easily access them from the playlist screen.

- **MVVM Architecture**:
  - Uses **Model-View-ViewModel (MVVM)** architecture for clear separation of data, UI, and logic layers, ensuring maintainability and testability.

- **Firebase Integration**:
  - **Firebase Authentication** is used for user sign-up, login, and secure session management.
  - **Firebase Firestore** is used for managing user data, playlists, and song metadata.

## Technologies and Libraries Used

- **Kotlin**: For Android development.
- **MVVM Architecture**: Used to separate UI, data, and logic layers.
- **ExoPlayer**: For music playback functionality.
- **Firebase Authentication**: To manage user accounts and secure access.
- **Firebase Firestore**: For performing CRUD operations on playlists and user-specific data.
- **Jetpack Components**: Lifecycle-aware components like ViewModel and LiveData for managing UI-related data.
- **Material Design**: For modern UI design and user interactions.

## Setup Instructions

To set up and run this project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/your_username/your_repository_name.git

   Usage
After launching the app, the user can sign up or log in using Firebase Authentication.
On the Home Screen, users can explore:
New Releases: Discover the latest songs.
Recently Played: View songs recently played by the user.
Most Played: See the most popular songs.
Trending: Check out trending songs.
Music Playback: Users can play songs using ExoPlayer. Playback controls (play/pause/skip) are integrated seamlessly.
Playlists: Users can create playlists, add songs to them, and manage their music library easily.

Firebase Setup Instructions
To configure Firebase for this app:

Go to the Firebase Console.
Create a new Firebase project or use an existing project.
Enable Firebase Authentication and Firestore in the Firebase console.
Download the google-services.json file and place it in your project's app/ directory.
Sync your project with Gradle.

Future Enhancements 
Implement offline mode to allow users to play music even without internet access.
Add more personalized recommendations based on user preferences and listening history.
Improve search functionality to filter songs, albums, and artists.

