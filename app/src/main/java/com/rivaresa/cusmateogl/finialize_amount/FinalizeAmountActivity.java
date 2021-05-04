package com.rivaresa.cusmateogl.finialize_amount;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.calculator.CalculatorActivity;
import com.rivaresa.cusmateogl.databinding.ActivityFinalizeAmountBinding;
import com.rivaresa.cusmateogl.final_confirmation.ConfirmationActivity;
import com.rivaresa.cusmateogl.finialize_amount.action.FinalizeAmountAction;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.payment.paytm.adapter.PaymentsAdapter;

public class FinalizeAmountActivity extends BaseActivity {

    FinalizeAmountViewmodel viewmodel;
    ActivityFinalizeAmountBinding binding;
    PaymentsAdapter adapter;
    String scheme_code, scheme_name, scheme_interest, scheme_period, net_amount, scheme_period_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finalize_amount);
        viewmodel = new ViewModelProvider(this).get(FinalizeAmountViewmodel.class);
        binding.setViewmodel(viewmodel);
        viewmodel.setBinding(binding);

        Intent intent = getIntent();
        if (intent != null) {
            binding.tvNetAmount.setText(intent.getStringExtra("net_amount"));
            viewmodel.maxAmount.set(Double.parseDouble(intent.getStringExtra("net_amount")));
            net_amount = intent.getStringExtra("net_amount");
            scheme_code = intent.getStringExtra("scheme_code");
            scheme_name = intent.getStringExtra("scheme_name");
            scheme_interest = intent.getStringExtra("scheme_interest");
            scheme_period = intent.getStringExtra("scheme_period");
            scheme_period_type = intent.getStringExtra("scheme_period_type");

        }
        setupRecyclerView(binding.rvSettlement);
        viewmodel.initLoading(this);
        viewmodel.getSettlementDetails();

        viewmodel.getmAction().observe(this, new Observer<FinalizeAmountAction>() {
            @Override
            public void onChanged(FinalizeAmountAction finalizeAmountAction) {
                viewmodel.cancelLoading();
                switch (finalizeAmountAction.getAction()) {
                    case FinalizeAmountAction.SETTLEMENT_ERROR:
                        break;

                    case FinalizeAmountAction.SETTLEMENT_SUCCESS:
                        adapter.setSettlementDataList(finalizeAmountAction.getSettlementDetailsResponse().getData());
                        adapter.notifyDataSetChanged();
                        break;

                    case FinalizeAmountAction.CLICK_CALCULATOR:
                        Intent intent1 = new Intent(FinalizeAmountActivity.this, CalculatorActivity.class);
                        intent1.putExtra("scheme_code", scheme_code);
                        intent1.putExtra("scheme_name", scheme_name);
                        intent1.putExtra("scheme_interest", scheme_interest);
                        intent1.putExtra("scheme_period", scheme_period);
                        intent1.putExtra("net_amount", net_amount);
                        intent1.putExtra("scheme_period_type", scheme_period_type);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case FinalizeAmountAction.CLICK_NEXT:
                        Intent intentNext = new Intent(FinalizeAmountActivity.this, ConfirmationActivity.class);
                        intentNext.putExtra("scheme_code", scheme_code);
                        intentNext.putExtra("scheme_name", scheme_name);
                        intentNext.putExtra("scheme_interest", scheme_interest);
                        intentNext.putExtra("loan_amount", viewmodel.loanAmount.get());
                        intentNext.putExtra("scheme_period", scheme_period);
                        intentNext.putExtra("net_amount", net_amount);
                        intentNext.putExtra("scheme_period_type", scheme_period_type);
                        startActivity(intentNext);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case FinalizeAmountAction.CLICK_SETTINGS:
                        exitDialog();
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
                        Intent intent = new Intent(FinalizeAmountActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new PaymentsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.reset();
    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        exitDialog();
    }
}