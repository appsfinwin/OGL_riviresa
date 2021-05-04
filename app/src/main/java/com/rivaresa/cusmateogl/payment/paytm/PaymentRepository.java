package com.rivaresa.cusmateogl.payment.paytm;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.payment.action.PaymentAction;
import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementDetailsResponse;
import com.rivaresa.cusmateogl.payment.paytm.pojo.checksum.ChecksumResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class PaymentRepository {
    public static PaymentRepository instance;
    CompositeDisposable compositeDisposable;
    MutableLiveData<PaymentAction> mAction=new MutableLiveData<>();
    public static PaymentRepository getInstance()
    {
        if (instance==null)
        {
            instance=new PaymentRepository();
        }
        return instance;
    }

    public MutableLiveData<PaymentAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<PaymentAction> mAction) {
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
                            mAction.setValue(new PaymentAction(PaymentAction.API_SUCCESS, response));

                        }else {
                            mAction.setValue(new PaymentAction(PaymentAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new PaymentAction(PaymentAction.API_ERROR,e.getMessage()));
                    }
                });

    }

    public CompositeDisposable getDisposible() {
        return compositeDisposable;
    }

    public void setDisposible(CompositeDisposable compositeDisposable) {
        this.compositeDisposable=compositeDisposable;
    }

    @SuppressLint("CheckResult")
    public void getChecksum(ApiInterface apiInterface, RequestBody body)
    {
        Single<ChecksumResponse> call=apiInterface.getChecksum(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ChecksumResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ChecksumResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new PaymentAction(PaymentAction.CHECKSUM_SUCCESS, response));

                        }else {
                           // mAction.setValue(new PaymentAction(PaymentAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new PaymentAction(PaymentAction.API_ERROR,e.getMessage()));
                    }
                });

    }
}
