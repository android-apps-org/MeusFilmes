# [My Movies](https://www.themoviedb.org/)

## Device configurations

- Lifecycle events after device is rotated:
    - onPause, onStop, onDestroy, onCreate, onStart, onResume
- Some values such as device orientation and screen width
    - can change at run-time
    - default behavior is to destroy and re-create activity 
        - whenever a device configuration changes

## [Loaders](https://developer.android.com/guide/components/loaders)

- Loaders have been deprecated as of Android P (API 28)
- Recommended to use a combination of ViewModels and LiveData
- ViewModels survive configuration changes like Loaders but with less boilerplate
- LiveData provides a lifecycle-aware way of loading data that you can reuse in multiple ViewModels

## Why use an AsyncTaskLoader for threads bound to an Activity rather than AsyncTask?

- Delivers the result to the current Activity (YES)
- Prevents duplication of background tasks (YES)
- Makes background threads run faster (NO)
- Helps eliminate duplication of zombie activities (YES)

