package com.rivaresa.cusmateogl.gold_loan.select_scheme;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.gold_loan.select_scheme.action.SelectSchemeAction;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.pojo.SchemesResponse;
import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementDetailsResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class SelectSchemeRepository {

    public static SelectSchemeRepository instance;
    CompositeDisposable compositeDisposable;
    MutableLiveData<SelectSchemeAction> mAction;
    public static SelectSchemeRepository getInstance()
    {
        if (instance==null)
        {
            instance=new SelectSchemeRepository();
        }
        return instance;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<SelectSchemeAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<SelectSchemeAction> mAction) {
        this.mAction = mAction;
    }

    @SuppressLint("CheckResult")
    public void getCSchemes(ApiInterface apiInterface, RequestBody body) {

        Single<SchemesResponse> call = apiInterface.getCSchemes(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SchemesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(SchemesResponse response) {
                        if (response.getData() != null) {
                            mAction.setValue(new SelectSchemeAction(SelectSchemeAction.SCHEMES_SUCCESS, response));

                        } else {
                            mAction.setValue(new SelectSchemeAction(SelectSchemeAction.SCHEMES_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new SelectSchemeAction(SelectSchemeAction.SCHEMES_ERROR, e.getMessage()));
                    }
                });


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
                            mAction.setValue(new SelectSchemeAction(SelectSchemeAction.SETTLEMENT_SUCCESS, response));

                        }else {
                            mAction.setValue(new SelectSchemeAction(SelectSchemeAction.SETTLEMENT_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new SelectSchemeAction(SelectSchemeAction.SETTLEMENT_ERROR,e.getMessage()));
                    }
                });

    }
}
