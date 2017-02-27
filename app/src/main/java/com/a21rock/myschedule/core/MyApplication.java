package com.a21rock.myschedule.core;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by 21rock on 2017/2/23.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
