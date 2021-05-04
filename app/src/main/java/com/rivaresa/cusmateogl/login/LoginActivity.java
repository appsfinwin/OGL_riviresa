package com.rivaresa.cusmateogl.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityLoginBinding;
import com.rivaresa.cusmateogl.login.action.LoginAction;
import com.rivaresa.cusmateogl.reset_password.forgot_password.ForgotPasswordActivity;
import com.rivaresa.cusmateogl.reset_password.otp.OtpActivity;
import com.rivaresa.cusmateogl.signup.SignupActivity;
import com.rivaresa.cusmateogl.supporting_class.ConstantClass;
import com.rivaresa.cusmateogl.utils.DataHolder;

public class LoginActivity extends BaseActivity {

    LoginViewmodel viewmodel;
    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewmodel = new ViewModelProvider(this).get(LoginViewmodel.class);
        viewmodel.setActivity(this);
        binding.setViewmodel(viewmodel);

//        String address = Services.getMacAddr();
////        Toast.makeText(this, address, Toast.LENGTH_SHORT).show();

        viewmodel.getLoginLivedata().observe(this, new Observer<LoginAction>() {
            @Override
            public void onChanged(LoginAction loginAction) {

                viewmodel.cancelLoading();
                switch (loginAction.getAction()) {
                    case LoginAction.LOGIN_SUCCESS:

                        if (loginAction.getLoginResponse().getBankDetails().size() > 0) {
                            //DataHolder.getInstance().bankDetails = (loginAction.getLoginResponse().getBankDetails());
                            //DataHolder.getInstance().selectedBankData = loginAction.getLoginResponse().getBankDetails().get(0);
                        }

                        DataHolder.getInstance().loginData = (loginAction.getLoginResponse().getLoginData());


                        editor.putString("name", loginAction.loginResponse.getLoginData().getName());
                        editor.putString("email", loginAction.loginResponse.getLoginData().getEmailId());
                        editor.putString("phone", loginAction.loginResponse.getLoginData().getPhoneNum());
                        editor.putString("cust_id", loginAction.loginResponse.getLoginData().getCustId());
                        editor.putBoolean(ConstantClass.IS_LOGIN,false);
                        editor.commit();

                        viewmodel.initLoading(LoginActivity.this);
                        viewmodel.generateOtp(loginAction.loginResponse.getLoginData().getPhoneNum());


                        break;

                    case LoginAction.OTP_SUCCESS:

                        viewmodel.cancelLoading();
                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        intent.putExtra("from","login");
                        intent.putExtra("otp_id",loginAction.otpCreationResponse.getOtp().getOtpId());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();

                        break;
                    case LoginAction.API_ERROR:
                        viewmodel.ErrorDialoge(loginAction.getError());
                        break;
                    case LoginAction.CLICK_FORGOT_PASSWORD:

                        Intent forgotIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                        //Intent forgotIntent=new Intent(LoginActivity.this, ResetPasswordActivity.class);
                        startActivity(forgotIntent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//                        View customView= LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_error_layout,null);
//                        TextView tv_error=customView.findViewById(R.id.tv_error);
//                        tv_error.setText("Customer executive will contact you soon");
//                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText("Request has been made!")
//                                .setCustomView(customView)
//                                .show();
                        break;

                    case LoginAction.CLICK_SIGN_UP:

                        Intent signuptIntent = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(signuptIntent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;


                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.mAction.setValue(new LoginAction(LoginAction.DEFAULT));
    }
}