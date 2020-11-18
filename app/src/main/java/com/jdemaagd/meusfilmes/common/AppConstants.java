package com.jdemaagd.meusfilmes.common;

import com.jdemaagd.meusfilmes.BuildConfig;

public final class AppConstants {

    // api keys
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String BASE_URL = BuildConfig.MOVIES_BASE_URL;
    public static final String BACKDROP_BASE_URL = BuildConfig.BACKDROP_BASE_URL;
    public static final String POSTER_URL = BuildConfig.POSTER_BASE_URL;

    // youtube keys
    public static final String YT_INTENT_URI = BuildConfig.YT_INTENT_URI;
    public static final String YT_PHOTO_URL = BuildConfig.YT_PHOTO_URL;
    public static final String YT_VIDEO_URL = BuildConfig.YT_VIDEO_URL;

    // network
    public static boolean IS_NETWORK_CONNECTED = false;

}
