package com.ike.doctordemo;

import android.app.Application;
import android.content.Context;

/**
作者：ike
时间：2017/3/7 10:02
功能描述：Application
**/

public class DoctorApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
}
