package com.rivaresa.cusmateogl.account_details.closed_loans.adapter;

import androidx.databinding.BaseObservable;

import com.rivaresa.cusmateogl.account_details.closed_loans.pojo.LoansData;

public class RowClosedLoansViewmodel extends BaseObservable {

    public LoansData loansData;

    public RowClosedLoansViewmodel(LoansData loansData) {
        this.loansData = loansData;
    }

    String scheme,branch,accountNo,inventoryNo,period,periodType,ornamentsReleased,ornamentsReleasedDate,loanAmount;

    public String getLoanAmount() {
        loanAmount=loansData.getLoanAmount().toString();
        return loanAmount;
    }

    public String getScheme() {
        scheme=loansData.getScheme();
            return scheme;
    }

    public String getBranch() {
        branch=loansData.getBranch();
        return branch;
    }

    public String getAccountNo() {
        accountNo=loansData.getAccountNo();
        return accountNo;
    }

    public String getInventoryNo() {
        inventoryNo=loansData.getInventoryNo();
        return inventoryNo;
    }

    public String getPeriod() {
        period=loansData.getPeriod().toString();
        return period;
    }

    public String getPeriodType() {
        periodType=loansData.getPeriodType();
        return periodType;
    }

    public String getOrnamentsReleased() {
        ornamentsReleased=loansData.getOrnamentsReleased();
        return ornamentsReleased;
    }

    public String getOrnamentsReleasedDate() {
        ornamentsReleasedDate=loansData.getOrnamentsReleasedDate();
        return ornamentsReleasedDate;
    }

    public void setLoansData(LoansData loansData) {
        this.loansData=loansData;
    }
}
