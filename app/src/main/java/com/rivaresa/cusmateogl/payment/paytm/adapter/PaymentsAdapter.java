package com.rivaresa.cusmateogl.payment.paytm.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.RowPaymentBinding;
import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementData;

import java.util.Collections;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {

    List<SettlementData> settlementDataList;

    private static final String TAG = "PaymentsAdapter";
    public PaymentsAdapter() {
        this.settlementDataList= Collections.emptyList();
    }

    public void setSettlementDataList(List<SettlementData> settlementDataList) {
        this.settlementDataList = settlementDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowPaymentBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_payment,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setBinding(settlementDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return settlementDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowPaymentBinding binding;
        public ViewHolder(@NonNull RowPaymentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setBinding(SettlementData settlementData)
        {
            if (binding.getViewmodel()==null)
            {
               binding.setViewmodel(new RowPaymentViewmodel(settlementData));
            }else {
                binding.getViewmodel().setData(settlementData);
            }
            //binding.executePendingBindings();
        }
    }
}
