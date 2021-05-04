package com.rivaresa.cusmateogl.finialize_amount;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.rivaresa.cusmateogl.databinding.ActivityFinalizeAmountBinding;
import com.rivaresa.cusmateogl.finialize_amount.action.FinalizeAmountAction;
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

import static android.content.Context.MODE_PRIVATE;

public class FinalizeAmountViewmodel extends AndroidViewModel {
    public FinalizeAmountViewmodel(@NonNull Application application) {
        super(application);

        compositeDisposable=new CompositeDisposable();
        repository=FinalizeAmountRepository.getInstance();
        this.application=application;
        repository.setCompositeDisposable(compositeDisposable);
        mAction=new MutableLiveData<>();
        repository.setmAction(mAction);
        sharedPreferences=application.getSharedPreferences("login",MODE_PRIVATE);
    }

    FinalizeAmountRepository repository;
    Application application;
    SharedPreferences sharedPreferences;
    CompositeDisposable compositeDisposable;
    MutableLiveData<FinalizeAmountAction> mAction;
    Retrofit retrofit;
    ActivityFinalizeAmountBinding binding;
    ApiInterface apiInterface;
    public ObservableField<String> loanAmount=new ObservableField<>("");
    public ObservableField<Double> maxAmount=new ObservableField<>(0.0);


    public void setBinding(ActivityFinalizeAmountBinding binding) {
        this.binding = binding;
    }

    public void getSettlementDetails()
    {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Accountno", sharedPreferences.getString("account_number",""));
        jsonParams.put("flag", "");


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getSettlementDetails(apiInterface,body);
    }

    public MutableLiveData<FinalizeAmountAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        (repository.getCompositeDisposable()).dispose();
    }

    public void reset() {
        mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.DEFAULT));
    }

    public void clickCalculator(View view)
    {
        mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.CLICK_CALCULATOR));
    }

    public void clickNext(View view)
    {
        if (loanAmount.get().equals("")){
            showSnakbar("Please enter required loan amount");
        }else if (Double.parseDouble(loanAmount.get())>maxAmount.get()) {
            showSnakbar("Please enter an amount less than "+maxAmount.get());
        }
        else {
            mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.CLICK_NEXT));
        }
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

    private void showSnakbar(String message) {

        Snackbar snackbar = Snackbar
                .make(binding.mainLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void clickSettings(View view)
    {
        mAction.setValue(new FinalizeAmountAction(FinalizeAmountAction.CLICK_SETTINGS));
    }

//        amountToPay.set(String.valueOf(bal));
//        amountToPayInt.set(bal);


}
