package com.rivaresa.cusmateogl.login;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.meetic.marypopup.MaryPopup;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.login.action.LoginAction;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class LoginViewmodel extends AndroidViewModel {

    LoginRepository loginRepository;
    Application application;
    public CompositeDisposable compositeDisposable;

    public Activity activity;
    MutableLiveData<LoginAction> mAction;
    Retrofit retrofit;
    ApiInterface apiInterface;
    public LoginViewmodel(@NonNull Application application) {
        super(application);
        loginRepository=LoginRepository.getInstance();
        this.application=application;
        compositeDisposable=new CompositeDisposable();

        loginRepository.setDisposible(compositeDisposable);
        mAction=new MutableLiveData<>();
    }

    public ObservableField<String> of_username=new ObservableField<>("");
    public ObservableField<String> of_password=new ObservableField<>("");
    ProgressDialog loading;


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
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        loginRepository.otpGenerate(apiInterface, body);
    }
    public void clickSignIn(View view)
    {
        if (of_username.get().equals(""))
        {
            showSnakbar("Username cannot be ampty",view);
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
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
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

    public void ErrorDialoge(String error)
    {
        MaryPopup maryPopup;
        maryPopup = MaryPopup.with((activity))
                .cancellable(false)
                .draggable(true)
                .scaleDownDragging(true)
                .fadeOutDragging(true)
                .center(true)

                .blackOverlayColor(Color.parseColor("#DD444444"))
                .backgroundColor(Color.parseColor("#EFF4F5"));

        LayoutInflater inflater= activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_popup,null);
        TextView errorMessage=view.findViewById(R.id.txt_msg);
        errorMessage.setText(error);

        maryPopup.from(view);
        maryPopup.content(view).show();
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
