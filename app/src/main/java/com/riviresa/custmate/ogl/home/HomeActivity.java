package com.riviresa.custmate.ogl.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.riviresa.custmate.R;
import com.riviresa.custmate.databinding.ActivityHomeBinding;
import com.riviresa.custmate.ogl.BaseActivity;
import com.riviresa.custmate.ogl.account_details.AccountDetailsActivity;
import com.riviresa.custmate.ogl.account_list.AccountListActivity;
import com.riviresa.custmate.ogl.contact.ContactActivity;
import com.riviresa.custmate.ogl.gold_loan.GoldLoanActivity;
import com.riviresa.custmate.ogl.home.action.HomeAction;
import com.riviresa.custmate.ogl.login.LoginActivity;


public class HomeActivity extends BaseActivity {

    HomeViewmodel viewmodel;
    ActivityHomeBinding binding;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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