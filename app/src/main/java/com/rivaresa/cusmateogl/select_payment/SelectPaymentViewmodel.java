package com.rivaresa.cusmateogl.select_payment;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SelectPaymentViewmodel extends AndroidViewModel {

    MutableLiveData<String> mAction;

    public SelectPaymentViewmodel(@NonNull Application application) {
        super(application);
        mAction=new MutableLiveData<>();
    }

    public void clickInterestPayment(View view)
    {
        mAction.setValue("interest_payment");
    }

    public void clickPartPayment(View view)
    {
        mAction.setValue("part_payment");
    }

    public void clickFullPayment(View view)
    {
        mAction.setValue("full_payment");
    }

    public LiveData<String> getmAction() {
        return mAction;
    }

    public void setmAction(MutableLiveData<String> mAction) {
        this.mAction = mAction;
    }

    public void reset() {
        mAction.setValue("default");
    }

    public void clickSettings(View view)
    {
        mAction.setValue("settings");
    }
}
