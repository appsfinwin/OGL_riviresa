package com.rivaresa.cusmateogl.account_details;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_details.closed_loans.ClosedLoansActivity;
import com.rivaresa.cusmateogl.account_details.dialog_accounts.ActionDialogAccounts;
import com.rivaresa.cusmateogl.account_details.dialog_accounts.DialogAccountsViewmodel;
import com.rivaresa.cusmateogl.account_details.dialog_accounts.DialogeAccountsAdapter;
import com.rivaresa.cusmateogl.account_details.pojo.Table;
import com.rivaresa.cusmateogl.databinding.ActivityAccountDetailsBinding;
import com.rivaresa.cusmateogl.databinding.DialogLayoutAcountsBinding;

import java.util.List;


public class AccountDetailsActivity extends BaseActivity {

    AccountDetailsViewmodel viewmodel;
    ActivityAccountDetailsBinding binding;
    int CLOSED_LOAN=1234;

    Dialog accountsDialog,warningDialog;
    DialogLayoutAcountsBinding bindingDialogAccounts;
    DialogAccountsViewmodel dialogueViewmodel;
    DialogeAccountsAdapter dialogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_account_details);
        viewmodel=new ViewModelProvider(this).get(AccountDetailsViewmodel.class);
        binding.setViewmodel(viewmodel);

        initDialoge();
        viewmodel.initLoading(this);
        viewmodel.getBankDetails();
//        if (DataHolder.getInstance().bankDetails.size()>0) {
//            viewmodel.setInitData(DataHolder.getInstance().bankDetails.get(0));
//        }

        viewmodel.getmAction().observe(this, new Observer<AccountAction>() {
            @Override
            public void onChanged(AccountAction action) {
                viewmodel.cancelLoading();
                switch (action.getAction())
                {
                    case AccountAction.CLICK_CLOSED_LOANS:
                        Intent intent=new Intent(AccountDetailsActivity.this, ClosedLoansActivity.class);
                        startActivityForResult(intent,CLOSED_LOAN);
                        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        break;

                    case AccountAction.CLICK_ACCOUNTS:
                        dialogAdapter.setBankDetails(viewmodel.bankDetailsList.get());
                        dialogAdapter.notifyDataSetChanged();
                        accountsDialog.show();
                        break;

                    case AccountAction.DEFAULT:
                        break;
                    case AccountAction.BANK_DETAILS_SUCCESS:
                        setInitData(action.getBankDetailsResponse.getBankDetails().getTable());
                        break;


                    case AccountAction.API_ERROR:


                        Dialog dialog= new Dialog(AccountDetailsActivity.this);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.getWindow().setElevation(0);
                        //errorDialog.getWindow().setLayout((int) WindowManager.LayoutParams.WRAP_CONTENT,  WindowManager.LayoutParams.WRAP_CONTENT);
                        @SuppressLint("InflateParams")
                        View customView_ = LayoutInflater.from(AccountDetailsActivity.this).inflate(R.layout.layout_error_popup, null);
                        TextView tv_error_ = customView_.findViewById(R.id.tv_error);
                        TextView tvOkey = customView_.findViewById(R.id.tv_error_ok);
                        tv_error_.setText(action.getError());

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

                        break;
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewmodel.reset();
        finish();
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewmodel.reset();
    }


    private void initDialoge() {

        accountsDialog = new Dialog(this);
        bindingDialogAccounts = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_layout_acounts, null, false);

        dialogueViewmodel = new DialogAccountsViewmodel();
        accountsDialog.setContentView(bindingDialogAccounts.getRoot());
        accountsDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels),  WindowManager.LayoutParams.WRAP_CONTENT);
        //inventoryDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        // accountsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        accountsDialog.setCanceledOnTouchOutside(true);
        accountsDialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        accountsDialog.cancel();
                    }
                }
        );
        bindingDialogAccounts.setViewmodel(dialogueViewmodel);
        bindingDialogAccounts.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountsDialog.cancel();
            }
        });

        setupListAdapter(bindingDialogAccounts.rvAccounts);

    }

    private void setupListAdapter(RecyclerView rvAccounts) {

        dialogAdapter = new DialogeAccountsAdapter();
        rvAccounts.setAdapter(dialogAdapter);
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));

        dialogAdapter.getmAction().observe(this, new Observer<ActionDialogAccounts>() {
            @Override
            public void onChanged(ActionDialogAccounts actionDialogAccounts) {
                switch (actionDialogAccounts.getAction())
                {
                    case ActionDialogAccounts.SELECT_BANK_DATA:
                        //DataHolder.getInstance().selectedBankData=actionDialogAccounts.getBankDetail();
                        setBankData(actionDialogAccounts.getBankDetail());
                        break;
                }
            }
        });
    }

    public void setInitData(List<Table> bankDetails) {
        viewmodel.bankDetailsList.set(bankDetails);
        for (Table table: bankDetails)
        {
            if (table.getIsDefault().equals("Y"))
            {
                binding.tvAccountNumber.setText(table.getAccNo());
                binding.tvBankName.setText(table.getBank());
                binding.tvIfsc.setText(table.getIFSCCode());
                binding.tvBranch.setText(table.getBranch());

                binding.tvName.setText(sharedPreferences.getString("name", ""));
                binding.tvEmail.setText(sharedPreferences.getString("email", ""));
                binding.tvMobile.setText(sharedPreferences.getString("phone", ""));

                editor.putString("CustBankId", table.getCustBankId());
                editor.putString("bankAccountNumber", table.getAccNo());
                editor.putString("bankIfsc", table.getIFSCCode());
                editor.commit();
            }

        }


    }

    public void setBankData(Table bankDetail) {
        binding.tvAccountNumber.setText(bankDetail.getAccNo());
        binding.tvBankName.setText(bankDetail.getBank());
        binding.tvIfsc.setText(bankDetail.getIFSCCode());
        binding.tvBranch.setText(bankDetail.getBranch());
        binding.tvName.setText(sharedPreferences.getString("name", ""));
        binding.tvEmail.setText(sharedPreferences.getString("email", ""));
        binding.tvMobile.setText(sharedPreferences.getString("phone", ""));
        editor.putString("CustBankId",bankDetail.getCustBankId());

        editor.putString("CustBankId", bankDetail.getCustBankId());
        editor.putString("bankAccountNumber", bankDetail.getAccNo());
        editor.putString("bankIfsc", bankDetail.getIFSCCode());
        editor.commit();
    }


}