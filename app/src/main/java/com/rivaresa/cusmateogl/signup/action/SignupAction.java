package com.rivaresa.cusmateogl.signup.action;


import com.rivaresa.cusmateogl.reset_password.otp.pojo.Signup_response.SignUpResponse;
import com.rivaresa.cusmateogl.signup.pojo.otp_response.OtpGenerateResponse;

public class SignupAction {
    public static final int DEFAULT=-1;
    public static final int CLICK_SIGN_UP=1;
    public static final int CLICK_SIGN_IN=2;
    public static final int CLICK_BACK=3;
    public static final int OTP_SUCCESS=4;
    public static final int API_ERROR=5;
    public static final int SIGNUP_SUCCESS=6;

    public int action;
    public String error;
    public OtpGenerateResponse otpGenerateResponse;
    public SignUpResponse signUpResponse;

    public SignupAction(int action, SignUpResponse signUpResponse) {
        this.action = action;
        this.signUpResponse = signUpResponse;
    }

    public SignupAction(int action, OtpGenerateResponse otpGenerateResponse) {
        this.action = action;
        this.otpGenerateResponse = otpGenerateResponse;
    }

    public SignupAction(int action) {
        this.action = action;
    }

    public SignupAction(int action, String error) {
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

    public OtpGenerateResponse getOtpGenerateResponse() {
        return otpGenerateResponse;
    }

    public void setOtpGenerateResponse(OtpGenerateResponse otpGenerateResponse) {
        this.otpGenerateResponse = otpGenerateResponse;
    }

    public SignUpResponse getSignUpResponse() {
        return signUpResponse;
    }

    public void setSignUpResponse(SignUpResponse signUpResponse) {
        this.signUpResponse = signUpResponse;
    }
}
