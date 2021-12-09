package com.riviresa.custmate.ogl.signup;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.riviresa.custmate.R;
import com.riviresa.custmate.databinding.ActivitySignupBinding;
import com.riviresa.custmate.ogl.BaseActivity;
import com.riviresa.custmate.ogl.login.LoginActivity;
import com.riviresa.custmate.ogl.reset_password.otp.OtpActivity;
import com.riviresa.custmate.ogl.signup.action.SignupAction;
import com.riviresa.custmate.ogl.utils.Services;

public class SignupActivity extends BaseActivity {

    SignupViewmodel viewmodel;
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        viewmodel = new ViewModelProvider(this).get(SignupViewmodel.class);
        binding.setViewmodel(viewmodel);

        viewmodel.setBinding(binding);
        viewmodel.getmAction().observe(this, new Observer<SignupAction>() {
            @Override
            public void onChanged(SignupAction signupAction) {
                viewmodel.cancelLoading();
                switch (signupAction.getAction()) {
                    case SignupAction.CLICK_BACK:
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case SignupAction.CLICK_SIGN_IN:
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                        break;

                    case SignupAction.OTP_SUCCESS:
                        viewmodel.setSignupData(signupAction.getOtpGenerateResponse().getOtpData().getOtpId());
                        Intent otpIntent = new Intent(SignupActivity.this, OtpActivity.class);
                        otpIntent.putExtra("from", "signup");
                        startActivity(otpIntent);
                        break;

                    case SignupAction.API_ERROR:
                        Services.errorDialog(SignupActivity.this, signupAction.getError());

                        break;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.mAction.setValue(new SignupAction(SignupAction.DEFAULT));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}