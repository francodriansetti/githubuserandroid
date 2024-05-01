GitHub User Android App
=======================

Project Overview
----------------

The GitHub User Android App allows users to search for GitHub profiles using keywords. It displays a list of GitHub users and provides detailed views for each user's profile, including their repositories, followers, and other relevant information.

Key Features
------------

-   **User Search**: Search GitHub users by keywords.
-   **User Details**: View detailed information about GitHub users including profile data, repositories, and activity.
-   **Throttled Search**: Implements a throttled search mechanism to optimize network usage and enhance user experience.

Architecture
------------

This project follows Clean Architecture principles, structured around the MVVM (Model-View-ViewModel) pattern, and uses Jetpack Compose for the UI.

### Core Components

-   **API**: Interfaces to communicate with GitHub's REST API.
-   **Model**: Data models for mapping JSON responses from GitHub.
-   **Repository**: Abstraction layer for fetching data from network or other sources.
-   **DI**: Dependency injection modules using Hilt to provide instances of classes.
-   **Domain**: Contains use cases encapsulating business logic.
-   **UI**: Compose screens and ViewModel to manage UI-related data and state management.

Technologies Used
-----------------

-   **Kotlin**
-   **Jetpack Compose**
-   **Retrofit**: HTTP client for Android.
-   **Coil**: Image loading library for Compose.
-   **Hilt**: Dependency injection library.
-   **Coroutines & Flow**: For asynchronous programming and reactive streams.

License
-------

Distributed under the MIT License. 