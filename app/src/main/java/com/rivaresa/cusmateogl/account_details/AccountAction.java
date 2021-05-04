package com.rivaresa.cusmateogl.account_details;


import com.rivaresa.cusmateogl.account_details.pojo.GetBankDetailsResponse;

public class AccountAction {
    public static final int DEFAULT = -1;
    public static final int BANK_DETAILS_SUCCESS = 1;
    public static final int API_ERROR = 2;
    public static final int CLICK_CLOSED_LOANS = 3;
    public static final int CLICK_ACCOUNTS = 4;

    int action;
    String error;
    GetBankDetailsResponse getBankDetailsResponse;

    public AccountAction(int action) {
        this.action = action;
    }

    public AccountAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public AccountAction(int action, GetBankDetailsResponse getBankDetailsResponse) {
        this.action = action;
        this.getBankDetailsResponse = getBankDetailsResponse;
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

    public GetBankDetailsResponse getGetBankDetailsResponse() {
        return getBankDetailsResponse;
    }

    public void setGetBankDetailsResponse(GetBankDetailsResponse getBankDetailsResponse) {
        this.getBankDetailsResponse = getBankDetailsResponse;
    }
}
