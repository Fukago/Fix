package com.skylinetan.energycloud;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by skylineTan on 16/12/8.
 */
public class CloudApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
