<h1>Pop Flix</h1>

PopFlix is a gorgeous client application for [TMDb](https://www.themoviedb.org) on Android, built using Kotlin.

## Architecture and Tech-stack

* Built on MVVM architecture pattern
* Uses [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/), specifically ViewModel, LiveData and Room.
* Has a clean, gorgeous user interface with pretty animations, built using Android Transitions framework, and [Material Components for Android](https://github.com/material-components/material-components-android)
* Heavily uses [RxJava](https://github.com/ReactiveX/RxJava) for network calls, transformations, and database observation.
* Completely offline ready. MovieDB uses [Room](https://developer.android.com/topic/libraries/architecture/room) for managing a local SQLite database, which means that if you have seen some content already while you were online, you won't need an internet connection to see it again. Everything except movie trailers are cached.
* Uses [Retrofit](https://square.github.io/retrofit/) for making API calls.
* Uses [Glide](https://github.com/bumptech/glide) for image loading.
* Built on a Single-Activity Architecture. Every screen in the app is a fragment.

## Features
* Discover Top Rated and Popular movies on TMDb.
* Search for movies (TODO)
* View movie details like release date, rating, overview, **movie trailer** and cast right inside the app.
* Works offline by caching data into a database.
* Supports dark mode.

## Screenshots
<img src="https://raw.githubusercontent.com/vishvendra01/PopFlix/b60d926f3179c84e478fcdc6cbe519d0f65210e9/screenshots/screenshot1.png" width="25%"></img> <img src="https://raw.githubusercontent.com/vishvendra01/PopFlix/b60d926f3179c84e478fcdc6cbe519d0f65210e9/screenshots/screenshot2.png" width="25%"></img> <img src="https://raw.githubusercontent.com/vishvendra01/PopFlix/b60d926f3179c84e478fcdc6cbe519d0f65210e9/screenshots/screenshot3.png" width="25%"></img>