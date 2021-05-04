package com.rivaresa.cusmateogl.renew_loan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityRenewLoanBinding;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.renew_loan.action.RenewLoanAction;


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
                        break;

                    case RenewLoanAction.RENEW_SUCCES:
                        viewmodel.setReceipt(renewLoanAction.getRenewalResponse().getData());
                        break;

                    case RenewLoanAction.CLICK_OK:
                        Intent intent1 = new Intent(RenewLoanActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case RenewLoanAction.API_ERROR:
                        showError(renewLoanAction.getError());
                        break;
                }
            }
        });
    }

    public void showError(String error) {

        warningDialog = new Dialog(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_popup, null);
        TextView errorMessage = view.findViewById(R.id.txt_msg);
        TextView ok = view.findViewById(R.id.tv_email);
        errorMessage.setText(error);

        ok.setText("OK");
        ok.setTextColor(getResources().getColor(R.color.colorPrimary));
        ok.setTextSize(16);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                warningDialog.dismiss();
            }
        });
        warningDialog.setContentView(view);
        //warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        warningDialog.getWindow().setLayout((WindowManager.LayoutParams.MATCH_PARENT), WindowManager.LayoutParams.WRAP_CONTENT);
        warningDialog.setCanceledOnTouchOutside(false);
        warningDialog.setCancelable(true);
        warningDialog.show();

        warningDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                finish();
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}