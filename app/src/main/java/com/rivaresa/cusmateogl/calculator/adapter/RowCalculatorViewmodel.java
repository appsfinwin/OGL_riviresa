package com.rivaresa.cusmateogl.calculator.adapter;

import androidx.databinding.BaseObservable;

import com.rivaresa.cusmateogl.calculator.pojo.CalculatorData;

public class RowCalculatorViewmodel extends BaseObservable {
    String day,interest,amount;
    CalculatorData calculatorData;

    public RowCalculatorViewmodel(CalculatorData calculatorData) {
        this.calculatorData = calculatorData;
    }

    public String getDay() {
        day=calculatorData.getDays();
        return day;
    }

    public String getInterest() {
        interest=calculatorData.getInterest()+" %";
        return interest;
    }

    public String getAmount() {
        amount=calculatorData.getAmount().toString();
        return amount;
    }

    public CalculatorData getCalculatorData() {
        return calculatorData;
    }

    public void setCalculatorData(CalculatorData calculatorData) {
        this.calculatorData = calculatorData;
    }
}
