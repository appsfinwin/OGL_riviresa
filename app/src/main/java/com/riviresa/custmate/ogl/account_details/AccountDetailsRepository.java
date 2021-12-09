package com.riviresa.custmate.ogl.account_details;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.riviresa.custmate.ogl.retrofit.ApiInterface;
import com.riviresa.custmate.ogl.account_details.pojo.GetBankDetailsResponse;
import com.riviresa.custmate.ogl.retrofit.ApiInterface;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class AccountDetailsRepository {
    public static AccountDetailsRepository instance;
    CompositeDisposable compositeDisposable;
    MutableLiveData<AccountAction> mAction;
    public static AccountDetailsRepository getInstance()
    {
        if (instance==null)
        {
            instance= new AccountDetailsRepository();
        }
        return instance;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<AccountAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<AccountAction> mAction) {
        this.mAction = mAction;
    }

    @SuppressLint("CheckResult")
    public void getBankDetails(ApiInterface apiInterface, RequestBody body)
    {
        Single<GetBankDetailsResponse> call=apiInterface.getBankDetails(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetBankDetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(GetBankDetailsResponse response) {
                        if (response.getBankDetails().getTable().size()>0) {

                                mAction.setValue(new AccountAction(AccountAction.BANK_DETAILS_SUCCESS, response));
                            }else {
                                mAction.setValue(new AccountAction(AccountAction.API_ERROR, "Bank details empty! Please contact branch for further details."));
                            }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new AccountAction(AccountAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new AccountAction(AccountAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new AccountAction(AccountAction.API_ERROR, e.getMessage()));
                        }
                    }
                });
    }
}
