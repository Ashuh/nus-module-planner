package com.ashuh.nusmoduleplanner.common;

import android.app.Application;

public class NusModulePlannerApplication extends Application {
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(getApplicationContext());
    }
}
