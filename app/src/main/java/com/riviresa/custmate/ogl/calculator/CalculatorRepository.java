package com.riviresa.custmate.ogl.calculator;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.riviresa.custmate.ogl.calculator.action.CalculatorAction;
import com.riviresa.custmate.ogl.calculator.pojo.CalculatorResponse;
import com.riviresa.custmate.ogl.calculator.action.CalculatorAction;
import com.riviresa.custmate.ogl.calculator.pojo.CalculatorResponse;
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

public class CalculatorRepository {
    public static CalculatorRepository instance;
    public static CalculatorRepository getInstance()
    {
        if (instance==null)
        {
            instance=new CalculatorRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<CalculatorAction> mAction;

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<CalculatorAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<CalculatorAction> mAction) {
        this.mAction = mAction;
    }

    @SuppressLint("CheckResult")
    public void getCalculator(ApiInterface apiInterface, RequestBody body)
    {
        Single<CalculatorResponse> call=apiInterface.getCalculation(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CalculatorResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(CalculatorResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new CalculatorAction(CalculatorAction.CALCULATE_SUCCESS, response));

                        }else {
                            mAction.setValue(new CalculatorAction(CalculatorAction.API_ERROR, response.getError()));
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new CalculatorAction(CalculatorAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new CalculatorAction(CalculatorAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new CalculatorAction(CalculatorAction.API_ERROR, e.getMessage()));
                        }
                    }
                });

    }
}
