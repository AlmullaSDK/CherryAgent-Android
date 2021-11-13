package com.serbus.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;


import com.serbus.amxsdk.TopicListener;
import com.serbus.amxsdk.CherryAgent;

public class AppController extends Application implements TopicListener,Application.ActivityLifecycleCallbacks {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DEMO" , "SDK PRE INIT");
        new CherryAgent().initSDK(getApplicationContext(),"1fxb5zl0ul62u",this).setDomain("https://apib-kwt.almullaexchange.com/xms");
    }

    @Override
    public void onTopicReceived(String topic) {
        Log.e("TOPIC",topic);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        new CherryAgent().initSDK(getApplicationContext(),"CONSUMER_KEY",this).setDomain("https://apib-kwt.almullaexchange.com/xms");
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
