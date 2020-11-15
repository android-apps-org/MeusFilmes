package com.jdemaagd.meusfilmes.common;

import com.jdemaagd.meusfilmes.BuildConfig;

public final class AppConstants {

    // api keys
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String BASE_URL = BuildConfig.MOVIES_BASE_URL;
    public static final String POSTER_URL = BuildConfig.POSTER_BASE_URL;

    // database
    public static final String DATABASE_NAME = "movie";

    // intent extras
    public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    // sort descriptors
    public static final String FAVORITES_SORT_DESCRIPTOR = "favorites";
    public static final String POPULARITY_SORT_DESCRIPTOR = "popular";
    public static final String TOP_RATED_SORT_DESCRIPTOR = "top_rated";
}
