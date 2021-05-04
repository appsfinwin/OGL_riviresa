package com.rivaresa.cusmateogl.account_list;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.account_list.action.AccountListAction;
import com.rivaresa.cusmateogl.account_list.pojo.account_response.AccountDetailsResponse;
import com.rivaresa.cusmateogl.account_list.pojo.inventory_response.InventoryDetailResponse;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class AccountListRepository {

    CompositeDisposable compositeDisposable;
    public static AccountListRepository instance;
    MutableLiveData<AccountListAction> mAction = new MutableLiveData<>();

    public static AccountListRepository getInstance() {
        if (instance == null) {
            instance = new AccountListRepository();
        }
        return instance;
    }


    @SuppressLint("CheckResult")
    public void accountDetailsCall(ApiInterface apiInterface, RequestBody body) {

        Single<AccountDetailsResponse> call = apiInterface.getAccountDetails(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AccountDetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(AccountDetailsResponse response) {
                        if (response.getData() != null) {
                            mAction.setValue(new AccountListAction(AccountListAction.ACCOUNT_DETAILS_SUCCESS, response));

                        } else {
                            mAction.setValue(new AccountListAction(AccountListAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new AccountListAction(AccountListAction.API_ERROR, e.getMessage()));
                    }
                });


    }


    public void inventoryDetailsCall(ApiInterface apiInterface, RequestBody body) {

        Single<InventoryDetailResponse> call = apiInterface.getInventoryDetails(body);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<InventoryDetailResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(InventoryDetailResponse response) {
                        if (response.getData() != null) {
                            mAction.setValue(new AccountListAction(AccountListAction.INVENTORY_DETAILS_SUCCESS, response));

                        } else {
                            mAction.setValue(new AccountListAction(AccountListAction.API_ERROR, response.getError()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAction.setValue(new AccountListAction(AccountListAction.API_ERROR, e.getMessage()));
                    }
                });


    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<AccountListAction> getmAction() {
        return mAction;
    }
}
