package com.rivaresa.cusmateogl.reset_password.reset_password;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityResetPasswordBinding;
import com.rivaresa.cusmateogl.reset_password.reset_password.action.ResetPasswordAction;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ResetPasswordViewmodel extends AndroidViewModel {
    public ResetPasswordViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
        mAction= new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        disposable=new CompositeDisposable();
        repository.setCompositeDisposable(compositeDisposable);
        repository.setmAction(mAction);
    }

    public ObservableField<String> otpValue= new ObservableField<>("");
    public ObservableField<String> otpId= new ObservableField<>("");
    public ObservableField<String> newPassword= new ObservableField<>("");
    public ObservableField<String> reenterPassword= new ObservableField<>("");
    public ObservableField<String> accountNumber= new ObservableField<>("");
    public ObservableField<String> otptime=new ObservableField<>("");
    ActivityResetPasswordBinding binding;
    ApiInterface apiInterface;
    Application application;
    ResetPasswordRepository repository= ResetPasswordRepository.getInstance();
    Dialog loading;
    private MutableLiveData<ResetPasswordAction> mAction;
    private CompositeDisposable compositeDisposable;
    Disposable disposable;

    public void initLoading(Context context) {
        loading= Services.showLoading(context);
    }

    public void cancelLoading() {
        if (loading != null) {
            loading.cancel();
            loading = null;
        }
    }

    public MutableLiveData<ResetPasswordAction> getmAction() {
        return mAction;
    }

    public void setTimer()
    {
        disposable = io.reactivex.Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())

                .doOnNext(new Consumer<Long>() {
                    public void accept(Long x) throws Exception {
                        // update your view here
                        binding.tvOtpTime.setVisibility(View.VISIBLE);
                        otptime.set("in " +(60 - x) + " seconds");
                        binding.tvResendOtp.setClickable(false);
                        binding.tvResendOtp.setTextColor(application.getResources().getColor(R.color.black));
                    }
                })
                .takeUntil(aLong -> aLong == 60)
                .doOnComplete(new Action() {
                                  @Override
                                  public void run() throws Exception {

                                      binding.tvOtpTime.setVisibility(View.GONE);
                                      binding.tvResendOtp.setClickable(true);
                                      binding.tvResendOtp.setTextColor(application.getResources().getColor(R.color.l_red));
                                  }
                              }

                ).subscribe();


    }


    public void resetPassword() {

        Map<String, String> items = new HashMap<>();
        items.put("Accountno", accountNumber.get());
        items.put("Flag", "UPDATE");
        items.put("OTP_ID", otpId.get());
        items.put("OTP", otpValue.get());
        items.put("NewPassword", newPassword.get());

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(items)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.validateOtp(apiInterface,body);
    }

    public void resentOtp() {

        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("Accountno", accountNumber.get());
        items.put("Flag", "REQUEST_OTP");

        //params.put("data", encr.conRevString(Enc_Utils.enValues(items)));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(items)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.resendOtp(apiInterface,body);
    }

    public void clickResendOtp(View view)
    {
        resentOtp();
    }
    public void clickSubmit(View view)
    {
        if (otpValue.get().equals(""))
        {
            toast("Please enter OTP");
        }else if (newPassword.get().equals(""))
        {
            toast("Please enter password");
        }else if (reenterPassword.get().equals(""))
        {
            toast("Please Re-enter password");
        }else if (!newPassword.get().equals(reenterPassword.get()))
        {
            toast("Passwords do not match!");
        }else
        {
            initLoading(view.getContext());
            resetPassword();
        }
    }

    public void toast(String message){
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show();
    }

    public void setBinding(ActivityResetPasswordBinding binding) {
        this.binding=binding;
    }
}
