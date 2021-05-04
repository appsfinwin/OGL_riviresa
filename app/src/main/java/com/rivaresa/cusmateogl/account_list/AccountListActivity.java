package com.rivaresa.cusmateogl.account_list;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_list.action.AccountListAction;
import com.rivaresa.cusmateogl.account_list.adapter.AccountListAdapter;
import com.rivaresa.cusmateogl.account_list.adapter.AccountListRowAction;
import com.rivaresa.cusmateogl.account_list.dialog_inventory_details.DialogInventoryDetailsViewmodel;
import com.rivaresa.cusmateogl.account_list.dialog_inventory_details.DialogIventoryDetailsAdapter;
import com.rivaresa.cusmateogl.databinding.ActivityAccountListBinding;
import com.rivaresa.cusmateogl.databinding.DialogLayoutInventoryBinding;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.SelectSchemeActivity;
import com.rivaresa.cusmateogl.home.HomeActivity;
import com.rivaresa.cusmateogl.select_payment.SelectPaymentActivity;

public class AccountListActivity extends BaseActivity {

    ActivityAccountListBinding binding;
    AccountListViewmodel viewmodel;
    AccountListAdapter adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //for dialog
    Dialog inventoryDialog;
    DialogLayoutInventoryBinding bindingInventoryDialog;
    DialogInventoryDetailsViewmodel dialogueViewmodel;
    DialogIventoryDetailsAdapter dialogAdapter;
    String from;
    Dialog warningDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Intent intent = getIntent();
        if (intent != null) {
            from = intent.getStringExtra("from");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_list);
        viewmodel = new ViewModelProvider(this).get(AccountListViewmodel.class);
        binding.setViewmodel(viewmodel);

        viewmodel.initLoading(this);
        viewmodel.getAccountDetails();
        setupRecyclerView(binding.rvAccounts);
        initDialoge();
        viewmodel.getmAction().observe(this, new Observer<AccountListAction>() {
            @Override
            public void onChanged(AccountListAction accountListAction) {
                viewmodel.cancelLoading();
                switch (accountListAction.getAction()) {
                    case AccountListAction.ACCOUNT_DETAILS_SUCCESS:

                        adapter.setAccountDataList(accountListAction.getAccountDetailsResponse().getData());
                        adapter.notifyDataSetChanged();
                        break;

                    case AccountListAction.API_ERROR:

                        showError(accountListAction.getError());
                        break;

                    case AccountListAction.INVENTORY_DETAILS_SUCCESS:
                        Glide
                                .with(AccountListActivity.this)
                                .load(accountListAction.getInventoryDetailResponse().getData().getImage())
                                .centerCrop()
                                .into(bindingInventoryDialog.imageView);
                        dialogAdapter.setOrnamentList(accountListAction.getInventoryDetailResponse().getOrnaments());
                        dialogAdapter.notifyDataSetChanged();
                        inventoryDialog.show();

                        break;

                        case AccountListAction.CLICK_SETTINGS:

                            exitDialog();
                            break;
                }
            }
        });

    }

    private void initDialoge() {

        //for dialog
//        Dialog inventoryDialog;
//        DialogLayoutInventoryBinding bindingInventoryDialog;
//        DialogInventoryDetailsViewmodel dialogueViewmodel;
//        DialogIventoryDetailsAdapter dialogAdapter;
//        //

        inventoryDialog = new Dialog(this);
        bindingInventoryDialog = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_layout_inventory, null, false);

        dialogueViewmodel = new DialogInventoryDetailsViewmodel();
        inventoryDialog.setContentView(bindingInventoryDialog.getRoot());
        inventoryDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels), WindowManager.LayoutParams.WRAP_CONTENT);
        //inventoryDialog.getWindow().setLayout((int) (this.getResources().getDisplayMetrics().widthPixels * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        //inventoryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        inventoryDialog.setCanceledOnTouchOutside(true);
        bindingInventoryDialog.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryDialog.cancel();
            }
        });
        inventoryDialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        inventoryDialog.cancel();


                    }
                }
        );
        bindingInventoryDialog.setViewmodel(dialogueViewmodel);

        setupListAdapter(bindingInventoryDialog.rvInventory);

    }
    private void exitDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .setTitle("Exit?")
                .setMessage("Do you want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AccountListActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void setupListAdapter(RecyclerView listCenter) {
        dialogAdapter = new DialogIventoryDetailsAdapter();
        listCenter.setAdapter(dialogAdapter);
        listCenter.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new AccountListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setObservable(adapter);
    }

    private void setObservable(AccountListAdapter adapter) {

        adapter.getmAction().observe(this, new Observer<AccountListRowAction>() {
            @Override
            public void onChanged(AccountListRowAction accountListRowAction) {

                switch (accountListRowAction.getAction()) {
                    case AccountListRowAction.CLICK_ITEM_DETAILS:

                        String account_number = accountListRowAction.getAccountData().getAccountNo();
                        account_number = accountListRowAction.getAccountData().getAccountNo();
                        editor.putString("account_number", accountListRowAction.getAccountData().getAccountNo());
                        editor.putString("inventory_number", accountListRowAction.getAccountData().getInventoryNo());
                        editor.commit();
                        viewmodel.initLoading(AccountListActivity.this);
                        viewmodel.getInventoryDetails(account_number);

                        break;

                    case AccountListRowAction.CLICK_SELECT:
                        editor.putString("account_number", accountListRowAction.getAccountData().getAccountNo());
                        editor.putString("inventory_number", accountListRowAction.getAccountData().getInventoryNo());
                        editor.commit();

                        if (from.equals("home")) {
                            Intent intent = new Intent(AccountListActivity.this, SelectPaymentActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }else if (from.equals("goldLoan")){
                            Intent goldIntent = new Intent(AccountListActivity.this, SelectSchemeActivity.class);
                            startActivity(goldIntent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
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
        finish();
//        if (warningDialog.isShowing())
//        {
//            warningDialog.dismiss();
//        }
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
//                finish();
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                warningDialog.dismiss();
            }
        });
        warningDialog.setContentView(view);
        //warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        warningDialog.getWindow().setLayout(( WindowManager.LayoutParams.MATCH_PARENT), WindowManager.LayoutParams.WRAP_CONTENT);
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