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
        if (activityCount!=1) {

            editor.putBoolean(ConstantClass.BACKGROUND, false);
            editor.apply();
            boolean isLogin = sharedPreferences.getBoolean(ConstantClass.IS_LOGIN, false);
            if (!isLogin) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }else {
            activityCount=2;
        }
    }
    @Override
    public void background() {
        editor.putBoolean(ConstantClass.BACKGROUND,true);
        editor.apply();
    }
    @Override
    public void onSessionLogout() {
        editor.putBoolean(ConstantClass.IS_LOGIN,false);
        editor.apply();

        if (!sharedPreferences.getBoolean(ConstantClass.BACKGROUND,false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
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
