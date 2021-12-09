package com.riviresa.custmate.ogl;

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
    private static int ONE_MINUTE=60000;
    private static int FIVE_MINUTE=300000;

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
        },  (FIVE_MINUTE));
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
        if (logoutListener!=null) {
            logoutListener.background();
            //userSessionStart();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        //logoutListener.background();

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        //Log.d("MyApp", "App in foreground")
        if (logoutListener!=null) {
            logoutListener.foreground();
        }
    }
}
