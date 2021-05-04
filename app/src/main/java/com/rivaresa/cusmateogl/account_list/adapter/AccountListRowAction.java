package com.rivaresa.cusmateogl.account_list.adapter;

import com.rivaresa.cusmateogl.account_list.pojo.account_response.AccountData;

public class AccountListRowAction {

    public static final int DEFAULT=0;
    public static final int CLICK_ITEM_DETAILS=1;
    public static final int CLICK_SELECT=2;

    public AccountData accountData;

    public AccountListRowAction( int action,AccountData accountData) {
        this.accountData = accountData;
        this.action = action;
    }

    int action;
    String error;

    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public AccountListRowAction(int action) {
        this.action = action;
    }

    public AccountListRowAction(int action, String error) {
        this.action = action;
        this.error = error;
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
}
