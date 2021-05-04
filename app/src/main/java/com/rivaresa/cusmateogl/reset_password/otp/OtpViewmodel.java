package com.rivaresa.cusmateogl.reset_password.otp;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityOtpBinding;
import com.rivaresa.cusmateogl.reset_password.otp.action.OtpAction;
import com.rivaresa.cusmateogl.retrofit.ApiInterface;
import com.rivaresa.cusmateogl.retrofit.RetrofitClient;
import com.rivaresa.cusmateogl.utils.DataHolder;
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

public class OtpViewmodel extends AndroidViewModel {
    public OtpViewmodel(@NonNull Application application) {
        super(application);
        this.compositeDisposable=new CompositeDisposable();
        this.application=application;
        mACtion=new MutableLiveData<>();
        disposable=new CompositeDisposable();

        repository=OtpRepository.getInstance();
        repository.setDisposable(disposable);
        repository.setmACtion(mACtion);
    }
    Application application;
    Disposable compositeDisposable;
    CompositeDisposable disposable;
    MutableLiveData<OtpAction> mACtion;
    OtpRepository repository;
    ApiInterface apiInterface;
    ActivityOtpBinding binding;

    public MutableLiveData<OtpAction> getmACtion() {
        mACtion=repository.getmACtion();
        return mACtion;
    }

    public ObservableField<String> otptime=new ObservableField<>("");
    public ObservableField<String> os_otp=new ObservableField<>("");
    public ObservableField<String> from=new ObservableField<>("");
    public ObservableField<String> otpId=new ObservableField<>("");
    public void setTimer()
    {
        compositeDisposable = io.reactivex.Observable.interval(1, TimeUnit.SECONDS)
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

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getDisposable()).dispose();
        compositeDisposable.dispose();
        mACtion.setValue(new OtpAction(OtpAction.DEFAULT));
    }

    public void signUp() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("account_no", DataHolder.getInstance().sIgnupData.getAccountNo());
        jsonParams.put("mobile_no", DataHolder.getInstance().sIgnupData.getMobileNo());
        jsonParams.put("password", DataHolder.getInstance().sIgnupData.getPassword());
        jsonParams.put("OTP_ID", DataHolder.getInstance().sIgnupData.getOTPID());
        jsonParams.put("Name", DataHolder.getInstance().sIgnupData.getName());
        jsonParams.put("OTP", os_otp.get());

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String request=new JSONObject(jsonParams).toString();
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.signUp(apiInterface, body);
    }

    public void validateOtp() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Flag", "CHECK");
        jsonParams.put("OTP_ID", otpId.get());

        jsonParams.put("OTP", os_otp.get());

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String request=new JSONObject(jsonParams).toString();
        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.validateOtp(apiInterface, body);
    }

    public void clickSubmitOtp(View view)
    {
        if (os_otp.get().equals(""))
        {
            Toast.makeText(application, "please enter otp", Toast.LENGTH_SHORT).show();

        }
        else {

            if (from.get().equals("login"))
            {
                validateOtp();
            }else {
                signUp();
            }
        }
    }

    public void generateOtp() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("particular", "CUSTMATE_REG");
        jsonParams.put("account_no",DataHolder.getInstance().sIgnupData.getAccountNo());
        jsonParams.put("amount", "0");
        jsonParams.put("agent_id", "0");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.resendOtp(apiInterface, body);
    }

    public void resendLoginOtp() {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("Flag", "SEND");
        jsonParams.put("MobileNo",DataHolder.getInstance().loginData.getPhoneNum());

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        apiInterface = RetrofitClient.RetrofitClient().create(ApiInterface.class);
        repository.resendLoginOtp(apiInterface, body);
    }


    public void setBinding(ActivityOtpBinding binding) {
        this.binding=binding;
    }

    public void clickResendOtp(View view)
    {
        if (from.get().equals("login")) {
            initLoading(view.getContext());
            os_otp.set("");
            resendLoginOtp();
        }
        else if (from.get().equals("signup"))
        {
            initLoading(view.getContext());
            os_otp.set("");
            generateOtp();
        }

    }

    ProgressDialog loading;


    public void initLoading(Context context) {
        loading= Services.showProgressDialog(context);
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
