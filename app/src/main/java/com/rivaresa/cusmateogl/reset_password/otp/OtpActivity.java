package com.rivaresa.cusmateogl.reset_password.otp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityOtpBinding;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.login.LoginActivity;
import com.rivaresa.cusmateogl.reset_password.otp.action.OtpAction;
import com.rivaresa.cusmateogl.utils.DataHolder;

public class OtpActivity extends BaseActivity {
    OtpViewmodel viewmodel;
    ActivityOtpBinding binding;
    String from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        viewmodel = new ViewModelProvider(this).get(OtpViewmodel.class);
        binding.setViewmodel(viewmodel);
        viewmodel.setBinding(binding);
        viewmodel.setTimer();

        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("login") ||(from.equals("reset_password")))
        {
            viewmodel.otpId.set(intent.getStringExtra("otp_id"));
        }
        viewmodel.from.set(from);

        viewmodel.getmACtion().observe(this, new Observer<OtpAction>() {
            @Override
            public void onChanged(OtpAction otpAction) {
                viewmodel.cancelLoading();
                switch (otpAction.getAction()) {
                    case OtpAction.API_ERROR:
                        Toast.makeText(OtpActivity.this, otpAction.getError(), Toast.LENGTH_SHORT).show();
                        break;

                    case OtpAction.SIGNUP_SUCCESS:
                        if (from.equals("signup")) {
                            Toast.makeText(OtpActivity.this, otpAction.getSignUpResponse().getUser().getData().getMsg(), Toast.LENGTH_LONG).show();

                            Intent intent1 = new Intent(OtpActivity.this, LoginActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                        }else  if (from.equals("login"))
                        {
                            Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            finish();

                        }
                        break;

                    case OtpAction.RESEND_OTP_SUCCESS:
                        viewmodel.setTimer();
                        DataHolder.getInstance().sIgnupData.setOTPID(otpAction.getResendOtpResponse().getOtpData().getOtpId());
                        break;

                    case OtpAction.VALIDATE_OTP_SUCCESS:
                        Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                        break;

                    case OtpAction.RESEND_LOGIN_OTP_SUCCESS:
                        viewmodel.setTimer();
                        viewmodel.otpId.set(otpAction.otpCreationResponse.getOtp().getOtpId());
                        break;
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.mACtion.setValue(new OtpAction(OtpAction.DEFAULT));
    }

    @Override
    public void onBackPressed() {
      if (from.equals("signup"))
      {
          new AlertDialog.Builder(this)
                  .setTitle("Cancel?")
                  .setMessage("Do you want to cancel sign up?")
                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          startActivity(intent);
                          overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                          finish();
                      }

                  })
                  .setNegativeButton("No", null)
                  .show();
      }else if (from.equals("login")){
          Intent intent=new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
          overridePendingTransition(R.anim.fadein, R.anim.fadeout);
          finish();
      }
    }
}