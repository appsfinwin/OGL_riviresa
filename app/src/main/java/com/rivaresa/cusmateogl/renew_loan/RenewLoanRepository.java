package com.rivaresa.cusmateogl.renew_loan;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.renew_loan.action.RenewLoanAction;
import com.rivaresa.cusmateogl.renew_loan.pojo.RenewalResponse;
import com.rivaresa.cusmateogl.renew_loan.pojo.gold_loan.GoldLoanResponse;
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

public class RenewLoanRepository {
    public static RenewLoanRepository instance;
    public static RenewLoanRepository getInstance()
    {
        if (instance==null)
        {
            instance=new RenewLoanRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<RenewLoanAction> mAction;

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<RenewLoanAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<RenewLoanAction> mAction) {
        this.mAction = mAction;
    }

    @SuppressLint("CheckResult")
    public void loanRenewal(ApiInterface apiInterface, RequestBody body)
    {
        Single<RenewalResponse> call=apiInterface.renewLoan(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RenewalResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(RenewalResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new RenewLoanAction(RenewLoanAction.RENEW_SUCCES, response));

                        }else {
                            mAction.setValue(new RenewLoanAction(RenewLoanAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new RenewLoanAction(RenewLoanAction.API_ERROR,e.getMessage()));
                    }
                });

    }   @SuppressLint("CheckResult")
    public void goldLoanRenewal(ApiInterface apiInterface, RequestBody body)
    {
        Single<GoldLoanResponse> call=apiInterface.renewGoldLoan(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GoldLoanResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(GoldLoanResponse response) {
                        if (response.getData().getTable1().size() > 0) {
                            if (response.getData().getTable1().get(0).getReturnStatus().equals("Y")) {
                                mAction.setValue(new RenewLoanAction(RenewLoanAction.GOLD_LOAN_RENEW_SUCCESS, response));

                            } else {
                                mAction.setValue(new RenewLoanAction(RenewLoanAction.API_ERROR, response.getData().getTable1().get(0).getReturnMessage()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof SocketTimeoutException)
                        {
                            mAction.setValue(new RenewLoanAction(RenewLoanAction.API_ERROR,"Timeout! Please try again later"));
                        }else if (e instanceof UnknownHostException)
                        {
                            mAction.setValue(new RenewLoanAction(RenewLoanAction.API_ERROR,"No Internet"));
                        }else {
                            mAction.setValue(new RenewLoanAction(RenewLoanAction.API_ERROR, e.getMessage()));
                        }
                    }
                });

    }
}
