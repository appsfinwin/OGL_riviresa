package com.rivaresa.cusmateogl.reset_password.otp;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.reset_password.otp.action.OtpAction;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.Signup_response.SignUpResponse;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.otp_creation.OtpCreationResponse;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.validate_otp.ValidateOtpResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.signup.pojo.otp_response.OtpGenerateResponse;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class OtpRepository {
    public static OtpRepository instance;
    public static OtpRepository getInstance()
    {
        if (instance==null)
        {
            instance=new OtpRepository();
        }
        return instance;
    }

    CompositeDisposable disposable;
    MutableLiveData<OtpAction> mAction;

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    public MutableLiveData<OtpAction> getmACtion() {
        return mAction;
    }

    public void setmACtion(MutableLiveData<OtpAction> mACtion) {
        this.mAction = mACtion;
    }

    @SuppressLint("CheckResult")
    public void signUp(ApiInterface apiInterface, RequestBody body)
    {
        Single<SignUpResponse> call=apiInterface.signUp(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SignUpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(SignUpResponse response) {
                        if (response.getUser().getData().getStatus().equals("Y")) {
                            mAction.setValue(new OtpAction(OtpAction.SIGNUP_SUCCESS, response));

                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, response.getUser().getData().getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
 @SuppressLint("CheckResult")
    public void validateOtp(ApiInterface apiInterface, RequestBody body)
    {
        Single<ValidateOtpResponse> call=apiInterface.validateOtp(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ValidateOtpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(ValidateOtpResponse response) {
                        if (response.getOtp ().getStatus().equals("Y")) {
                            mAction.setValue(new OtpAction(OtpAction.VALIDATE_OTP_SUCCESS, response));

                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, response.getOtp().getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void resendOtp(ApiInterface apiInterface, RequestBody body)
    {
        Single<OtpGenerateResponse> call=apiInterface.otpGenerate(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<OtpGenerateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(OtpGenerateResponse response) {
                        if (response.getOtpData()!=null) {
                            mAction.setValue(new OtpAction(OtpAction.RESEND_OTP_SUCCESS, response));

                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void resendLoginOtp(ApiInterface apiInterface, RequestBody body)
    {
        Single<OtpCreationResponse> call=apiInterface.createOtp(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<OtpCreationResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(OtpCreationResponse response) {
                        if (response.getOtp().getStatus().equals("Y")) {
                            mAction.setValue(new OtpAction(OtpAction.RESEND_LOGIN_OTP_SUCCESS, response));

                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, response.getOtp().getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new OtpAction(OtpAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
