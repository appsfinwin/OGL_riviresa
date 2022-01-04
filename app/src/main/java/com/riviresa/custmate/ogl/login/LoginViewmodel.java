package com.riviresa.custmate.ogl.login;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.riviresa.custmate.ogl.login.action.LoginAction;
import com.riviresa.custmate.ogl.retrofit.ApiInterface;
import com.riviresa.custmate.ogl.retrofit.RetrofitClient;
import com.riviresa.custmate.ogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginViewmodel extends AndroidViewModel {

    LoginRepository loginRepository;
    Application application;
    public CompositeDisposable compositeDisposable;

    public Activity activity;
    MutableLiveData<LoginAction> mAction;
    ApiInterface apiInterface;
    public LoginViewmodel(@NonNull Application application) {
        super(application);
        loginRepository=LoginRepository.getInstance();
        this.application=application;
        compositeDisposable=new CompositeDisposable();

        loginRepository.setDisposible(compositeDisposable);
        mAction=new MutableLiveData<>();
        apiInterface= RetrofitClient.getApi();
    }

    public ObservableField<String> of_username=new ObservableField<>("");
    public ObservableField<String> of_password=new ObservableField<>("");
    Dialog loading;


    public void initLoading(Context context) {
        loading= Services.showProgressDialog(context);
    }

    public void cancelLoading()
    {
        if(loading!=null)
        {
            loading.cancel();
            loading=null;
        }
    }

    public LiveData<LoginAction> getLoginLivedata() {
        mAction=loginRepository.getmAction();
        return mAction;
    }

    public void setLoginLivedata(MutableLiveData<LoginAction> loginLivedata) {
        this.mAction = loginLivedata;
    }

    public void generateOtp(String phone)
    {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Flag", "SEND");
        jsonParams.put("MobileNo", phone);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String request=new JSONObject(jsonParams).toString();
        loginRepository.otpGenerate(apiInterface, body);
    }
    public void clickSignIn(View view)
    {
        if (of_username.get().equals(""))
        {
            Toast.makeText(view.getContext(), "Enter phone number", Toast.LENGTH_SHORT).show();
            //showSnakbar("Username cannot be ampty",view);
        }else if (of_password.get().equals(""))
        {
            showSnakbar("Password cannot be ampty",view);
        }else {
            initLoading(view.getContext());
            loginApi();
        }
    }

    public void loginApi()
    {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("username", of_username.get());
        jsonParams.put("password", of_password.get());


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String error=new JSONObject(jsonParams).toString();
        loginRepository.loginApi(apiInterface,body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        (loginRepository.getDisposible()).clear();
        mAction.setValue(new LoginAction(LoginAction.DEFAULT));
    }


    public void setActivity(LoginActivity loginActivity) {
        this.activity=loginActivity;
    }


    public void clickForgotPassword(View view)
    {
        mAction.setValue(new LoginAction(LoginAction.CLICK_FORGOT_PASSWORD));
    }

    public void clickSignUp(View view)
    {
        mAction.setValue(new LoginAction(LoginAction.CLICK_SIGN_UP));
    }

    private void showSnakbar(String message, View view) {

        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }


}
