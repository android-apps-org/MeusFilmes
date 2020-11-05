# [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)

## [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)

- A lifecycle-aware Observable data holder
    - respects lifecycle of other app components (activities, fragments, services)
    - ensures LiveData only updates app component observers that are in an active lifecycle state
- Sits between database and UI
- Monitors changes in database and notifies observers when data changes
- Subject (LiveData) keeps list of Observers subscribed to it
    - notifies them when there is any relevant changes
    - when data changes, set value method on LiveData object will be called
    - which will trigger call to method in each of the Observers
- NOTE: LiveData considers an observer to be in an active state
    - if its lifecycle is in the STARTED or RESUMED state
    - only notifies active observers about updates
    - inactive observers registered to watch LiveData objects are not notified about changes

## [Room](https://developer.android.com/topic/libraries/architecture/room)

- Object Relational Mapping (ORM) Library
- Persistence library provides an abstraction layer over SQLite
    - allows for more robust database access while harnessing full power of SQLite
- SQL validation at compilation
- Built to work with LiveData and RxJava for data observation
- Helps create cache of app's data on device
    - cache serves as app's single source of truth
    - allows users to view a consistent copy of key information within app
    - regardless of whether users have an internet connection

## [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

- Designed to store and manage UI-related data in a lifecycle conscious way
- Allows data to survive configuration changes such as rotation
- Lifecycle starts once Activity is created and lasts until it is finished
- So we can cache complex data in ViewModel
- When Activity is re-created after rotation, it will use same ViewModel object in cache data
- Can make asynchronous calls without overhead of preventing memory leaks (concern when no VM)
    - i.e. Activity is destroyed before call finishes

## [Lifecycle-aware Components](https://developer.android.com/topic/libraries/architecture/lifecycle)

- Perform actions in response to a change in the lifecycle status of another component
- These components help produce better-organized, lighter-weight code, that is easier to maintain

## Resources

- [Data Storage with Room](https://developer.android.com/training/data-storage/room)
- [Lifecycle Dependencies](https://developer.android.com/jetpack/androidx/releases/lifecycle)

