package com.rivaresa.cusmateogl.account_list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_list.pojo.account_response.AccountData;
import com.rivaresa.cusmateogl.databinding.RowAccountNumberBinding;

import java.util.Collections;
import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {
    public AccountListAdapter() {
        this.accountDataList = Collections.emptyList();
        mAction=new MutableLiveData<>();
    }

    List<AccountData> accountDataList;
    MutableLiveData<AccountListRowAction>mAction;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowAccountNumberBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_account_number,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setBinding(accountDataList.get(position),mAction);
    }

    public void setAccountDataList(List<AccountData> accountDataList)
    {
        this.accountDataList=accountDataList;
        notifyDataSetChanged();
    }

    public MutableLiveData<AccountListRowAction> getmAction() {
        return mAction;
    }

    @Override
    public int getItemCount() {
        return accountDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowAccountNumberBinding binding;
        public ViewHolder(@NonNull RowAccountNumberBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setBinding(AccountData accountData, MutableLiveData<AccountListRowAction> mAction)
        {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new AccountListRowViewmodel(accountData,mAction));
            }else
            {
                binding.getViewmodel().setAccountData(accountData);
            }
        }
    }
}
