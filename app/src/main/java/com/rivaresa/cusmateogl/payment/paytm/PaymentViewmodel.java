package com.rivaresa.cusmateogl.payment.paytm;

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
import com.rivaresa.cusmateogl.databinding.ActivityPaymentBinding;
import com.rivaresa.cusmateogl.payment.action.PaymentAction;
import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementData;
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
import retrofit2.Retrofit;

public class PaymentViewmodel extends AndroidViewModel {
    public PaymentViewmodel(@NonNull Application application) {
        super(application);

        compositeDisposable = new CompositeDisposable();
        repository = PaymentRepository.getInstance();
        this.application = application;
        repository.setDisposible(compositeDisposable);
        mAction = new MutableLiveData<>();
    }

    ActivityPaymentBinding binding;
    PaymentRepository repository;
    Application application;
    CompositeDisposable compositeDisposable;
    MutableLiveData<PaymentAction> mAction;
    Retrofit retrofit;
    ApiInterface apiInterface;

    public ObservableField<String> balance = new ObservableField<>("");
    public ObservableField<String> amountToPay = new ObservableField<>("");
    public ObservableField<Double> amountToPayInt = new ObservableField<>(0.0);
    public ObservableField<String> paymentMode = new ObservableField<>("");

    @Override
    protected void onCleared() {
        super.onCleared();

        (repository.getDisposible()).dispose();
    }

    public LiveData<PaymentAction> getmAction() {
        mAction = repository.getmAction();
        return mAction;
    }

    public void getSettlementDetails(String flag, String account_number) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Accountno", account_number);
        jsonParams.put("flag", "");


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.getSettlementDetails(apiInterface, body);
    }

    public void reset() {
        mAction.setValue(new PaymentAction(PaymentAction.DEFAULT));
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

    public void clickSettings(View view)
    {
        mAction.setValue(new PaymentAction(PaymentAction.CLICK_SETTINGS));
    }
    public void setBalance(List<SettlementData> settlementData) {
        double bal = 0;
        for (SettlementData data : settlementData) {
            bal = bal + Double.parseDouble(data.getBalance());
        }

        balance.set(String.valueOf(bal));

    }

    public void setAmount(List<SettlementData> settlementData) {
        double bal = 0;
        for (SettlementData data : settlementData) {
            String particular = data.getParticular();
            particular = data.getParticular();
            if (!particular.equals("Principal Amount")) {
                bal = bal + Double.parseDouble(data.getBalance());
            }
        }

        amountToPay.set(String.valueOf(bal));
        amountToPayInt.set(bal);
    }

    public void setFullAmount() {
        amountToPay.set(balance.get());
        amountToPayInt.set(Double.parseDouble(balance.get()));
    }

    public void setChecksum() {

        Map<String, Object> jobj = new HashMap<>();
//        jsonParams.put("Accountno", account_number);
//        jsonParams.put("flag", "");
        jobj.put("MID", "sYwTAG93503854487535");
        jobj.put("ORDER_ID", "202004251336551111");
        jobj.put("CUST_ID", "ANVINDIGITALSERVICE");
        jobj.put("INDUSTRY_TYPE_ID", "Retail");
        jobj.put("CHANNEL_ID", "WAP");
        jobj.put("TXN_AMOUNT", "100");
        jobj.put("WEBSITE", "WEBSTAGING");
        jobj.put("EMAIL", "leslinantony996@gmail.com");
        jobj.put("MOBILE_NO", "8714155345");
        jobj.put("CALLBACK_URL", "https://securegw-stage.paytm.in/order/process/theia/paytmCallback?ORDER_ID=202004251336551111");

        // https://securegw-stage.paytm.in/order/process/theia/paytmCallback?ORDER_ID="+orderIdString
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jobj)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        //repository.getChecksum(apiInterface,body);

        mAction.setValue(new PaymentAction(PaymentAction.CHECKSUM_SUCCESS));
    }

    public void clickPaytm(View view) {
//        setChecksum();
        amountToPayInt.set(Double.parseDouble(amountToPay.get()));
        if (paymentMode.get().equals("part_payment") && amountToPay.get().equals("")) {
            showSnakbar("Please enter amount!", view);
        }
        else if (amountToPayInt.get() <= 0) {
            showSnakbar(amountToPay.get() + " is not a valid amount!", view);
        }
        else {
            mAction.setValue(new PaymentAction(PaymentAction.CLICK_PAY));
        }
    }

    private void showSnakbar(String message, View view) {

        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public void setBinding(ActivityPaymentBinding binding) {
        this.binding = binding;
    }
}
