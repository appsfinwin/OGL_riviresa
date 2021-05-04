package com.rivaresa.cusmateogl.payment.paytm.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettlementData {

    @SerializedName("Particular")
    @Expose
    private String particular;
    @SerializedName("Total Received")
    @Expose
    private String totalReceived;
    @SerializedName("Balance")
    @Expose
    private String balance;
    @SerializedName("Overdue")
    @Expose
    private String overdue;
    @SerializedName("Current Receipt")
    @Expose
    private String currentReceipt;

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(String totalReceived) {
        this.totalReceived = totalReceived;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getCurrentReceipt() {
        return currentReceipt;
    }

    public void setCurrentReceipt(String currentReceipt) {
        this.currentReceipt = currentReceipt;
    }

}