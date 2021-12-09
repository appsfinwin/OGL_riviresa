package com.riviresa.custmate.ogl.payment.paytm.adapter;

import androidx.databinding.BaseObservable;

import com.riviresa.custmate.ogl.payment.paytm.pojo.SettlementData;
import com.riviresa.custmate.ogl.payment.paytm.pojo.SettlementData;

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
