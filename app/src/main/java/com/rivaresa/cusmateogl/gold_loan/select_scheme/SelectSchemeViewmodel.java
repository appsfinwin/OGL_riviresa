package com.rivaresa.cusmateogl.gold_loan.select_scheme;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.gold_loan.select_scheme.action.SelectSchemeAction;
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

public class SelectSchemeViewmodel extends AndroidViewModel {
    public SelectSchemeViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        mAction=new MutableLiveData<>();
        compositeDisposable=new CompositeDisposable();
        sharedPreferences=application.getSharedPreferences("login", Context.MODE_PRIVATE);
        repository=SelectSchemeRepository.getInstance();
        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);
    }

    Application application;
    public CompositeDisposable compositeDisposable;
    SharedPreferences sharedPreferences;
    public Activity activity;
    MutableLiveData<SelectSchemeAction> mAction;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SelectSchemeRepository repository;

    public LiveData<SelectSchemeAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
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
    public void getSchemes()
    {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Accountno", sharedPreferences.getString("account_number",""));
        jsonParams.put("inventoryno", sharedPreferences.getString("inventory_number",""));


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getCSchemes(apiInterface,body);
    }

    public void getSettlementDetails()
    {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Accountno", sharedPreferences.getString("account_number",""));
        //jsonParams.put("flag", "");


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getSettlementDetails(apiInterface,body);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
    }

    public void reset() {

        mAction.setValue(new SelectSchemeAction(SelectSchemeAction.DEFAULT));
    }
}
