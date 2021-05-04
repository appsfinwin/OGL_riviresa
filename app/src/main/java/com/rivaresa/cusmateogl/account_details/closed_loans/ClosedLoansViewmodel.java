package com.rivaresa.cusmateogl.account_details.closed_loans;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.account_details.closed_loans.action.ClosedLoanAction;
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

public class ClosedLoansViewmodel extends AndroidViewModel {
    public ClosedLoansViewmodel(@NonNull Application application) {
        super(application);

        compositeDisposable=new CompositeDisposable();
        this.application=application;
        repository=ClosedLoansRepository.getInstance();
        repository.setCompositeDisposable(compositeDisposable);

        sharedPreferences=application.getSharedPreferences("login", Context.MODE_PRIVATE);
        repository.setCompositeDisposable(compositeDisposable);
        mAction=new MutableLiveData<>();
    }

    Application application;
    ClosedLoansRepository repository;
    CompositeDisposable compositeDisposable;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;
    MutableLiveData<ClosedLoanAction> mAction;


    public void getClosedLoans() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("cust_id", sharedPreferences.getString("cust_id",""));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getClosedLoans(apiInterface,body);
    }

    public MutableLiveData<ClosedLoanAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        (repository.getCompositeDisposable()).dispose();
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

    
}
