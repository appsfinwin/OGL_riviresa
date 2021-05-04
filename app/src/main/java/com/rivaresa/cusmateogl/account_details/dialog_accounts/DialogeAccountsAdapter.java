package com.rivaresa.cusmateogl.account_details.dialog_accounts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_details.pojo.Table;
import com.rivaresa.cusmateogl.databinding.RowLayoutAccountsBinding;
import com.rivaresa.cusmateogl.login.pojo.BankDetail;

import java.util.Collections;
import java.util.List;

public class DialogeAccountsAdapter extends RecyclerView.Adapter<DialogeAccountsAdapter.ViewHolder> {
    List<Table> bankDetails;
    MutableLiveData<ActionDialogAccounts> mAction;
    public DialogeAccountsAdapter() {
        this.bankDetails= Collections.emptyList();
        mAction=new MutableLiveData<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutAccountsBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.row_layout_accounts,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setAccounts(bankDetails.get(position),mAction);
    }

    public void setBankDetails(List<Table> bankDetails) {
        this.bankDetails = bankDetails;
        notifyDataSetChanged();
    }

    public MutableLiveData<ActionDialogAccounts> getmAction() {
        return mAction;
    }

    @Override
    public int getItemCount() {
        return bankDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowLayoutAccountsBinding binding;
        public ViewHolder(@NonNull RowLayoutAccountsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setAccounts(Table bankDetail, MutableLiveData<ActionDialogAccounts> mAction)
        {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new RowDialogAccountsViewmodel(bankDetail,mAction));
            }else {
                binding.getViewmodel().setBankDetails(bankDetail);
            }
        }
    }
}
