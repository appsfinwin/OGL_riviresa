package com.rivaresa.cusmateogl.payment.paytm.adapter;

import androidx.databinding.BaseObservable;

import com.rivaresa.cusmateogl.payment.paytm.pojo.SettlementData;

public class RowPaymentViewmodel extends BaseObservable {
    String particular,balance;
    SettlementData settlementData;

    public RowPaymentViewmodel(SettlementData settlementData) {
        this.settlementData = settlementData;
    }

    public void setData(SettlementData settlementData) {
        this.settlementData=settlementData;
        notifyChange();
    }

    public String getBalance() {
        balance=settlementData.getBalance();
        return balance;
    }

    public String getParticular() {
        particular= settlementData.getParticular();
        return particular;
    }
}
