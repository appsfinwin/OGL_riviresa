package com.rivaresa.cusmateogl.reset_password.reset_password.action;

import com.rivaresa.cusmateogl.reset_password.forgot_password.pojo.ResetPasswordResponse;

public class ResetPasswordAction {
    public static final int DEFAULT=-1;
    public static final int RESET_OTP_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int RESEND_OTP_SUCCESS=3;
    public int action;
    public String error;
    public ResetPasswordResponse resetPasswordResponse;

    public ResetPasswordAction(int action, ResetPasswordResponse resetPasswordResponse) {
        this.action = action;
        this.resetPasswordResponse = resetPasswordResponse;
    }

    public ResetPasswordResponse getResetPasswordResponse() {
        return resetPasswordResponse;
    }

    public void setResetPasswordResponse(ResetPasswordResponse resetPasswordResponse) {
        this.resetPasswordResponse = resetPasswordResponse;
    }

    public ResetPasswordAction(int action) {
        this.action = action;
    }

    public ResetPasswordAction(int action, String error) {
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
}
