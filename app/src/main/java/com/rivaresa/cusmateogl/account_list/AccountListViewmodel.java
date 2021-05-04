package com.rivaresa.cusmateogl.account_list;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.account_list.action.AccountListAction;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class AccountListViewmodel extends AndroidViewModel {

    Application application;
    public CompositeDisposable compositeDisposable;
    SharedPreferences sharedPreferences;
    public Activity activity;
    MutableLiveData<AccountListAction> mAction;
    Retrofit retrofit;
    ApiInterface apiInterface;
    AccountListRepository repository;

    public AccountListViewmodel(@NonNull Application application) {
        super(application);

        this.application=application;
        repository=AccountListRepository.getInstance();
        mAction=new MutableLiveData<>();
        compositeDisposable=new CompositeDisposable();
        sharedPreferences=application.getSharedPreferences("login", Context.MODE_PRIVATE);
        repository.setCompositeDisposable(compositeDisposable);
    }

    Dialog loading;


    public void initLoading(Context context) {
        loading= Services.showLoading(context);
    }

    public void cancelLoading()
    {
        if(loading!=null)
        {
            loading.cancel();
            loading=null;
        }
    }

    public LiveData<AccountListAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void clickSettings(View view)
    {
        mAction.setValue(new AccountListAction(AccountListAction.CLICK_SETTINGS));
    }

    public void getAccountDetails()
    {

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Cust_id", sharedPreferences.getString("cust_id",""));


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.accountDetailsCall(apiInterface,body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
    }

    public void reset() {
        mAction.setValue(new AccountListAction(AccountListAction.DEFAULT));
    }

    public void getInventoryDetails(String account_number) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("custid", sharedPreferences.getString("cust_id",""));
        jsonParams.put("accno", account_number);


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.inventoryDetailsCall(apiInterface,body);
    }


}
