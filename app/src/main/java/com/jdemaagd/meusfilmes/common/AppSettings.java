package com.jdemaagd.meusfilmes.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

    public static int getChangedMovie(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_changed_movie), -1);
    }

    public static void setChangedMovie(Context context, int movieNumber) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(context.getString(R.string.pref_changed_movie), movieNumber);
        editor.apply();
    }
}