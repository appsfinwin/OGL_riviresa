package com.rivaresa.cusmateogl.reset_password.forgot_password;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityForgotPasswordBinding;
import com.rivaresa.cusmateogl.reset_password.reset_password.ResetPasswordActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgotPasswordActivity extends BaseActivity {
    ForgotPasswordViewmodel viewmodel;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        viewmodel = new ViewModelProvider(this).get(ForgotPasswordViewmodel.class);
        binding.setViewmodel(viewmodel);


        viewmodel.getmAction().observe(this, new Observer<ForgotPasswordAction>() {
            @Override
            public void onChanged(ForgotPasswordAction forgotPasswordAction) {
                viewmodel.cancelLoading();
                switch (forgotPasswordAction.getAction()) {
                    case ForgotPasswordAction.GENERATE_OTP_SUCCESS:

                        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("from", "reset_password");
                        intent.putExtra("account_number", viewmodel.phoneNumber.get());
                        intent.putExtra("otp_id", forgotPasswordAction.getResetPasswordResponse().getData().getTable1().get(0).getReturnID());
                        startActivity(intent);
                        break;

                    case ForgotPasswordAction.API_ERROR:

                        View customView_ = LayoutInflater.from(ForgotPasswordActivity.this).inflate(R.layout.layout_error_layout, null);
                        TextView tv_error_ = customView_.findViewById(R.id.tv_error);
                        tv_error_.setText( forgotPasswordAction.getError());

                        new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error!")
                                .setCustomView(customView_)
                                .show();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}