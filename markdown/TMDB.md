# [Movies DB API](https://www.themoviedb.org/documentation/api)

## [Example Endpoints](https://developers.themoviedb.org/3/getting-started/introduction)

- https://api.themoviedb.org/3/movie/popular?api_key=
- https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1&api_key=
- https://image.tmdb.org/t/p/w185/riYInlsq2kf1AWoGm80JQW5dLKp.jpg
- https://api.themoviedb.org/3/movie/{id}/reviews?api_key=
- https://api.themoviedb.org/3/movie/{id}/videos?api_key=
- https://api.themoviedb.org/3/movie/{id}?api_key=

## Lint

- Analyze --> Inspect Code
- ./gradlew lint
- gradle build && gradle installDebug

## [Data Binding](https://developer.android.com/topic/libraries/data-binding)

- [ButterKnife](https://jakewharton.github.io/butterknife/)

## Networking

- [Volley](https://www.geeksforgeeks.org/volley-library-in-android/)
- [Retrofit](https://square.github.io/retrofit/)
- [Repository Module](https://developer.android.com/jetpack/guide#fetch-data)
- MovieActivity -> MovieViewModel -> MovieRepository -> TMDBService -> API Endpoints
```
    apiClient.getMovies(
            getApplication().getString(R.string.language),
            String.valueOf(page)).enqueue(new Callback<ApiResponse<Movie>>() {
        @Override
        public void onResponse(Call<ApiResponse<Movie>> call,
                               Response<ApiResponse<Movie>> response) {
            if (response.isSuccessful()) {
                List<Movie> result = response.body().results;
                List<Movie> value = mMovies.getValue();
                if (value == null || value.isEmpty()) {
                    mMovies.setValue(result);
                } else {
                    value.addAll(result);
                    mMovies.setValue(value);
                }
                status.setValue(0);
            }
        }

        @Override
        public void onFailure(Call<ApiResponse<Movie>> call, Throwable t) {
            mMovies = null;
            status.setValue(NO_INTERNET);
        }
    });
```

## Resources

- [MovieDB Status Codes](https://www.themoviedb.org/documentation/api/status-codes)
- [URLConnection](https://developer.android.com/reference/java/net/URLConnection)
- [Retrofit 2](https://www.youtube.com/watch?v=KIAoQbAu3eA&feature=youtu.be&t=35m8s)

