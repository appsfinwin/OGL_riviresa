package com.rivaresa.cusmateogl.gold_loan.select_scheme;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.ActivitySelectSchemeBinding;
import com.rivaresa.cusmateogl.databinding.DialogLayoutSettlementBinding;
import com.rivaresa.cusmateogl.finialize_amount.FinalizeAmountActivity;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.action.RowSchemeAction;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.action.SelectSchemeAction;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.adapter.SchemeAdapter;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.dialog.SettlementDialogViewmodel;
import com.rivaresa.cusmateogl.payment.paytm.adapter.PaymentsAdapter;

public class SelectSchemeActivity extends AppCompatActivity {

    ActivitySelectSchemeBinding binding;
    SelectSchemeViewmodel viewmodel;
    SchemeAdapter adapter;
    String account_number;
    SharedPreferences sharedPreferences;
    Dialog settlementDialog;
    DialogLayoutSettlementBinding dialogBinding;
    SettlementDialogViewmodel dialogueViewmodel;
    PaymentsAdapter paymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_scheme);
        viewmodel = new ViewModelProvider(this).get(SelectSchemeViewmodel.class);
        binding.setViewmodel(viewmodel);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        account_number = sharedPreferences.getString("account_number", "");
        setupRecyclerView(binding.rvSchemes);
        viewmodel.initLoading(this);
        viewmodel.getSchemes();
        initDialoge();
        viewmodel.getmAction().observe(this, new Observer<SelectSchemeAction>() {
            @Override
            public void onChanged(SelectSchemeAction selectSchemeAction) {
                viewmodel.cancelLoading();
                switch (selectSchemeAction.getAction()) {
                    case SelectSchemeAction.SCHEMES_ERROR:
                        break;

                    case SelectSchemeAction.SCHEMES_SUCCESS:
                        adapter.setSchemeData(selectSchemeAction.getSchemesResponse().getData());
                        adapter.notifyDataSetChanged();
                        break;

                    case SelectSchemeAction.SETTLEMENT_SUCCESS:
                        paymentAdapter.setSettlementDataList(selectSchemeAction.getSettlementDetailsResponse().getData());
                        paymentAdapter.notifyDataSetChanged();
                        settlementDialog.show();
                        break;
                }
            }
        });

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new SchemeAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setObservable(adapter);
    }

    private void setObservable(SchemeAdapter adapter) {
        adapter.getmAction().observe(this, new Observer<RowSchemeAction>() {
            @Override
            public void onChanged(RowSchemeAction rowSchemeAction) {
                switch (rowSchemeAction.getAction()) {
                    case RowSchemeAction.CLICK_SELECT:
                        //if (rowSchemeAction.getSchemeData().getPeriod().equals("")) {
                            Intent intent = new Intent(SelectSchemeActivity.this, FinalizeAmountActivity.class);
                            intent.putExtra("net_amount", rowSchemeAction.getSchemeData().getNetAmtAvailable());
                            intent.putExtra("scheme_code", rowSchemeAction.getSchemeData().getSchemecode());
                            intent.putExtra("scheme_name", rowSchemeAction.getSchemeData().getName());
                            intent.putExtra("scheme_interest", rowSchemeAction.getSchemeData().getInterest());
                            intent.putExtra("scheme_period", rowSchemeAction.getSchemeData().getPeriod());
                            intent.putExtra("scheme_period_type", rowSchemeAction.getSchemeData().getPeriodtype());
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                        }else {
//                            Toast.makeText(SelectSchemeActivity.this, "Scheme interest is empty", Toast.LENGTH_SHORT).show();
//                        }
                        break;

                    case RowSchemeAction.CLICK_SETTLEMENT:
                        viewmodel.getSettlementDetails();
                        break;

                }
            }
        });
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

    private void initDialoge() {

        settlementDialog = new Dialog(this);
        dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_layout_settlement, null, false);
        dialogueViewmodel = new SettlementDialogViewmodel();
        settlementDialog.setContentView(dialogBinding.getRoot());
        settlementDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels), WindowManager.LayoutParams.WRAP_CONTENT);
//        //inventoryDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
//        // accountsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
        settlementDialog.setCanceledOnTouchOutside(true);
        settlementDialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        settlementDialog.cancel();
                    }
                }
        );
        dialogBinding.setViewmodel(dialogueViewmodel);
        dialogBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settlementDialog.cancel();
            }
        });

        setupListAdapter(dialogBinding.rvSettlement);

    }

    private void setupListAdapter(RecyclerView rvSettlement) {
        paymentAdapter = new PaymentsAdapter();
        rvSettlement.setAdapter(paymentAdapter);
        rvSettlement.setLayoutManager(new LinearLayoutManager(this));
    }
}