package com.rivaresa.cusmateogl.gold_loan;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.account_details.pojo.GetBankDetailsResponse;
import com.rivaresa.cusmateogl.gold_loan.action.GoldLoanAction;
import com.rivaresa.cusmateogl.gold_loan.pojo.terms_and_conditions.TermsResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class GoldLoanRepository {

    public static GoldLoanRepository instance;
    CompositeDisposable compositeDisposable;
    MutableLiveData<GoldLoanAction> mAction;
    public static GoldLoanRepository getInstance()
    {
        if (instance==null)
        {
            instance=new GoldLoanRepository();
        }
        return instance;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public MutableLiveData<GoldLoanAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<GoldLoanAction> mAction) {
        this.mAction = mAction;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @SuppressLint("CheckResult")
    public void getTerms(ApiInterface apiInterface)
    {
        Single<TermsResponse> call=apiInterface.getTermsAndConditions();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TermsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(TermsResponse response) {
                        if (response.getData()!=null) {
                            mAction.setValue(new GoldLoanAction(GoldLoanAction.TERMS_API_SUCCESS, response));

                        }else {
                            mAction.setValue(new GoldLoanAction(GoldLoanAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new GoldLoanAction(GoldLoanAction.API_ERROR,e.getMessage()));
                    }
                });
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

                            mAction.setValue(new GoldLoanAction(GoldLoanAction.BANK_DETAILS_SUCCESS, response));
                        }else {
                            mAction.setValue(new GoldLoanAction(GoldLoanAction.BANK_DETAILS_ERROR, "Bank details empty! Please contact branch for further details."));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new GoldLoanAction(GoldLoanAction.API_ERROR,e.getMessage()));
                    }
                });
    }
}
