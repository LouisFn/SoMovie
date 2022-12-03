SoMovie [Work in progress 🚧]
==================

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](http://developer.android.com/index.html)
![API](https://img.shields.io/badge/API-26%2B-orange.svg?style=flat)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/LouisFn/SoMovie/blob/master/LICENSE)
[![Build](https://github.com/LouisFn/SoMovie/workflows/Build/badge.svg?branch=master)](https://github.com/LouisFn/SoMovie/actions)

An android app using [The Movie Database](https://www.themoviedb.org/) API to display the current trending, upcoming, popular and top rated movies.

Built entirely using the Jetpack Compose.

The aim of this project is to test the latest trends, libraries and tools to develop an Android application.

## Preview

### Explore
<img src="preview/preview1.gif" width="250"/>

### Discover
<img src="preview/preview2.gif" width="250"/>

### Watchlist
<img src="preview/preview3.gif" width="250"/>

### Details
<img src="preview/preview4.gif" width="250"/>

## Tech Stack

- [Kotlin](https://kotlinlang.org/) - Official programming language for Android development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) - Official Kotlin's tooling for performing asynchronous work.
- [Android Jetpack](https://developer.android.com/jetpack) - Jetpack is a suite of libraries to help developers build state-of-the-art applications.
	- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel is designed to store and manage UI-related data in a lifecycle conscious way.
	- [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room library provides an abstraction layer over SQLite to allow for more robust database access.
	- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - DataStore is a data storage solution that stores key-value pairs or typed objects.
	- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android.
	- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps to load and display pages of data from a larger dataset from local storage or over network.
- [Accompanist](https://github.com/google/accompanist) - A collection of extension libraries for Jetpack Compose.
- [Retrofit](https://github.com/square/retrofit) - A library for building REST API clients.
- [Moshi](https://github.com/square/moshi) - Moshi is a modern JSON library for Android to parse JSON into Java and Kotlin classes
- [Coil](https://github.com/coil-kt/coil) - An image loading library.
- [Timber](https://github.com/JakeWharton/timber) - Timber is a logger with a small, extensible API which provides utility on top of Android's normal Log class.
- [LeakCanary](https://square.github.io/leakcanary/) - LeakCanary is a memory leak detection library for Android.
- [Flipper](https://fbflipper.com/) - Flipper is a platform for debugging Android apps.
- [Detekt](https://github.com/detekt/detekt) - A static code analysis library for Kotlin.
- [Ktlint](https://github.com/pinterest/ktlint) [(Spotless)](https://github.com/diffplug/spotless) - A library for formatting Kotlin code according to official guidelines.
- [Testing](https://developer.android.com/training/testing) - The app is currently covered with unit tests and instrumentation tests.
	- [JUnit](https://junit.org/junit4) - JUnit is a unit testing framework for the Java programming language.
	- [Kotest assertion](https://kotest.io/docs/assertions/assertions.html) - Kotest assertion is a library providing fluent assertions for kotlin.
	- [Faker](https://github.com/serpro69/kotlin-faker) - A library for generating fake data that can be used for testing.
- [Gradle's Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Gradle’s Kotlin DSL is an alternative syntax to the Groovy DSL with an enhanced editing experience.

For more information about used dependencies, see [this](/buildSrc/src/main/java/Libraries.kt) file.

## Testing

As the application is not intended to be deployed in production, only the feature `watchlist` has been tested.

## Development setup

Add your [The Movie Database](https://www.themoviedb.org/)'s API key in local.properties file.
```xml
tmdb_api_key=YOUR_API_KEY
```

## License

[MIT](LICENSE)
