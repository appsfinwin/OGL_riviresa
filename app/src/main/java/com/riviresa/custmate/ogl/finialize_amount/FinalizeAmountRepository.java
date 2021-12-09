package com.riviresa.custmate.ogl.finialize_amount;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.riviresa.custmate.ogl.payment.paytm.pojo.SettlementDetailsResponse;
import com.riviresa.custmate.ogl.finialize_amount.action.FinalizeAmountAction;
import com.riviresa.custmate.ogl.payment.paytm.pojo.SettlementDetailsResponse;
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

public class FinalizeAmountRepository {

    public static FinalizeAmountRepository instance;
    public static FinalizeAmountRepository getInstance()
    {
        if (instance==null)
        {
            instance=new FinalizeAmountRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<FinalizeAmountAction> mAction;

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<FinalizeAmountAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<FinalizeAmountAction> mAction) {
        this.mAction = mAction;
    }

    @SuppressLint("CheckResult")
    public void getSettlementDetails(ApiInterface apiInterface, RequestBody body)
    {
        Single<SettlementDetailsResponse> call=apiInterface.getSettlementDetails(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SettlementDetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(SettlementDetailsResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.SETTLEMENT_SUCCESS, response));

                        }else {
                            mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.SETTLEMENT_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.SETTLEMENT_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.SETTLEMENT_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.SETTLEMENT_ERROR, e.getMessage()));
                        }
                    }
                });

    }

}
