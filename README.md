Petask is an Android application designed to help users organize their daily tasks while taking care of a virtual pet.

The application combines task management with a simple reward system: completing tasks gives the user coins and experience, which can be used to take care of their pet and progress through the leveling system.

## Features

- Firebase Authentication (Email/Password and Google)
- User profile (Update picture from the gallery or use camera)
- Virtual pet with different states depending on its needs
- Task management
- Weekly and Monthly Goals
- Coin and reward system
- Experience and leveling system

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Clean Architecture
- Room
- Retrofit
- Firebase Authentication with Email/Password and google
- Hilt
- Flow
- Git / GitHub

## Development

Petask was developed as a collaborative project with the guidance and support of a senior developer.

The project provided experience working on a real Android application while applying software architecture principles, Git workflows, and modern Android development practices.

## Project Status
Petask is currently under development. Some features are still being completed and improved.

## NOTE: Google Sign-In & Authentication

Since this is a portfolio, the google-services.json file was intentionally included in this public repository so the project can be cloned and tested without any additional Firebase setup.

The API key included in the file is restricted through Google Cloud Console and can only be used with Firebase Authentication services.

**Google Sign-In:** Due to Google's standard security policies, the SHA-1 fingerprint of the build environment must be registered in the Firebase project. Because your local SHA-1 is not registered, pressing the Google Sign-In button will result in a Firebase error on your device.

If you need to test Google Sign-In, please contact me and I can add the required SHA fingerprint to the Firebase project. Alternatively, you can register using an email and password.

  
