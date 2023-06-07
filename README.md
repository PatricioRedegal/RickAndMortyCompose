# Rick and Morty Character App

This is an Android application that displays characters from the popular TV show Rick and Morty. It leverages the [Rick and Morty API](https://rickandmortyapi.com) to fetch character data, allowing users to search for characters, apply filters and more.

## Features

- Search: Users can search for characters by name, species, or status.
- Filters: The app provides filters to refine character search results based on their status.
- Favorites: Add or remove favorites and show them into a list
- Error Handling: The application handles errors gracefully, providing users with informative error messages in case of network or API issues.
- Splash Screen: The app features a visually appealing splash screen that appears during the initial app launch.
- Unit Tests: The codebase includes unit tests to ensure the correctness of various components and functionalities.
- MVVM Pattern: The application follows the Model-View-ViewModel (MVVM) architectural pattern, which separates concerns and improves maintainability.
- Built with Kotlin and Compose: The app is built using Kotlin programming language and utilizes Jetpack Compose for building the user interface.
- App Icon: An icon representing the Rick and Morty theme has been added to enhance the app's visual identity.
- Material3: The app incorporates Material3 design principles and components, providing a modern and cohesive user experience.
- Product Flavors: The app supports different product flavors, including "dev" and "pro", allowing for customization and separate builds targeting specific environments or configurations.
- Modules: The codebase is organized into modules, including Domain, Data, and Use Cases, following a modular architecture that promotes separation of concerns and code reusability.
- Internet Connection Handler: The app includes an internet connection handler to monitor the device's network connectivity and provide appropriate feedback to the user when the internet connection is not available.
- DataStore: The app utilizes DataStore, a data storage solution provided by Jetpack, to save and retrieve user-selected filters persistently. This ensures that the applied filters are preserved across app sessions.
- Hilt Dependency Injection: The app leverages Hilt, a dependency injection framework provided by Android Jetpack, to manage and simplify dependency injection throughout the application. This promotes modularity, testability, and maintainability of the codebase.
- libs.versions.toml: The project includes a libs.versions.toml file that centralizes the version management of all the external libraries used in the project. This ensures consistency and makes it easier to manage and update the library versions.

## Installation

1. Open the project in Android Studio.
2. Build and run the application on an Android device or emulator.

Note: Ensure that you have the latest version of Android Studio and the required Android SDK dependencies installed.

## Acknowledgements
- [Rick and Morty API](https://rickandmortyapi.com): The API used to fetch character data.
- [Jetpack Compose](https://developer.android.com/jetpack/compose): The modern Android UI toolkit used for building the user interface.
- [Kotlin Programming Language](https://kotlinlang.org/): The programming language used for developing the app.
- External Libraries: [Retrofit](https://square.github.io/retrofit/), [Landscapist](https://github.com/skydoves/landscapist) and [Lottie](https://github.com/airbnb/lottie-android).

## Contact
For any inquiries or feedback, please contact Patricio José Rodríguez Arias.

Enjoy exploring the world of Rick and Morty characters!

