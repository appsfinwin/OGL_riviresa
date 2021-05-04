package com.rivaresa.cusmateogl.select_payment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivitySelectPaymentBinding;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.payment.paytm.PaymentActivity;

public class SelectPaymentActivity extends BaseActivity {

    ActivitySelectPaymentBinding binding;
    SelectPaymentViewmodel viewmodel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String account_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        binding= DataBindingUtil.setContentView(this, R.layout.activity_select_payment);
        viewmodel=new ViewModelProvider(this).get(SelectPaymentViewmodel.class);
        binding.setViewmodel(viewmodel);

        binding.tvName.setText(sharedPreferences.getString("name",""));
        binding.tvEmail.setText(sharedPreferences.getString("email",""));
        binding.tvPhone.setText(sharedPreferences.getString("phone",""));
        account_number=sharedPreferences.getString("account_number","");


        viewmodel.getmAction().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s)
                {
                    case "interest_payment":
                        Intent intent=new Intent(SelectPaymentActivity.this, PaymentActivity.class);
                        intent.putExtra("payment_type","interest_payment");
                        intent.putExtra("account_number",account_number);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;
                    case "part_payment":

                        Intent part_intent=new Intent(SelectPaymentActivity.this, PaymentActivity.class);
                        part_intent.putExtra("payment_type","part_payment");
                        part_intent.putExtra("account_number",account_number);
                        startActivity(part_intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;
                    case "full_payment":
                        Intent full_intent=new Intent(SelectPaymentActivity.this, PaymentActivity.class);
                        full_intent.putExtra("payment_type","full_payment");
                        full_intent.putExtra("account_number",account_number);
                        startActivity(full_intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case "settings":
                        exitDialog();
                        break;

                    case "default":
                        break;
                }
            }
        });
    }

    private void exitDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit?")
                .setMessage("Do you want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SelectPaymentActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.reset();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}