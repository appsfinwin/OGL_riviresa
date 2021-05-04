package com.rivaresa.cusmateogl.model.account_holder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountHolderResponse {

    @SerializedName("account")
    @Expose
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}