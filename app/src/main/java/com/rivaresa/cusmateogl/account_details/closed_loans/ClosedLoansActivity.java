package com.rivaresa.cusmateogl.account_details.closed_loans;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.rivaresa.cusmateogl.account_details.AccountDetailsActivity;
import com.rivaresa.cusmateogl.account_details.closed_loans.action.ClosedLoanAction;
import com.rivaresa.cusmateogl.account_details.closed_loans.adapter.ClosedLoansAdapter;
import com.rivaresa.cusmateogl.databinding.ActivityClosedLoansBinding;
import com.rivaresa.cusmateogl.utils.Services;


public class ClosedLoansActivity extends BaseActivity {

    ClosedLoansViewmodel viewmodel;
    ActivityClosedLoansBinding binding;
    ClosedLoansAdapter adapter;
    Dialog warningDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_closed_loans);
        viewmodel = new ViewModelProvider(this).get(ClosedLoansViewmodel.class);
        binding.setViewmodel(viewmodel);

        setupRecyclerView(binding.rvClosedAccounts);
        viewmodel.initLoading(this);
        viewmodel.getClosedLoans();

        viewmodel.getmAction().observe(this, new Observer<ClosedLoanAction>() {
            @Override
            public void onChanged(ClosedLoanAction closedLoanAction) {
                viewmodel.cancelLoading();
                switch (closedLoanAction.getAction()) {
                    case ClosedLoanAction.API_SUCCESS:
                        adapter.setLoansData(closedLoanAction.getClosedLoansResponse().getData());
                        adapter.notifyDataSetChanged();
                        break;

                    case ClosedLoanAction.API_ERROR:
                        Services.errorDialog(ClosedLoansActivity.this,closedLoanAction.getError());

                        break;

                        case ClosedLoanAction.NO_DATA:
                            Dialog dialog= new Dialog(ClosedLoansActivity.this);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            dialog.getWindow().setElevation(0);
                            //errorDialog.getWindow().setLayout((int) WindowManager.LayoutParams.WRAP_CONTENT,  WindowManager.LayoutParams.WRAP_CONTENT);
                            @SuppressLint("InflateParams")
                            View customView_ = LayoutInflater.from(ClosedLoansActivity.this).inflate(R.layout.layout_error_popup, null);
                            TextView tv_error_ = customView_.findViewById(R.id.tv_error);
                            TextView tvOkey = customView_.findViewById(R.id.tv_error_ok);
                            tv_error_.setText(closedLoanAction.getError());

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

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new ClosedLoansAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

}