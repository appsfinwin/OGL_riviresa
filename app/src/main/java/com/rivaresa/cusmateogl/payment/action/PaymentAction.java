package com.rivaresa.cusmateogl.payment.action;

import com.rivaresa.cusmateogl.payment.axis_payment.pojo.PaymentResponse;
import com.rivaresa.cusmateogl.payment.axis_payment.pojo.TokenResponse;
import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementDetailsResponse;
import com.rivaresa.cusmateogl.payment.paytm.pojo.checksum.ChecksumResponse;

public class PaymentAction {

    public static final int DEFAULT=0;
    public static final int API_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int CHECKSUM_SUCCESS=3;
    public static final int CLICK_PAY=4;
    public static final int TOKEN_GENERATION_SUCCESS=5;
    public static final int AXIS_PAYMENT_SUCCESS=6;
    public static final int AXIS_PAYMENT_ERROR=7;
    public static final int TOKEN_GENERATION_ERROR=8;
    public static final int CLICK_SETTINGS=9;

    public SettlementDetailsResponse settlementDetailsResponse;
    public ChecksumResponse checksumResponse;
    public TokenResponse tokenResponse;
    public PaymentResponse paymentResponse;

    int action;
    String error;

    public PaymentAction( int action,PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
        this.action = action;
    }

    public PaymentAction(int action, TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
        this.action = action;
    }

    public PaymentAction(int action) {
        this.action = action;
    }

    public PaymentAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public PaymentAction(int action,SettlementDetailsResponse settlementDetailsResponse) {
        this.settlementDetailsResponse = settlementDetailsResponse;
        this.action = action;
    }

    public SettlementDetailsResponse getSettlementDetailsResponse() {
        return settlementDetailsResponse;
    }

    public void setSettlementDetailsResponse(SettlementDetailsResponse settlementDetailsResponse) {
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

    public PaymentAction( int action,ChecksumResponse checksumResponse) {
        this.checksumResponse = checksumResponse;
        this.action = action;
    }

    public ChecksumResponse getChecksumResponse() {
        return checksumResponse;
    }

    public void setChecksumResponse(ChecksumResponse checksumResponse) {
        this.checksumResponse = checksumResponse;
    }

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }
}
