package com.rivaresa.cusmateogl.gold_loan.select_scheme.action;

import com.rivaresa.cusmateogl.gold_loan.select_scheme.pojo.SchemesResponse;
import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementDetailsResponse;

public class SelectSchemeAction {
    public static final int DEFAULT=0;
    public static final int SCHEMES_SUCCESS=1;
    public static final int SCHEMES_ERROR=2;
    public static final int SETTLEMENT_SUCCESS=3;
    public static final int SETTLEMENT_ERROR=4;

    public int action;
    public String error;
    public SchemesResponse schemesResponse;
    public SettlementDetailsResponse settlementDetailsResponse;

    public SelectSchemeAction(int action, SettlementDetailsResponse settlementDetailsResponse) {
        this.action = action;
        this.settlementDetailsResponse = settlementDetailsResponse;
    }

    public SelectSchemeAction(int action, SchemesResponse schemesResponse) {
        this.action = action;
        this.schemesResponse = schemesResponse;
    }

    public SchemesResponse getSchemesResponse() {
        return schemesResponse;
    }

    public void setSchemesResponse(SchemesResponse schemesResponse) {
        this.schemesResponse = schemesResponse;
    }

    public SelectSchemeAction(int action) {
        this.action = action;
    }

    public SelectSchemeAction(int action, String error) {
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

    public SettlementDetailsResponse getSettlementDetailsResponse() {
        return settlementDetailsResponse;
    }

    public void setSettlementDetailsResponse(SettlementDetailsResponse settlementDetailsResponse) {
        this.settlementDetailsResponse = settlementDetailsResponse;
    }
}
