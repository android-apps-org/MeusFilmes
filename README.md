# [My Movies](https://www.themoviedb.org/)

## Device configurations

- Lifecycle events after device is rotated:
    - onPause, onStop, onDestroy, onCreate, onStart, onResume
- Some values such as device orientation and screen width
    - can change at run-time
    - default behavior is to destroy and re-create activity 
        - whenever a device configuration changes

## Why use an AsyncTaskLoader for threads bound to an Activity rather than AsyncTask?

- Delivers the result to the current Activity (YES)
- Prevents duplication of background tasks (YES)
- Makes background threads run faster (NO)
- Helps eliminate duplication of zombie activities (YES)


## Layout

- CoordinatorLayout
  - AppBarLayout
    - CollapsingToolbarLayout
      - ImageView
      - View
      - Toolbar
  - NestedScrollView
    - LinearLayout
      - includes
  - FloatingActionButton

## Resources

- [Shared Element Transitions](https://mikescamell.com/shared-element-transitions-part-1/)


java.lang.IllegalArgumentException: Shared element name must not be null

