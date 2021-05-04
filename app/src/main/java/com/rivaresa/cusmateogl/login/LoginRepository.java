package com.rivaresa.cusmateogl.login;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.login.action.LoginAction;
import com.rivaresa.cusmateogl.login.pojo.LoginResponse;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.otp_creation.OtpCreationResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class LoginRepository {
    public static LoginRepository instance;
    CompositeDisposable compositeDisposable;

    MutableLiveData<LoginAction> mAction=new MutableLiveData<>();
    public static LoginRepository getInstance()
    {
        if (instance==null)
        {
            instance=new LoginRepository();
        }
        return instance;
    }

    public MutableLiveData<LoginAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<LoginAction> mAction) {
        this.mAction = mAction;
    }


    @SuppressLint("CheckResult")
    public void otpGenerate(ApiInterface apiInterface, RequestBody body)
    {
        Single<OtpCreationResponse> call=apiInterface.createOtp(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<OtpCreationResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(OtpCreationResponse response) {
                        if (response.getOtp().getStatus().equals("Y")) {
                            mAction.setValue(new LoginAction(LoginAction.OTP_SUCCESS, response));

                        }else {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR, response.getOtp().getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void loginApi(ApiInterface apiInterface, RequestBody body)
    {
        Single<LoginResponse> call=apiInterface.login(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        if (loginResponse.getLoginData()!=null) {
                            mAction.setValue(new LoginAction(LoginAction.LOGIN_SUCCESS, loginResponse));

                        }else {
                            mAction.setValue(new LoginAction(LoginAction.API_ERROR, loginResponse.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new LoginAction(LoginAction.API_ERROR,e.getMessage()));
                    }
                });



    }

    public CompositeDisposable getDisposible() {
        return compositeDisposable;
    }

    public void setDisposible(CompositeDisposable compositeDisposable) {
        this.compositeDisposable=compositeDisposable;
    }
}
