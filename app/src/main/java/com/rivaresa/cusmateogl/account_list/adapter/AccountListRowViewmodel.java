package com.rivaresa.cusmateogl.account_list.adapter;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.account_list.pojo.account_response.AccountData;

public class AccountListRowViewmodel extends BaseObservable {

    AccountData accountData;
    MutableLiveData<AccountListRowAction> mAction;
    String inventoryNo,accountNumber,pledgedAmount,netWeight;
    public AccountListRowViewmodel(AccountData accountData, MutableLiveData<AccountListRowAction> mAction) {
        this.accountData=accountData;
        this.mAction=mAction;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData=accountData;
        notifyChange();
    }

    public String getInventoryNo() {
        inventoryNo=accountData.getInventoryNo();
        return inventoryNo;
    }

    public String getAccountNumber() {
        accountNumber=accountData.getAccountNo();
        return accountNumber;
    }

    public String getPledgedAmount() {
        pledgedAmount=accountData.getAmount().toString();
        return pledgedAmount;
    }

    public String getNetWeight() {
        netWeight=accountData.getItemWeight().toString();
        return netWeight;
    }

    public void clickSelect(View view)
    {
        mAction.setValue(new AccountListRowAction(AccountListRowAction.CLICK_SELECT,accountData));
    }

    public void clickItemDetails(View view)
    {
        mAction.setValue(new AccountListRowAction(AccountListRowAction.CLICK_ITEM_DETAILS,accountData));
    }
}
