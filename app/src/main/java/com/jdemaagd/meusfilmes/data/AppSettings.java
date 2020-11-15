package com.jdemaagd.meusfilmes.data;

import android.content.Context;

import com.jdemaagd.meusfilmes.common.AppConstants;

// TODO: Shared Preferences

public class AppSettings {

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
        return AppConstants.FAVORITES_SORT_DESCRIPTOR;
    }

    private static String getPopularitySortDescriptor() {
        return AppConstants.POPULARITY_SORT_DESCRIPTOR;
    }

    public static String getTopRatedSortDescriptor() {
        return AppConstants.TOP_RATED_SORT_DESCRIPTOR;
    }
}