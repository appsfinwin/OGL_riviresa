package com.rivaresa.cusmateogl.account_details.closed_loans;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.account_details.closed_loans.action.ClosedLoanAction;
import com.rivaresa.cusmateogl.account_details.closed_loans.pojo.ClosedLoansResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ClosedLoansRepository {

    public static ClosedLoansRepository instance;
    CompositeDisposable compositeDisposable;
    MutableLiveData<ClosedLoanAction> mAction=new MutableLiveData<>();
    public static ClosedLoansRepository getInstance()
    {
        if (instance==null)
        {
            instance=new ClosedLoansRepository();

        }
        return instance;
    }

    public MutableLiveData<ClosedLoanAction> getmAction() {
        return mAction;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @SuppressLint("CheckResult")
    public void getClosedLoans(ApiInterface apiInterface, RequestBody body)
    {
        Single<ClosedLoansResponse> call=apiInterface.getClosedLoans(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ClosedLoansResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ClosedLoansResponse response) {
                        if (response.getData().size()>0) {
                            mAction.setValue(new ClosedLoanAction(ClosedLoanAction.API_SUCCESS, response));

                        }else {
                            if (response.getData().size()<=0)
                            {
                                mAction.setValue(new ClosedLoanAction(ClosedLoanAction.NO_DATA,"No data available"));
                            }else {
                                mAction.setValue(new ClosedLoanAction(ClosedLoanAction.API_ERROR, response.getError()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new ClosedLoanAction(ClosedLoanAction.API_ERROR,e.getMessage()));
                    }
                });

    }
}
