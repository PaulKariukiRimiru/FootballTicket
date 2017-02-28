package com.example.mike.footballticket;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Mike on 1/27/2017.
 */
public class AppClass extends Application {

    //this will initialize multidex in your own Application class
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
