package com.rivaresa.cusmateogl;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import java.util.Timer;
import java.util.TimerTask;

public class App extends Application implements LifecycleObserver {

    private static LogoutListener logoutListener = null;
    private static Timer timer = null;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }


    public static void userSessionStart() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (logoutListener != null) {
                    logoutListener.onSessionLogout();
                   // log.d("App", "Session Destroyed");
                }
            }
        },  (300000));
    }

    public static void resetSession() {
        userSessionStart();
    }

    public static void registerSessionListener(LogoutListener listener) {
        logoutListener = listener;
    }


    @Override
    public void onTrimMemory(int level) {
        // this method is called when the app goes in background.
        // you can perform your logout service here
        super.onTrimMemory(level);
        logoutListener.background();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        //Log.d("MyApp", "App in foreground")
        logoutListener.foreground();
    }
}
