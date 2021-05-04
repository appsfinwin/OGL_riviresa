package com.rivaresa.cusmateogl.finialize_amount.action;

import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementDetailsResponse;

public class FinalizeAmountAction {

    public static final int DEFAULT=0;
    public static final int SETTLEMENT_SUCCESS=1;
    public static final int SETTLEMENT_ERROR=2;
    public static final int CLICK_CALCULATOR=3;
    public static final int CLICK_NEXT=4;
    public static final int CLICK_SETTINGS=5;

    public int action;
    public String error;
    public SettlementDetailsResponse settlementDetailsResponse;
    public FinalizeAmountAction(int action) {
        this.action = action;
    }

    public FinalizeAmountAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public FinalizeAmountAction(int action, SettlementDetailsResponse settlementDetailsResponse) {
        this.action = action;
        this.settlementDetailsResponse = settlementDetailsResponse;
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

    public SettlementDetailsResponse getSettlementDetailsResponse() {
        return settlementDetailsResponse;
    }

    public void setSettlementDetailsResponse(SettlementDetailsResponse settlementDetailsResponse) {
        this.settlementDetailsResponse = settlementDetailsResponse;
    }
}
