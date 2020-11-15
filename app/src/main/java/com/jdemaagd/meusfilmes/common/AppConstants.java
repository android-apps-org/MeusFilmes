package com.jdemaagd.meusfilmes.common;

import com.jdemaagd.meusfilmes.BuildConfig;

public final class AppConstants {

    // api keys
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String BASE_URL = BuildConfig.MOVIES_BASE_URL;
    public static final String POSTER_URL = BuildConfig.POSTER_BASE_URL;

    // bundle state
    public static final String BUNDLE_COUNT = "count";
    public static final String BUNDLE_LAST_PAGE = "last_page";
    public static final String BUNDLE_LOADING = "loading";
    public static final String BUNDLE_PREF = "pref";
    public static final String BUNDLE_RECYCLER = "recycler";

    // database
    public static final String DATABASE_NAME = "movie";

    // intent extras
    public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    // network
    public static boolean IS_NETWORK_CONNECTED = false;

    // sort descriptors
    public static final String FAVORITES_SORT_DESCRIPTOR = "favorites";
    public static final String POPULARITY_SORT_DESCRIPTOR = "popular";
    public static final String TOP_RATED_SORT_DESCRIPTOR = "top_rated";
}
