package com.rivaresa.cusmateogl.home;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.home.action.HomeAction;

public class HomeViewmodel extends AndroidViewModel {
    public HomeViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        mAction=new MutableLiveData<>();
    }


    Application application;
    MutableLiveData<HomeAction> mAction;
    public void clickPayOnline(View view)
    {
        mAction.setValue(new HomeAction(HomeAction.CLICK_PAY_ONLINE));
    }

    public void clickAccountDetails(View view)
    {
        mAction.setValue(new HomeAction(HomeAction.CLICK_ACCOUNT_DETAILS));
    }

    public void clickOnlineGoldLoan(View view)
    {
        mAction.setValue(new HomeAction(HomeAction.CLICK_ONLINE_GOLD_LOAN));
    }
    public void clickContact(View view)
    {
        mAction.setValue(new HomeAction(HomeAction.CLICK_CONTACT));
    }

    public MutableLiveData<HomeAction> getmAction() {
        return mAction;
    }
}
