package com.rivaresa.cusmateogl.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivitySignupBinding;
import com.rivaresa.cusmateogl.login.LoginActivity;
import com.rivaresa.cusmateogl.reset_password.otp.OtpActivity;
import com.rivaresa.cusmateogl.signup.action.SignupAction;

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
                        Toast.makeText(SignupActivity.this, signupAction.getError(), Toast.LENGTH_LONG).show();
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