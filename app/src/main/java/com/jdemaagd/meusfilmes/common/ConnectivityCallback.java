package com.jdemaagd.meusfilmes.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class ConnectivityCallback {

    private static final String LOG_TAG = ConnectivityCallback.class.getSimpleName();

    private Context context;

    public ConnectivityCallback(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void registerNetworkCallback()
    {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerDefaultNetworkCallback( new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(Network network) {
                    Log.d(LOG_TAG, "Internet Available.");
                    AppConstants.IS_NETWORK_CONNECTED = true;
                }
                @Override
                public void onLost(Network network) {
                    Log.d(LOG_TAG, "Internet NOT Available.");
                    AppConstants.IS_NETWORK_CONNECTED = false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            AppConstants.IS_NETWORK_CONNECTED = false;
        }

        Log.d(LOG_TAG, "Connected: " + AppConstants.IS_NETWORK_CONNECTED);
    }
}
