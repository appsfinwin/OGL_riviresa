package com.rivaresa.cusmateogl.account_details.closed_loans.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_details.closed_loans.pojo.LoansData;
import com.rivaresa.cusmateogl.databinding.RowLayoutClosedAccountsBinding;

import java.util.Collections;
import java.util.List;

public class ClosedLoansAdapter extends RecyclerView.Adapter<ClosedLoansAdapter.ViewHolder> {
    List<LoansData> loansData;

    public ClosedLoansAdapter() {
        this.loansData= Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutClosedAccountsBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.row_layout_closed_accounts,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setLoansData(loansData.get(position));
    }

    @Override
    public int getItemCount() {
        return loansData.size();
    }

    public void setLoansData(List<LoansData> loansData) {
        this.loansData = loansData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowLayoutClosedAccountsBinding binding;
        public ViewHolder(@NonNull RowLayoutClosedAccountsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setLoansData(LoansData loansData)
        {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new RowClosedLoansViewmodel(loansData));
            }else
            {
                binding.getViewmodel().setLoansData(loansData);
            }
        }
    }
}
