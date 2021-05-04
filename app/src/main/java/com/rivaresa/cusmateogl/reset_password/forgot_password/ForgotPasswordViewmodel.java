package com.rivaresa.cusmateogl.reset_password.forgot_password;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.supporting_class.Enc_crypter;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ForgotPasswordViewmodel extends AndroidViewModel {
    public ForgotPasswordViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        repository= ForgotPasswordRepository.getInstance();
        mAction=new MutableLiveData<>();
        compositeDisposable=new CompositeDisposable();

        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);
    }

    public ObservableField<String> phoneNumber=new ObservableField<>("");
    Application application;
    ForgotPasswordRepository repository;
    MutableLiveData<ForgotPasswordAction> mAction;
    CompositeDisposable compositeDisposable;

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

    public MutableLiveData<ForgotPasswordAction> getmAction() {
        return mAction;
    }

    public void clickSentOtp(View view)
    {
        int phone=phoneNumber.get().length();
        if (phoneNumber.get().equals(""))
        {
            Toast.makeText(application, "Account Number cannot be empty", Toast.LENGTH_SHORT).show();
        }else {
            initLoading(view.getContext());
            sentOtp();
        }
    }

    final Enc_crypter encr = new Enc_crypter();

    ApiInterface apiInterface;

    public void sentOtp() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("Accountno", phoneNumber.get());
        items.put("Flag", "REQUEST_OTP");

        //params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(items)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.sentOtp(apiInterface,body);
    }
}
