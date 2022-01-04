package com.riviresa.custmate.ogl.gold_loan.select_scheme;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.riviresa.custmate.ogl.gold_loan.select_scheme.action.SelectSchemeAction;
import com.riviresa.custmate.ogl.retrofit.ApiInterface;
import com.riviresa.custmate.ogl.retrofit.RetrofitClient;
import com.riviresa.custmate.ogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SelectSchemeViewmodel extends AndroidViewModel {
    public SelectSchemeViewmodel(@NonNull Application application) {
        super(application);
        this.application = application;
        mAction = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE);
        repository = SelectSchemeRepository.getInstance();
        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);
        apiInterface = RetrofitClient.getApi();
    }

    Application application;
    public CompositeDisposable compositeDisposable;
    SharedPreferences sharedPreferences;
    MutableLiveData<SelectSchemeAction> mAction;
    ApiInterface apiInterface;
    SelectSchemeRepository repository;

    public LiveData<SelectSchemeAction> getmAction() {
        mAction = repository.getmAction();
        return mAction;
    }

    Dialog loading;


    public void initLoading(Context context) {
        loading = Services.showLoading(context);
    }

    public void cancelLoading() {
        if (loading != null) {
            loading.cancel();
            loading = null;
        }
    }

    public void getSchemes() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Accountno", sharedPreferences.getString("account_number", ""));
        jsonParams.put("inventoryno", sharedPreferences.getString("inventory_number", ""));


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        repository.getCSchemes(apiInterface, body);
    }

    public void getSettlementDetails() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Accountno", sharedPreferences.getString("account_number", ""));
        //jsonParams.put("flag", "");


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        repository.getSettlementDetails(apiInterface, body);
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
