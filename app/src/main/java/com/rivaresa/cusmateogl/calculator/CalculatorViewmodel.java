package com.rivaresa.cusmateogl.calculator;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.rivaresa.cusmateogl.calculator.action.CalculatorAction;
import com.rivaresa.cusmateogl.calculator.pojo.Data;
import com.rivaresa.cusmateogl.databinding.ActivityCalculatorBinding;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.DataHolder;
import com.rivaresa.cusmateogl.utils.Services;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CalculatorViewmodel extends AndroidViewModel {
    public CalculatorViewmodel(@NonNull Application application) {
        super(application);

        repository=CalculatorRepository.getInstance();
        this.application=application;
        compositeDisposable=new CompositeDisposable();
        mAction=new MutableLiveData<>();

        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);
    }

    Application application;
    CompositeDisposable compositeDisposable;
    CalculatorRepository repository;
    MutableLiveData<CalculatorAction> mAction;
    Activity activity;
    ApiInterface apiInterface;
    ActivityCalculatorBinding binding;

    public ObservableField<String> totalDays=new ObservableField<>("");
    public ObservableField<String> totalAmount=new ObservableField<>("");
    public ObservableField<String> date=new ObservableField<>("");
    public ObservableField<String> amount=new ObservableField<>("");
    public ObservableField<String> scheme=new ObservableField<>("");
    public ObservableField<String> schemeName=new ObservableField<>("");
    public ObservableField<String> netAmount=new ObservableField<>("");
    public ObservableField<String> selectDate=new ObservableField<>("Select Demand Date");

    public void setScheme(String scheme_code, String scheme_name, String net_amount) {
        this.scheme.set(scheme_code);
        this.netAmount.set(net_amount);
        this.schemeName.set(scheme_name);
    }

    public ActivityCalculatorBinding getBinding() {
        return binding;
    }

    public void setBinding(ActivityCalculatorBinding binding) {
        this.binding = binding;
    }

    public LiveData<CalculatorAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void initDatePicker(Context context)
    {
        new SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                         date.set(String.valueOf(year) + "-" + (String.valueOf(monthOfYear+ 1) ) + "-" + String.valueOf(dayOfMonth));
                        selectDate.set(String.valueOf(year) + "-" + (String.valueOf(monthOfYear+ 1) ) + "-" + String.valueOf(dayOfMonth));

                    }
                })

                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(Integer.parseInt(DataHolder.getInstance().loginData.getDYear()), (Integer.parseInt(DataHolder.getInstance().loginData.getDMonth())-1), (Integer.parseInt(DataHolder.getInstance().loginData.getDDay())))
                .minDate(Integer.parseInt(DataHolder.getInstance().loginData.getDYear()), (Integer.parseInt(DataHolder.getInstance().loginData.getDMonth())-1), (Integer.parseInt(DataHolder.getInstance().loginData.getDDay())))
                .maxDate(Integer.parseInt(DataHolder.getInstance().loginData.getDYear())+2, (Integer.parseInt(DataHolder.getInstance().loginData.getDMonth())+2), (Integer.parseInt(DataHolder.getInstance().loginData.getDDay())))
                .build()
                .show();
    }

    public void getCalculator() {
        Map<String, Object> jsonParams = new HashMap<>();
        //jsonParams.put("cust_id", sharedPreferences.getString("cust_id",""));
        jsonParams.put("Demanddate", date.get());
        jsonParams.put("Amount", amount.get());
        jsonParams.put("Scheme", scheme.get());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getCalculator(apiInterface,body);
    }

    public void clickCalculator(View view)
    {
        if (date.get().equals("") || amount.get().equals(""))
        {
            showSnakbar(view.getContext());
        }else {
            initLoading(view.getContext());
            getCalculator();
        }
    }

    private void showSnakbar(Context context) {

        Snackbar snackbar = Snackbar
                .make(binding.mainLayout, "Please select Demanding date", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
    }

    public void setData(Data data) {

        totalAmount.set("₹ "+data.getAmount().getTotalamount());
        totalDays.set(data.getAmount().getTotaldays());

    }

    public void clickDate(View view)
    {
        initDatePicker(view.getContext());
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
