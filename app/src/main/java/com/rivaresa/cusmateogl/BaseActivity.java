package com.rivaresa.cusmateogl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rivaresa.cusmateogl.login.LoginActivity;
import com.rivaresa.cusmateogl.supporting_class.ConstantClass;

public abstract class BaseActivity extends AppCompatActivity
        implements LogoutListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int activityCount=0;
    long backgroundTime=0,foregroundTime=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        activityCount=1;
        App.registerSessionListener(this);
    }

    @Override
    public void doLogout() {

    }

    @Override
    public void foreground() {

        foregroundTime= System.currentTimeMillis();
        long total=foregroundTime-(sharedPreferences.getLong(ConstantClass.BACKGROUND_TIME,0));
//        total=foregroundTime-backgroundTime;
//        if (activityCount!=1) {


//            boolean isLogin = sharedPreferences.getBoolean(ConstantClass.IS_LOGIN, false);
//            if (!isLogin) {
        if (total>300000) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
            //}
//        }else {
//            activityCount=2;
//        }
    }
    @Override
    public void background() {
//        editor.putBoolean(ConstantClass.BACKGROUND,true);
//        editor.apply();

        backgroundTime= System.currentTimeMillis();
        editor.putLong(ConstantClass.BACKGROUND_TIME,backgroundTime);
        editor.apply();
    }
    @Override
    public void onSessionLogout() {
        editor.putBoolean(ConstantClass.IS_LOGIN,false);
        editor.apply();


            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //reset session when user interact
        App.resetSession();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        boolean isBackground=sharedPreferences.getBoolean(ConstantClass.BACKGROUND, false);
//                if (isBackground) {
//        boolean isLogin=sharedPreferences.getBoolean(ConstantClass.IS_LOGIN, false);
//            if (!isLogin) {
//                Intent intent = new Intent(this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        }
    }


}
