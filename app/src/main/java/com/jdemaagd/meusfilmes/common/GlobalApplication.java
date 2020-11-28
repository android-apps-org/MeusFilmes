package com.jdemaagd.meusfilmes.common;

import android.app.Application;
import android.content.Context;

public class GlobalApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        /*
            use appContext here to process
            when other classes need context object to initialize when application is created
         */
    }

    public static Context getAppContext() {
        return appContext;
    }
}
