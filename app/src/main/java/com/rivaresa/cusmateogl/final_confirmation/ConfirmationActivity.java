package com.rivaresa.cusmateogl.final_confirmation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivityConfirmationBinding;
import com.rivaresa.cusmateogl.final_confirmation.action.ConfirmationAction;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.renew_loan.RenewLoanActivity;

public class ConfirmationActivity extends BaseActivity {

    ActivityConfirmationBinding binding;
    ConfirmationViewmodel viewmodel;
    String scheme_code, scheme_name, scheme_interest, scheme_period, net_amount, loanAmount, inventoryNo, customerId, scheme_period_type;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation);
        viewmodel = new ViewModelProvider(this).get(ConfirmationViewmodel.class);
        binding.setViewmodel(viewmodel);

        viewmodel.initLoading(this);
        Intent intent = getIntent();
        if (intent != null) {
            net_amount = intent.getStringExtra("net_amount");
            scheme_code = intent.getStringExtra("scheme_code");
            scheme_name = intent.getStringExtra("scheme_name");
            scheme_interest = intent.getStringExtra("scheme_interest");
            scheme_period = intent.getStringExtra("scheme_period");
            loanAmount = intent.getStringExtra("loan_amount");
            scheme_period_type = intent.getStringExtra("scheme_period_type");
            inventoryNo = sharedPreferences.getString("inventory_number", "");
            customerId = sharedPreferences.getString("cust_id", "");
            viewmodel.getAppliactionDetails(inventoryNo, scheme_code, customerId, loanAmount);
        }
        binding.parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                findViewById(R.id.terms_scrollview).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        binding.termsScrollview.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        viewmodel.getmAction().observe(this, new Observer<ConfirmationAction>() {
            @Override
            public void onChanged(ConfirmationAction confirmationAction) {
                viewmodel.cancelLoading();
                switch (confirmationAction.getAction()) {
                    case ConfirmationAction.APPLICATION_DETAILS_SUCCESS:
                        if (confirmationAction.applictaionDetailsResponse.getPersonalDetails() != null &&
                                confirmationAction.applictaionDetailsResponse.getInventoryDetails() != null &&
                                confirmationAction.applictaionDetailsResponse.getPledgeDetails() != null &&
                                confirmationAction.applictaionDetailsResponse.getTermsConditions() != null
                        ) {
                            viewmodel.setData(confirmationAction.getApplictaionDetailsResponse());
                        }
                        break;

                    case ConfirmationAction.CLICK_CANCEL:
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case ConfirmationAction.CLICK_CONFIRM:
                        Intent intent1 = new Intent(ConfirmationActivity.this, RenewLoanActivity.class);
                        //Intent intent1=new Intent(ConfirmationActivity.this, AxisPaymentActivity.class);

                        intent1.putExtra("from", "gold_loan");
                        intent1.putExtra("scheme_code", scheme_code);
                        intent1.putExtra("scheme_name", scheme_name);
                        intent1.putExtra("scheme_interest", scheme_interest);
                        intent1.putExtra("loan_amount", viewmodel.loanAmount.get());
                        intent1.putExtra("scheme_period", scheme_period);
                        intent1.putExtra("net_amount", net_amount);
                        intent1.putExtra("scheme_period_type", scheme_period_type);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case ConfirmationAction.CLICK_SETTINGS:
                        exitDialog();
                        break;

                    case ConfirmationAction.API_ERROR:
                        break;
                }
            }
        });
    }

    private void exitDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit?")
                .setCancelable(false)
                .setMessage("Do you want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
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
        //super.onBackPressed();
        exitDialog();
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}