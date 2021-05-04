package com.rivaresa.cusmateogl.final_confirmation;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.final_confirmation.action.ConfirmationAction;
import com.rivaresa.cusmateogl.final_confirmation.pojo.ApplictaionDetailsResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ConfirmationRepository {
    public static ConfirmationRepository instance;
    public static ConfirmationRepository getInstance()
    {
        if (instance==null)
        {
            instance=new ConfirmationRepository();
        }
        return instance;
    }

    CompositeDisposable compositeDisposable;
    MutableLiveData<ConfirmationAction> mAction;

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<ConfirmationAction> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<ConfirmationAction> mAction) {
        this.mAction = mAction;
    }

    @SuppressLint("CheckResult")
    public void getAppliactionDetails(ApiInterface apiInterface, RequestBody body)
    {
        Single<ApplictaionDetailsResponse> call=apiInterface.getApplicationDetails(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApplictaionDetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ApplictaionDetailsResponse response) {
                        if (response.getPersonalDetails()!=null) {
                            mAction.setValue(new ConfirmationAction(ConfirmationAction.APPLICATION_DETAILS_SUCCESS, response));

                        }else {
                            mAction.setValue(new ConfirmationAction(ConfirmationAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new ConfirmationAction(ConfirmationAction.API_ERROR,e.getMessage()));
                    }
                });

    }
}
