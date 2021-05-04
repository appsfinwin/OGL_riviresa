package com.rivaresa.cusmateogl.account_details.dialog_accounts;

import com.rivaresa.cusmateogl.account_details.pojo.Table;

public class ActionDialogAccounts {

    public static final int DEFAULT=0;
    public static final int SELECT_BANK_DATA=1;
    public static final int CLICK_CHANGE_ACCOUNTS=2;

    public int action;
    public String error;

    public Table bankDetail;

    public ActionDialogAccounts(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public ActionDialogAccounts(int action) {
        this.action = action;
    }

    public ActionDialogAccounts(int action, Table bankDetail) {
        this.action = action;
        this.bankDetail = bankDetail;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Table getBankDetail() {
        return bankDetail;
    }

    public void setBankDetail(Table bankDetail) {
        this.bankDetail = bankDetail;
    }
}
