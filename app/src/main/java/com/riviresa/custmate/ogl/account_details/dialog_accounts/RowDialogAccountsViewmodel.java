package com.riviresa.custmate.ogl.account_details.dialog_accounts;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.riviresa.custmate.ogl.account_details.pojo.Table;

public class RowDialogAccountsViewmodel extends BaseObservable {

    Table bankDetail;
    MutableLiveData<ActionDialogAccounts> mAction;
    String bankName,accountNumber,address,ifsc;
    public RowDialogAccountsViewmodel(Table bankDetail, MutableLiveData<ActionDialogAccounts> mAction) {
        this.bankDetail=bankDetail;
        this.mAction=mAction;
    }

    public String getBankName() {
        bankName=bankDetail.getBank();
        return bankName;
    }

    public String getAccountNumber() {
        accountNumber=bankDetail.getAccNo();
        return accountNumber;
    }

    public String getAddress() {
        address=bankDetail.getBranch();
        return address;
    }

    public String getIfsc() {
        ifsc=bankDetail.getIFSCCode();
        return ifsc;
    }

    public void setBankDetails(Table bankDetail) {
        this.bankDetail=bankDetail;
        notifyChange();
    }

    public void clickSelectAccount(View view)
    {
        mAction.setValue(new ActionDialogAccounts(ActionDialogAccounts.SELECT_BANK_DATA,bankDetail));
    }
}
