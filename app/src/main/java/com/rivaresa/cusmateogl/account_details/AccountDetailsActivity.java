package com.rivaresa.cusmateogl.account_details;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.rivaresa.cusmateogl.databinding.ActivityAccountDetailsBinding;
import com.rivaresa.cusmateogl.databinding.DialogLayoutAcountsBinding;


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
        viewmodel.setBinding(binding);

        viewmodel.initLoading(this);
        viewmodel.getBankDetails();
//        if (DataHolder.getInstance().bankDetails.size()>0) {
//            viewmodel.setInitData(DataHolder.getInstance().bankDetails.get(0));
//        }
        initDialoge();
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
                        viewmodel.setInitData(action.getBankDetailsResponse.getBankDetails().getTable());
                        break;


                    case AccountAction.API_ERROR:
                        showError(action.getError());
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
                        viewmodel.setBankData(actionDialogAccounts.getBankDetail());
                        break;
                }
            }
        });
    }

    public void showError(String error) {

        warningDialog = new Dialog(this);

        LayoutInflater inflater= this.getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_popup,null);
        TextView errorMessage=view.findViewById(R.id.txt_msg);
        TextView ok=view.findViewById(R.id.tv_email);
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
        warningDialog.getWindow().setLayout(( WindowManager.LayoutParams.MATCH_PARENT), WindowManager.LayoutParams.WRAP_CONTENT);
        warningDialog.setCanceledOnTouchOutside(false);
        warningDialog.setCancelable(true);
        warningDialog.show();

    }


}