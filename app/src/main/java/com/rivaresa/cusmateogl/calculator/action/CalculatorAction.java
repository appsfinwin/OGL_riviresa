package com.rivaresa.cusmateogl.calculator.action;

import com.rivaresa.cusmateogl.calculator.pojo.CalculatorResponse;

public class CalculatorAction {
    public static final int DEFAULT=0;
    public static final int CALCULATE_SUCCESS=1;
    public static final int API_ERROR=2;
    public int action;
    public String error;
    public CalculatorResponse calculatorResponse;
    public CalculatorAction(int action) {
        this.action = action;
    }

    public CalculatorAction(int action, String error) {
        this.action = action;
        this.error = error;
    }

    public CalculatorAction(int action, CalculatorResponse calculatorResponse) {
        this.action = action;
        this.calculatorResponse = calculatorResponse;
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

    public CalculatorResponse getCalculatorResponse() {
        return calculatorResponse;
    }

    public void setCalculatorResponse(CalculatorResponse calculatorResponse) {
        this.calculatorResponse = calculatorResponse;
    }
}
