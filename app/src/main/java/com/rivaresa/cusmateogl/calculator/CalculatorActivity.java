package com.rivaresa.cusmateogl.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.calculator.action.CalculatorAction;
import com.rivaresa.cusmateogl.calculator.adapter.CalculatorAdapter;
import com.rivaresa.cusmateogl.databinding.ActivityCalculatorBinding;

public class CalculatorActivity extends BaseActivity {

    CalculatorViewmodel viewmodel;
    ActivityCalculatorBinding binding;
    CalculatorAdapter adapter;
    String scheme_code,scheme_name,scheme_interest,scheme_period,net_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_calculator);
       viewmodel=new ViewModelProvider(this).get(CalculatorViewmodel.class);
       binding.setViewmodel(viewmodel);


       setupRecyclerView(binding.rvCalculator);
        binding.layoutEstimatedAmount.setVisibility(View.GONE);
       viewmodel.setActivity(this);
       viewmodel.setBinding(binding);

        Intent intent=getIntent();
        if (intent!=null)
        {

            net_amount=intent.getStringExtra("net_amount");
            scheme_code=intent.getStringExtra("scheme_code");
            scheme_name=intent.getStringExtra("scheme_name");
            scheme_interest=intent.getStringExtra("scheme_interest");
            scheme_period=intent.getStringExtra("scheme_period");

            viewmodel.setScheme(scheme_code,scheme_name,net_amount);
        }
       viewmodel.getmAction().observe(this, new Observer<CalculatorAction>() {
           @Override
           public void onChanged(CalculatorAction calculatorAction) {
               viewmodel.cancelLoading();
               switch (calculatorAction.getAction())
               {
                   case CalculatorAction.CALCULATE_SUCCESS:
                       setupRecyclerView(binding.rvCalculator);
                       binding.layoutEstimatedAmount.setVisibility(View.VISIBLE);
                       adapter.setCalculatorData(calculatorAction.getCalculatorResponse().getData().getData());
                       adapter.notifyDataSetChanged();
                       viewmodel.setData(calculatorAction.getCalculatorResponse().getData());
                       break;
               }
           }
       });
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new CalculatorAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}