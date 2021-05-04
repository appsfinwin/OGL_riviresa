package com.rivaresa.cusmateogl.signup;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.signup.action.SignupAction;
import com.rivaresa.cusmateogl.signup.pojo.otp_response.OtpGenerateResponse;
import com.rivaresa.cusmateogl.supporting_class.Enc_crypter;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class SignupRepository {
    public static SignupRepository instance;
    public static SignupRepository getInstance()
    {
        if (instance==null)
        {
            instance=new SignupRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<SignupAction> mAction;

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<SignupAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<SignupAction> mAction) {
        this.mAction = mAction;
    }

    final Enc_crypter encr = new Enc_crypter();



    @SuppressLint("CheckResult")
    public void otpGenerate(ApiInterface apiInterface, RequestBody body)
    {
        Single<OtpGenerateResponse> call=apiInterface.otpGenerate(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<OtpGenerateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(OtpGenerateResponse response) {
                        if (response.getOtpData()!=null) {
                            mAction.setValue(new SignupAction(SignupAction.OTP_SUCCESS, response));

                        }else {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new SignupAction(SignupAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }


}
