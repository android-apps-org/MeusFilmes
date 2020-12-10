# My Movies App

## Entry: MovieActivity Design

- [DataBinding](https://developer.android.com/topic/libraries/data-binding)
  - removes findViewById: an expensive operation that requires traversing view hierarchy at runtime
  - connects layout to activity/fragment at compile-time (layout inflation)
    - compiler generates helper class (binding class) when activity is created
    - can access view with generated binding class without any overhead
- Lifecycle-Aware: ` mBinding.setLifecycleOwner(this) `
  - components can observe for changes on this Activity
- LiveData: wrapper around entities
  - to receive notifications of changes
  - which trigger updates in UI to reflect the changes
- Room
  - used to save/remove favorite movies from movies list
  - observes changes via ViewModel/LiveData
- ViewModel: gets list of movies to display in GridView as Poster Images
  - fetches from [The Movie Database](https://www.themoviedb.org/)
    - via RetroFit/OkHttp3
    - sorts by user preferred setting on popularity or top-rated
  - observes changes via ViewModel/LiveData

## DetailsActivity

- View details of selected movie
  - can favorite or un-favorite
  - updates via Room
  - observes for changes via ViewModel/LiveData/Room
    - UI updates to reflect changes

