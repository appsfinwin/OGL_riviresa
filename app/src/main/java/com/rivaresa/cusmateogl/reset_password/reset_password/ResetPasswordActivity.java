package com.rivaresa.cusmateogl.reset_password.reset_password;

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
import com.rivaresa.cusmateogl.databinding.ActivityResetPasswordBinding;
import com.rivaresa.cusmateogl.login.LoginActivity;
import com.rivaresa.cusmateogl.reset_password.reset_password.action.ResetPasswordAction;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ResetPasswordActivity extends BaseActivity {

    ActivityResetPasswordBinding binding;
    ResetPasswordViewmodel viewmodel;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        viewmodel = new ViewModelProvider(this).get(ResetPasswordViewmodel.class);
        binding.setViewmodel(viewmodel);

        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("reset_password")) {
            viewmodel.otpId.set(intent.getStringExtra("otp_id"));
            viewmodel.accountNumber.set(intent.getStringExtra("account_number"));
        }
        viewmodel.setBinding(binding);
        viewmodel.setTimer();
        viewmodel.getmAction().observe(this, new Observer<ResetPasswordAction>() {
            @Override
            public void onChanged(ResetPasswordAction resetPasswordAction) {
                viewmodel.cancelLoading();
                switch (resetPasswordAction.getAction()) {

                    case ResetPasswordAction.RESET_OTP_SUCCESS:

                        View customView = LayoutInflater.from(ResetPasswordActivity.this).inflate(R.layout.layout_error_layout, null);
                        TextView tv_error = customView.findViewById(R.id.tv_error);
                        tv_error.setText("Password changed Successfully!");
                        SweetAlertDialog dialog;
                        dialog = new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        dialog.setTitleText("Success!");
                        dialog.setCustomView(customView);
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent1 = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;

                    case ResetPasswordAction.API_ERROR:
                        View customView_ = LayoutInflater.from(ResetPasswordActivity.this).inflate(R.layout.layout_error_layout, null);
                        TextView tv_error_ = customView_.findViewById(R.id.tv_error);
                        tv_error_.setText(resetPasswordAction.getError());

                        new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error!")
                                .setCustomView(customView_)
                                .show();
                        break;


                    case ResetPasswordAction.RESEND_OTP_SUCCESS:
                        viewmodel.otpId.set(resetPasswordAction.getResetPasswordResponse().getData().getTable1().get(0).getReturnID());
                        //viewmodel.accountNumber.set(intent.getStringExtra("account_number"));
                        viewmodel.setTimer();
                        viewmodel.otpValue.set("");

                        break;
                }
            }
        });
    }
}