package com.rivaresa.cusmateogl.account_details;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.account_details.pojo.Table;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AccountDetailsViewmodel extends AndroidViewModel {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiInterface apiInterface;
    AccountDetailsRepository repository;
    CompositeDisposable compositeDisposable;

    ObservableField<List<Table>> bankDetailsList= new ObservableField<>();
    MutableLiveData<AccountAction> mAction;
    public AccountDetailsViewmodel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mAction=new MutableLiveData<>();
        compositeDisposable= new CompositeDisposable();
        repository= AccountDetailsRepository.getInstance();

        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);
    }









    public void clickClosedLoans(View view) {
        mAction.setValue(new AccountAction(AccountAction.CLICK_CLOSED_LOANS));
    }
    public void clickAccounts(View view) {
        mAction.setValue(new AccountAction(AccountAction.CLICK_ACCOUNTS));
    }

    public LiveData<AccountAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void reset() {
        //mAction.setValue(new AccountAction(AccountAction.DEFAULT));
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


    public void getBankDetails() {


        Map<String, String> items = new HashMap<>();

        items.put("CustId", sharedPreferences.getString("cust_id",""));

        //params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(items)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getBankDetails(apiInterface,body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
    }
}
