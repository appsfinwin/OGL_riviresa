package com.rivaresa.cusmateogl.account_details.closed_loans;

import android.app.Dialog;
import android.content.DialogInterface;
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
import com.rivaresa.cusmateogl.account_details.closed_loans.action.ClosedLoanAction;
import com.rivaresa.cusmateogl.account_details.closed_loans.adapter.ClosedLoansAdapter;
import com.rivaresa.cusmateogl.databinding.ActivityClosedLoansBinding;


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
                        showError(closedLoanAction.getError());
                        break;

                        case ClosedLoanAction.NO_DATA:
                        showError(closedLoanAction.getError());
                        warningDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            }
                        });
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



    }
}