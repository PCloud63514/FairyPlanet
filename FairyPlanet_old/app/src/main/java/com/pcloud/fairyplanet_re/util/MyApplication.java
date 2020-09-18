package com.pcloud.fairyplanet_re.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by PCloud on 2018-01-03.
 */

public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = this;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}