# TravelApp

## Overview

TravelApp is an Android application designed to provide users with a seamless experience for
exploring and planning their travel adventures. Built with Clean Architecture principles, SOLID
principles, MVVM architecture, and Material Design 3 guidelines, the app ensures maintainability,
scalability, and a modern UI/UX.

## Clean Architecture

Clean Architecture divides the application into layers with well-defined responsibilities, promoting
separation of concerns and maintainability. The key layers are:

- **Domain Layer**: Contains business logic and entities, independent of any framework or
  technology.
- **Data Layer**: Manages data retrieval and manipulation, including network requests and database
  operations.
- **Presentation Layer**: Handles UI logic and user interactions, separate from the business logic.

## MVVM (Model-View-ViewModel)

MVVM is an architectural pattern that separates an application into three main components:

- **Model**: Represents the data and business logic of the application.
- **View**: Represents the UI components of the application.
- **ViewModel**: Acts as an intermediary between the Model and the View, handling the presentation
  logic and managing the UI-related data.

## Material Design 3

TravelApp follows Material Design 3 guidelines to provide a modern and intuitive user experience. It
incorporates key principles such as bold typography, vibrant colors, meaningful motion, and a focus
on user actions.

## Technical Stack

- **Architecture**: Clean Architecture, MVVM
- **Networking**: Retrofit
- **Encryption**: Encrypted Shared Preferences
- **Concurrency**: Coroutine and Flow
- **UI Components**: LiveData, DataBinding, ViewBinding, Material Components for Android
- **Dependency Injection**: Koin

## Environment

The current environment is set to `dev`.

## Getting Started

To get started with Finance App, follow these steps:

1. Clone this repository: `git clone https://github.com/doananhtuan22111996/travel_app.git`
2. Open the project in Android Studio.
3. Set up Environment and update
   the `env.dev.properties`, `keystore.dev.properties`, `secrets.properties`
   and `app-keystore-dev.jks`  file with your configuration.
4. Build and run the project on an emulator or a physical device.

## Features

1. Splash screen follow
   by [Android developer documentation](https://developer.android.com/develop/ui/views/launch/splash-screen)
2. Onboarding
3. Feed Page with Paging 3 (Refresh, LoadMore, Paging
   State): [Paging 3 Overview](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
4. Detail Page with CollapsingToolbarLayout, content
5. Preview Images can zoom, swipe left/right by ViewPager2
6. Google map can show marker and zoom to position
7. Support Multiple languages in-app follow
   by [Android documentation](https://developer.android.com/guide/topics/resources/app-languages)

## Library supports

- Navigation Android: [Android Navigation](https://developer.android.com/guide/navigation)
- Coil: [Coil Documentation](https://coil-kt.github.io/coil/)
- PhotoView: [PhotoView on GitHub](https://github.com/GetStream/photoview-android)