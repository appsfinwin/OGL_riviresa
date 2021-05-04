package com.rivaresa.cusmateogl.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_details.AccountDetailsActivity;
import com.rivaresa.cusmateogl.account_list.AccountListActivity;
import com.rivaresa.cusmateogl.contact.ContactActivity;
import com.rivaresa.cusmateogl.databinding.ActivityHomeBinding;
import com.rivaresa.cusmateogl.gold_loan.GoldLoanActivity;
import com.rivaresa.cusmateogl.home.action.HomeAction;
import com.rivaresa.cusmateogl.login.LoginActivity;

public class HomeActivity extends BaseActivity {

    HomeViewmodel viewmodel;
    ActivityHomeBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewmodel = new ViewModelProvider(this).get(HomeViewmodel.class);
        binding.setViewmodel(viewmodel);

        binding.tvName.setText(sharedPreferences.getString("name", ""));
        binding.tvEmail.setText(sharedPreferences.getString("email", ""));
        binding.tvPhone.setText(sharedPreferences.getString("phone", ""));

        viewmodel.getmAction().observe(this, new Observer<HomeAction>() {
            @Override
            public void onChanged(HomeAction homeAction) {
                switch (homeAction.getAction()) {
                    case HomeAction.CLICK_PAY_ONLINE:
                        Intent intent = new Intent(HomeActivity.this, AccountListActivity.class);
                        intent.putExtra("from", "home");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case HomeAction.CLICK_ACCOUNT_DETAILS:

                        Intent accountIntent = new Intent(HomeActivity.this, AccountDetailsActivity.class);
                        startActivity(accountIntent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case HomeAction.CLICK_ONLINE_GOLD_LOAN:
                        Intent goldLoanIntent = new Intent(HomeActivity.this, GoldLoanActivity.class);
                        startActivity(goldLoanIntent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;

                    case HomeAction.CLICK_CONTACT:
                        Intent contactIntent = new Intent(HomeActivity.this, ContactActivity.class);
                        startActivity(contactIntent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Do you want to logout ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}