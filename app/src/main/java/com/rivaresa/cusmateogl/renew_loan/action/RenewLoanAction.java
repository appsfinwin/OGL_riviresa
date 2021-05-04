package com.rivaresa.cusmateogl.renew_loan.action;

import com.rivaresa.cusmateogl.renew_loan.pojo.RenewalResponse;
import com.rivaresa.cusmateogl.renew_loan.pojo.gold_loan.GoldLoanResponse;

public class RenewLoanAction {

    public static final int DEFAULT=0;
    public static final int RENEW_SUCCES=1;
    public static final int API_ERROR=2;
    public static final int CLICK_OK=3;
    public static final int GOLD_LOAN_RENEW_SUCCESS=4;
    public int action;
    public String error;
    public RenewalResponse renewalResponse;
    public GoldLoanResponse goldLoanResponse;

    public GoldLoanResponse getGoldLoanResponse() {
        return goldLoanResponse;
    }

    public void setGoldLoanResponse(GoldLoanResponse goldLoanResponse) {
        this.goldLoanResponse = goldLoanResponse;
    }

    public RenewLoanAction(int action, GoldLoanResponse goldLoanResponse) {
        this.action = action;
        this.goldLoanResponse = goldLoanResponse;
    }

    public RenewLoanAction(int action, RenewalResponse renewalResponse) {
        this.action = action;
        this.renewalResponse = renewalResponse;
    }

    public RenewLoanAction(int action) {
        this.action = action;
    }

    public RenewLoanAction(int action, String error) {
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

    public RenewalResponse getRenewalResponse() {
        return renewalResponse;
    }

    public void setRenewalResponse(RenewalResponse renewalResponse) {
        this.renewalResponse = renewalResponse;
    }
}
