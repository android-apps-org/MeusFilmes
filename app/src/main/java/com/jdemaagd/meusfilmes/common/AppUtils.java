package com.jdemaagd.meusfilmes.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppUtils {

    private boolean connected;
    private static AppUtils instance;

    public static AppUtils getInstance() {
        if (null != instance) {
            return instance;
        } else {
            instance = new AppUtils();
        }

        return instance;
    }

    private AppUtils() { }

    // API Level 28 and below
    public boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();

            return connected;
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }

        return connected;
    }
}
