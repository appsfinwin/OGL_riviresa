package com.rivaresa.cusmateogl.reset_password.forgot_password;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.reset_password.forgot_password.pojo.ResetPasswordResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ForgotPasswordRepository {

    public MutableLiveData<ForgotPasswordAction> mAction;
    public CompositeDisposable compositeDisposable;
    public static ForgotPasswordRepository instance;
    public static ForgotPasswordRepository getInstance()
    {
        if (instance==null)
        {
            instance=new ForgotPasswordRepository();
        }
        return instance;
    }

    public MutableLiveData<ForgotPasswordAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<ForgotPasswordAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @SuppressLint("CheckResult")
    public void sentOtp(ApiInterface apiInterface, RequestBody body)
    {
        Single<ResetPasswordResponse> call=apiInterface.resetPassword(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResetPasswordResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResetPasswordResponse response) {
                        if (response.getData().getTable1().size()>0) {
                            if (response.getData().getTable1().get(0).getReturnStatus().equals("Y"))
                            {
                                mAction.setValue(new ForgotPasswordAction(ForgotPasswordAction.GENERATE_OTP_SUCCESS, response));
                            }else {
                                 mAction.setValue(new ForgotPasswordAction(ForgotPasswordAction.API_ERROR, response.getData().getTable1().get(0).getReturnMessage()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new ForgotPasswordAction(ForgotPasswordAction.API_ERROR,e.getMessage()));
                    }
                });
    }


}
