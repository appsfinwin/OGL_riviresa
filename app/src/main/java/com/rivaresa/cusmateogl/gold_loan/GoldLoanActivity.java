package com.rivaresa.cusmateogl.gold_loan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_details.dialog_accounts.ActionDialogAccounts;
import com.rivaresa.cusmateogl.account_details.dialog_accounts.DialogAccountsViewmodel;
import com.rivaresa.cusmateogl.account_details.dialog_accounts.DialogeAccountsAdapter;
import com.rivaresa.cusmateogl.account_list.AccountListActivity;
import com.rivaresa.cusmateogl.databinding.ActivityGoldLoanBinding;
import com.rivaresa.cusmateogl.databinding.DialogLayoutAcountsBinding;
import com.rivaresa.cusmateogl.databinding.LayoutTermsAndCondiationsBinding;
import com.rivaresa.cusmateogl.gold_loan.action.GoldLoanAction;

public class GoldLoanActivity extends BaseActivity {


    ActivityGoldLoanBinding binding;
    GoldLoanViewmodel viewmodel;
    TermsAndConditionViewmodel dialogViewmodel;
    LayoutTermsAndCondiationsBinding dialogBinding;
    Dialog termsDialog;

    Dialog accountsDialog;
    DialogLayoutAcountsBinding bindingDialogAccounts;
    DialogAccountsViewmodel dialogueViewmodel;
    DialogeAccountsAdapter dialogAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_gold_loan);
        viewmodel=new ViewModelProvider(this).get(GoldLoanViewmodel.class);
        binding.setViewmodel(viewmodel);
        initDialoge();
        viewmodel.setBinding(binding);
        viewmodel.setContext(this);

        viewmodel.initLoading(this);
        viewmodel.getBankDetails();
//        if (DataHolder.getInstance().bankDetails.size()>0) {
//            viewmodel.setInitData(DataHolder.getInstance().bankDetails.get(0));
//        }
        viewmodel.getmAction().observe(this, new Observer<GoldLoanAction>() {
            @Override
            public void onChanged(GoldLoanAction goldLoanAction) {

                viewmodel.cancelLoading();
                switch (goldLoanAction.getAction())
                {
                    case GoldLoanAction.TERMS_API_SUCCESS:
                        showTermsAndConditions(goldLoanAction.getTermsResponse().getData().getTermsConditions());
                        break;

                    case GoldLoanAction.API_ERROR:
                        Toast.makeText(GoldLoanActivity.this, goldLoanAction.getError(), Toast.LENGTH_SHORT).show();
                        break;

                    case GoldLoanAction.CLICK_CHANGE_ACCOUNT:
                        dialogAdapter.setBankDetails(viewmodel.bankDetailsList.get());
                        dialogAdapter.notifyDataSetChanged();
                        accountsDialog.show();
                        break;

                    case GoldLoanAction.CLICK_CONTINUE:
                        Intent intent=new Intent(GoldLoanActivity.this, AccountListActivity.class);
                        intent.putExtra("from","goldLoan");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case GoldLoanAction.BANK_DETAILS_SUCCESS:
                        viewmodel.setInitData(goldLoanAction.getBankDetailsResponse.getBankDetails().getTable());
                        break;

                    case GoldLoanAction.BANK_DETAILS_ERROR:
                        showError(goldLoanAction.getError());
                        break;

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    public void showError(String error) {

        Dialog warningDialog = new Dialog(this);

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

    public void showTermsAndConditions(String terms)
    {
        termsDialog = new Dialog(this);
        dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_terms_and_condiations, null, false);

        dialogViewmodel = new TermsAndConditionViewmodel();
        termsDialog.setContentView(dialogBinding.getRoot());
        termsDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels),  WindowManager.LayoutParams.WRAP_CONTENT);
        //inventoryDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        // accountsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        termsDialog.setCanceledOnTouchOutside(true);
        termsDialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        termsDialog.cancel();
                    }
                }
        );
        dialogBinding.tvTerms.setText(terms);
        dialogBinding.setViewmodel(dialogViewmodel);
        dialogBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsDialog.cancel();
            }
        });
        termsDialog.show();

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
                        // DataHolder.getInstance().selectedBankData=actionDialogAccounts.getBankDetail();
                        viewmodel.setBankData(actionDialogAccounts.getBankDetail());
                        break;
                }
            }
        });
    }
}