package com.rivaresa.cusmateogl.gold_loan.select_scheme.adapter;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.rivaresa.cusmateogl.gold_loan.select_scheme.action.RowSchemeAction;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.pojo.SchemeData;

public class RowSchemesViewmodel extends BaseObservable {
    SchemeData schemeData;
    MutableLiveData<RowSchemeAction> mAction;
    String name,period,interest,eligibleAmount,settlementTotal,netAmount;
    public RowSchemesViewmodel(SchemeData schemeData, MutableLiveData<RowSchemeAction> mAction) {
        this.schemeData = schemeData;
        this.mAction=mAction;
    }

    public void setSchemeData(SchemeData schemeData, MutableLiveData<RowSchemeAction> mAction) {
        this.schemeData = schemeData;
        this.mAction=mAction;
    }



    public String getName() {
        name=schemeData.getName();
        return name;
    }

    public String getPeriod() {
        period=schemeData.getPeriod();
        return period;
    }

    public String getInterest() {
//        if (schemeData.getInterest()==null){
//            interest="";
//        }else {
//            interest = schemeData.getInterest();
//        }
        interest = schemeData.getInterest();
        return interest;
    }

    public String getEligibleAmount() {
        eligibleAmount=schemeData.getEligibleAmount();
        return eligibleAmount;
    }

    public String getSettlementTotal() {
        settlementTotal=schemeData.getSettlementTotal();
        return settlementTotal;
    }

    public String getNetAmount() {
        netAmount=schemeData.getNetAmtAvailable();
        return netAmount;
    }

    public void clickSelect(View view)
    {
        mAction.setValue(new RowSchemeAction(RowSchemeAction.CLICK_SELECT,schemeData));
    }

    public void clickSettlementDetails(View view)
    {
        mAction.setValue(new RowSchemeAction(RowSchemeAction.CLICK_SETTLEMENT,schemeData));
    }
}
