package com.jdemaagd.meusfilmes.common;

import android.content.Context;

import com.jdemaagd.meusfilmes.R;

public class AppSettings {

    public static String getFavoritesSortDescriptor(Context context) {
        return context.getString(R.string.sort_favorites);
    }

    public static String getPopularitySortDescriptor(Context context) {
        return context.getString(R.string.sort_popular);
    }

    public static String getTopRatedSortDescriptor(Context context) {
        return context.getString(R.string.sort_top_rated);
    }
}