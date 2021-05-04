package com.rivaresa.cusmateogl.reset_password.forgot_password;

import com.rivaresa.cusmateogl.reset_password.forgot_password.pojo.ResetPasswordResponse;

public class ForgotPasswordAction {
    public static final int DEFAULT=-1;
    public static final int GENERATE_OTP_SUCCESS=1;
    public static final int RESET_OTP_SUCCESS=2;
    public static final int API_ERROR=3;

    int action;
    String error;
    ResetPasswordResponse resetPasswordResponse;

    public ResetPasswordResponse getResetPasswordResponse() {
        return resetPasswordResponse;
    }

    public void setResetPasswordResponse(ResetPasswordResponse resetPasswordResponse) {
        this.resetPasswordResponse = resetPasswordResponse;
    }

    public ForgotPasswordAction(int action, ResetPasswordResponse resetPasswordResponse) {
        this.action = action;
        this.resetPasswordResponse = resetPasswordResponse;
    }

    public ForgotPasswordAction(int action) {
        this.action = action;
    }


    public ForgotPasswordAction(int action, String error) {
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
