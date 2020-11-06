package com.jdemaagd.meusfilmes.data;

import android.content.Context;

public class AppSettings {

    public static final String FAVORITES_SORT_DESCRIPTOR = "favorites";
    public static final String POPULARITY_SORT_DESCRIPTOR = "popular";
    public static final String TOP_RATED_SORT_DESCRIPTOR = "top_rated";

    public static String getFavoritesSortDescriptor(Context context) {
        return getFavoritesSortDescriptor();
    }

    public static String getPopularitySortDescriptor(Context context) {
        return getPopularitySortDescriptor();
    }

    public static String getTopRatedSortDescriptor(Context context) {
        return getTopRatedSortDescriptor();
    }

    private static String getFavoritesSortDescriptor() {
        return FAVORITES_SORT_DESCRIPTOR;
    }

    private static String getPopularitySortDescriptor() {
        return POPULARITY_SORT_DESCRIPTOR;
    }

    public static String getTopRatedSortDescriptor() {
        return TOP_RATED_SORT_DESCRIPTOR;
    }

}