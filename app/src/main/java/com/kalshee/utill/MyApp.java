package com.kalshee.utill;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by eWeb_A1 on 7/11/2018.
 */

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {


    public static boolean mCheck;
    private String TAG="MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
Log.e("awra","wrdwer");

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {


        mCheck=true;
        Log.e(TAG, "=======onActivityCreated==========");

    }

    @Override
    public void onActivityStarted(Activity activity) {
        mCheck=true;
        Log.e(TAG, "=================onActivityStarted=============");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCheck=true;
        Log.e(TAG, "=========onActivityResumed================");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e(TAG, "========onActivityPaused=================");
        mCheck= false;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e(TAG, "======onActivityStopped===================");
        mCheck= false;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e(TAG, "===========onActivityDestroyed==============");

    }
}
