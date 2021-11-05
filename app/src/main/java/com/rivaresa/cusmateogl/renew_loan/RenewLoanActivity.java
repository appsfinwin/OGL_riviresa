package com.rivaresa.cusmateogl.renew_loan;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityRenewLoanBinding;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.renew_loan.action.RenewLoanAction;
import com.rivaresa.cusmateogl.utils.Services;


public class RenewLoanActivity extends BaseActivity {

    ActivityRenewLoanBinding binding;
    RenewLoanViewmodel viewmodel;
    String flag, scheme_code, scheme_name, scheme_interest, scheme_period, net_amount,
            scheme_period_type, loanAmount, inventoryNo, customerId, loan_account_number,
            ifsc = "", bank_account_number = "", CustBankId = "";
    SharedPreferences sharedPreferences;
    Dialog warningDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_renew_loan);
        viewmodel = new ViewModelProvider(this).get(RenewLoanViewmodel.class);
        binding.setViewmodel(viewmodel);
        binding.mainLayout.setVisibility(View.GONE);
        if (intent != null) {

            if (intent.getStringExtra("from").equals("gold_loan")) {
                net_amount = intent.getStringExtra("net_amount");
                scheme_code = intent.getStringExtra("scheme_code");
                scheme_name = intent.getStringExtra("scheme_name");
                scheme_interest = intent.getStringExtra("scheme_interest");
                scheme_period = intent.getStringExtra("scheme_period");
                loanAmount = intent.getStringExtra("loan_amount");
                scheme_period_type = intent.getStringExtra("scheme_period_type");
                inventoryNo = sharedPreferences.getString("inventory_number", "");
                customerId = sharedPreferences.getString("cust_id", "");
                loan_account_number = sharedPreferences.getString("account_number", "");


                ifsc = sharedPreferences.getString("bankIfsc", "");
                bank_account_number = sharedPreferences.getString("bankAccountNumber", "");
                CustBankId = sharedPreferences.getString("CustBankId", "");

                viewmodel.initLoading(this);
                viewmodel.renewGoldLoan(
                        loanAmount,
                        ifsc,
                        loan_account_number,
                        scheme_code,
                        bank_account_number,
                        scheme_period,
                        scheme_period_type,
                        scheme_interest,
                        CustBankId
                );
            } else if (intent.getStringExtra("from").equals("pay_online")) {
                loan_account_number = sharedPreferences.getString("account_number", "");
                net_amount = intent.getStringExtra("net_amount");
                flag = intent.getStringExtra("flag");
                viewmodel.initLoading(this);
                viewmodel.renewLoan(net_amount, loan_account_number, flag, CustBankId);
            }
        }


        viewmodel.getmAction().observe(this, new Observer<RenewLoanAction>() {
            @Override
            public void onChanged(RenewLoanAction renewLoanAction) {
                viewmodel.cancelLoading();

                switch (renewLoanAction.getAction()) {
                    case RenewLoanAction.GOLD_LOAN_RENEW_SUCCESS:
                        viewmodel.setGoldLoanReceipt(renewLoanAction.getGoldLoanResponse().getData());
                        binding.mainLayout.setVisibility(View.VISIBLE);
                        break;

                    case RenewLoanAction.RENEW_SUCCES:
                        viewmodel.setReceipt(renewLoanAction.getRenewalResponse().getData());
                        binding.mainLayout.setVisibility(View.VISIBLE);
                        break;

                    case RenewLoanAction.CLICK_OK:
                        Intent intent1 = new Intent(RenewLoanActivity.this, HomeActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case RenewLoanAction.API_ERROR:
                        Services.errorDialog(RenewLoanActivity.this, renewLoanAction.getError());
                        //showError(renewLoanAction.getError());
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(RenewLoanActivity.this, HomeActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent1);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}