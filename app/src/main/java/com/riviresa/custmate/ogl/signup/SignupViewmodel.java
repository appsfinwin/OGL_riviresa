package com.riviresa.custmate.ogl.signup;

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
import com.riviresa.custmate.databinding.ActivitySignupBinding;
import com.riviresa.custmate.ogl.retrofit.ApiInterface;
import com.riviresa.custmate.ogl.retrofit.RetrofitClient;
import com.riviresa.custmate.ogl.signup.action.SignupAction;
import com.riviresa.custmate.ogl.signup.pojo.otp_response.signup_data.SIgnupData;
import com.riviresa.custmate.ogl.utils.DataHolder;
import com.riviresa.custmate.ogl.utils.Services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignupViewmodel extends AndroidViewModel {
    public SignupViewmodel(@NonNull Application application) {
        super(application);
        repository = SignupRepository.getInstance();
        mAction = new MutableLiveData<>();
        disposable=new CompositeDisposable();

        repository.setCompositeDisposable(disposable);
        repository.setmAction(mAction);
        apiInterface= RetrofitClient.getApi();
    }
    ActivitySignupBinding binding;

    SignupRepository repository;
    ApiInterface apiInterface;
    MutableLiveData<SignupAction> mAction;
    CompositeDisposable disposable;

    public ObservableField<String> os_accountNumber = new ObservableField<>("");
    public ObservableField<String> os_name = new ObservableField<>("");
    public ObservableField<String> os_mobileNumber = new ObservableField<>("");
    public ObservableField<String> os_password = new ObservableField<>("");
    public ObservableField<String> os_confirmPassword = new ObservableField<>("");

    public LiveData<SignupAction> getmAction() {
        mAction=repository.getmAction();
        return mAction;
    }

    public void clickBack(View view) {
        mAction.setValue(new SignupAction((SignupAction.CLICK_BACK)));
    }

    public void clickSignIn(View view) {
        mAction.setValue(new SignupAction((SignupAction.CLICK_SIGN_IN)));
    }

    public void clickSignUp(View view) {
        if (
                      ( !os_accountNumber.get().equals("") &&
                        !os_name.get().equals("") &&
                        //!os_mobileNumber.get().equals("") &&
                        !os_password.get().equals("") &&
                        !os_confirmPassword.get().equals("") &&
                        (os_password.get().equals(os_confirmPassword.get()))
                        //os_mobileNumber.get().length() == 10
                      )

        ) {
//            mAction.setValue(new SignupAction((SignupAction.CLICK_SIGN_UP)));
            initLoading(view.getContext());
            generateOtp();
        }else {
            if (os_accountNumber.get().equals("")) {
                showSnakbar(view,"Account number cannot be empty");
            }else if (os_name.get().equals(""))
            {
                showSnakbar(view,"Name cannot be empty");
            }
//            else if (os_mobileNumber.get().equals(""))
//            {
//                showSnakbar("Phone be empty");
//            }
//            else if ( os_mobileNumber.get().length() != 10)
//            {
//                showSnakbar("Enter a valid phone number!!");
//            }
            else if (os_password.get().equals("") ||os_confirmPassword.get().equals(""))
            {
                showSnakbar(view,"Password be empty");
            }
            else if (!os_password.get().equals(os_confirmPassword.get()))
            {
                showSnakbar(view,"Passwords do not match!!");
            }
        }

    }

    private void showSnakbar(View view,String message) {

        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void setBinding(ActivitySignupBinding binding) {
        this.binding=binding;
    }

    public void generateOtp()
    {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("particular", "CUSTMATE_REG");
        jsonParams.put("account_no", os_accountNumber.get());
        jsonParams.put("amount", "0");
        jsonParams.put("agent_id", "0");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String request=new JSONObject(jsonParams).toString();
        repository.otpGenerate(apiInterface, body);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        (repository.getCompositeDisposable()).dispose();
        mAction.setValue(new SignupAction(SignupAction.DEFAULT));
    }

    public void setSignupData(String otpId)
    {
        DataHolder.getInstance().sIgnupData=new SIgnupData(os_name.get(),os_accountNumber.get(),os_mobileNumber.get(),os_password.get(),otpId);
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
