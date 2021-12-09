package com.riviresa.custmate.ogl.finialize_amount;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.riviresa.custmate.R;
import com.riviresa.custmate.databinding.LayoutRowAccountsBinding;
import com.riviresa.custmate.ogl.payment.paytm.pojo.SettlementData;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {

    List<SettlementData> settlementDataList;

    public AccountsAdapter() {
        this.settlementDataList= Collections.emptyList();
    }

    public void setSettlementDataList(List<SettlementData> settlementDataList) {
        this.settlementDataList = settlementDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutRowAccountsBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_row_accounts,parent,false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.setBinding(settlementDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (settlementDataList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRowAccountsBinding binding;
        public ViewHolder(@NonNull @NotNull LayoutRowAccountsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setBinding(SettlementData settlementData)
        {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new RowAccountViewmodel(settlementData));
            }else {
                binding.getViewmodel().setData(settlementData);
            }
            //binding.executePendingBindings();
        }
    }
}
