package com.rivaresa.cusmateogl.gold_loan.action;


import com.rivaresa.cusmateogl.account_details.pojo.GetBankDetailsResponse;
import com.rivaresa.cusmateogl.gold_loan.pojo.terms_and_conditions.TermsResponse;

public class GoldLoanAction {
    int action;
    String error;
    public static final int DEFAULT=0;
    public static final int TERMS_API_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int CLICK_CONTINUE=3;
    public static final int CLICK_CHANGE_ACCOUNT=4;
    public static final int BANK_DETAILS_SUCCESS=5;
    public static final int BANK_DETAILS_ERROR=6;

    public TermsResponse termsResponse;
    public GetBankDetailsResponse getBankDetailsResponse;

    public GoldLoanAction(int action, GetBankDetailsResponse getBankDetailsResponse) {
        this.action = action;
        this.getBankDetailsResponse = getBankDetailsResponse;
    }

    public GoldLoanAction(int action) {
        this.action = action;
    }

    public GoldLoanAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public GoldLoanAction(int action, TermsResponse termsResponse) {
        this.action = action;
        this.termsResponse = termsResponse;
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

    public TermsResponse getTermsResponse() {
        return termsResponse;
    }

    public void setTermsResponse(TermsResponse termsResponse) {
        this.termsResponse = termsResponse;
    }
}
