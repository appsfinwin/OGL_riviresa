package com.rivaresa.cusmateogl.account_details.closed_loans.action;

import com.rivaresa.cusmateogl.account_details.closed_loans.pojo.ClosedLoansResponse;

public class ClosedLoanAction {
    int action;
    String error;
    public static final int DEFAULT=0;
    public static final int API_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int NO_DATA=3;
    public ClosedLoansResponse closedLoansResponse;

    public ClosedLoanAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public ClosedLoanAction(int action) {
        this.action = action;
    }

    public ClosedLoanAction(int action, ClosedLoansResponse closedLoansResponse) {
        this.action = action;
        this.closedLoansResponse = closedLoansResponse;
    }

    public ClosedLoansResponse getClosedLoansResponse() {
        return closedLoansResponse;
    }

    public void setClosedLoansResponse(ClosedLoansResponse closedLoansResponse) {
        this.closedLoansResponse = closedLoansResponse;
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
