package com.riviresa.custmate.ogl.gold_loan.select_scheme;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riviresa.custmate.R;
import com.riviresa.custmate.databinding.ActivitySelectSchemeBinding;
import com.riviresa.custmate.databinding.DialogLayoutSettlementBinding;
import com.riviresa.custmate.ogl.finialize_amount.FinalizeAmountActivity;
import com.riviresa.custmate.ogl.gold_loan.select_scheme.action.RowSchemeAction;
import com.riviresa.custmate.ogl.gold_loan.select_scheme.action.SelectSchemeAction;
import com.riviresa.custmate.ogl.gold_loan.select_scheme.adapter.SchemeAdapter;
import com.riviresa.custmate.ogl.gold_loan.select_scheme.dialog.SettlementDialogViewmodel;
import com.riviresa.custmate.ogl.payment.paytm.adapter.PaymentsAdapter;

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

                        showError(selectSchemeAction.getError());
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
                            intent.putExtra("settlement_total", rowSchemeAction.getSchemeData().getSettlementTotal());
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

    private void showError(String error)
    {
        Dialog dialog= new Dialog(SelectSchemeActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().setElevation(0);
        //errorDialog.getWindow().setLayout((int) WindowManager.LayoutParams.WRAP_CONTENT,  WindowManager.LayoutParams.WRAP_CONTENT);
        @SuppressLint("InflateParams")
        View customView_ = LayoutInflater.from(SelectSchemeActivity.this).inflate(R.layout.layout_error_popup, null);
        TextView tv_error_ = customView_.findViewById(R.id.tv_error);
        TextView tvOkey = customView_.findViewById(R.id.tv_error_ok);
        tv_error_.setText(error);

        tvOkey.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            dialog.cancel();
        });


        // errorDialog.addContentView(customView_,new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,  WindowManager.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(customView_);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

}