package com.rivaresa.cusmateogl.login.action;

import com.rivaresa.cusmateogl.login.pojo.LoginResponse;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.otp_creation.OtpCreationResponse;

public class LoginAction {
    public static final int LOGIN_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int DEFAULT=0;
    public static final int CLICK_FORGOT_PASSWORD=3;
    public static final int CLICK_SIGN_UP=4;
    public static final int OTP_SUCCESS=5;

    int action;
    String error;

    public LoginResponse loginResponse;
    public OtpCreationResponse otpCreationResponse;

    public LoginAction(int action, OtpCreationResponse otpCreationResponse) {
        this.action = action;
        this.otpCreationResponse = otpCreationResponse;
    }

    public LoginAction(int action) {
        this.action = action;
    }

    public LoginAction(int action, LoginResponse loginResponse) {
        this.action = action;
        this.loginResponse = loginResponse;
    }

    public LoginAction(int action, String error) {
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

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public OtpCreationResponse getOtpCreationResponse() {
        return otpCreationResponse;
    }

    public void setOtpCreationResponse(OtpCreationResponse otpCreationResponse) {
        this.otpCreationResponse = otpCreationResponse;
    }
}
