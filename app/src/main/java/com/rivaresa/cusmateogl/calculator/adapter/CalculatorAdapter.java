package com.rivaresa.cusmateogl.calculator.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.calculator.pojo.CalculatorData;
import com.rivaresa.cusmateogl.databinding.RowLayoutCalculatorBinding;

import java.util.Collections;
import java.util.List;

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.ViewHolder> {
    public List<CalculatorData> calculatorData;

    public CalculatorAdapter() {
        this.calculatorData = Collections.emptyList();
    }

    public void setCalculatorData(List<CalculatorData> calculatorData) {
        this.calculatorData = calculatorData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutCalculatorBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_layout_calculator,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setData(calculatorData.get(position));
    }

    @Override
    public int getItemCount() {
        return calculatorData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowLayoutCalculatorBinding binding;
        public ViewHolder(@NonNull RowLayoutCalculatorBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setData(CalculatorData data)
        {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new RowCalculatorViewmodel(data));
            }
            else {
                binding.getViewmodel().setCalculatorData(data);
            }
        }
    }
}
