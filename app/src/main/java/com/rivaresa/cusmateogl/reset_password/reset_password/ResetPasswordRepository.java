package com.rivaresa.cusmateogl.reset_password.reset_password;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.reset_password.forgot_password.pojo.ResetPasswordResponse;
import com.rivaresa.cusmateogl.reset_password.reset_password.action.ResetPasswordAction;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ResetPasswordRepository {
    public static ResetPasswordRepository INSTANCE;
    private MutableLiveData<ResetPasswordAction> mAction;
    private CompositeDisposable compositeDisposable;
    public static ResetPasswordRepository getInstance()
    {
        if (INSTANCE==null)
        {
            INSTANCE= new ResetPasswordRepository();
        }
        return INSTANCE;
    }

    public MutableLiveData<ResetPasswordAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<ResetPasswordAction> mAction) {
        this.mAction = mAction;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @SuppressLint("CheckResult")
    public void validateOtp(ApiInterface apiInterface, RequestBody body)
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
                                mAction.setValue(new ResetPasswordAction(ResetPasswordAction.RESET_OTP_SUCCESS, response));
                            }else {
                                mAction.setValue(new ResetPasswordAction(ResetPasswordAction.API_ERROR, response.getData().getTable1().get(0).getReturnMessage()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new ResetPasswordAction(ResetPasswordAction.API_ERROR,e.getMessage()));
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void resendOtp(ApiInterface apiInterface, RequestBody body)
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
                                mAction.setValue(new ResetPasswordAction(ResetPasswordAction.RESEND_OTP_SUCCESS, response));
                            }else {
                                mAction.setValue(new ResetPasswordAction(ResetPasswordAction.API_ERROR, response.getData().getTable1().get(0).getReturnMessage()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new ResetPasswordAction(ResetPasswordAction.API_ERROR,e.getMessage()));
                    }
                });
    }
}
