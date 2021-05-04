package com.rivaresa.cusmateogl.final_confirmation.action;

import com.rivaresa.cusmateogl.final_confirmation.pojo.ApplictaionDetailsResponse;

public class ConfirmationAction {

    public static final int DEFAULT=0;
    public static final int APPLICATION_DETAILS_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int CLICK_CONFIRM=3;
    public static final int CLICK_CANCEL=4;
    public static final int CLICK_SETTINGS=5;
    public int action;
    public String error;
    public ApplictaionDetailsResponse applictaionDetailsResponse;

    public ConfirmationAction(int action, ApplictaionDetailsResponse applictaionDetailsResponse) {
        this.action = action;
        this.applictaionDetailsResponse = applictaionDetailsResponse;
    }

    public ConfirmationAction(int action) {
        this.action = action;
    }

    public ConfirmationAction(int action, String error) {
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

    public ApplictaionDetailsResponse getApplictaionDetailsResponse() {
        return applictaionDetailsResponse;
    }

    public void setApplictaionDetailsResponse(ApplictaionDetailsResponse applictaionDetailsResponse) {
        this.applictaionDetailsResponse = applictaionDetailsResponse;
    }
}
