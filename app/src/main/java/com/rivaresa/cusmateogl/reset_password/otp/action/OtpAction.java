package com.rivaresa.cusmateogl.reset_password.otp.action;

import com.rivaresa.cusmateogl.reset_password.otp.pojo.Signup_response.SignUpResponse;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.otp_creation.OtpCreationResponse;
import com.rivaresa.cusmateogl.reset_password.otp.pojo.validate_otp.ValidateOtpResponse;
import com.rivaresa.cusmateogl.signup.pojo.otp_response.OtpGenerateResponse;

public class OtpAction {

    public static final int DEFAULT=-1;
    public static final int OTP_SUCCESS=1;
    public static final int API_ERROR=2;
    public static final int SIGNUP_SUCCESS=3;
    public static final int RESEND_OTP_SUCCESS=4;
    public static final int VALIDATE_OTP_SUCCESS=5;
    public static final int RESEND_LOGIN_OTP_SUCCESS=6;
    public static final int RESET_PASSWORD_SUCCESS=6;
    public int action;
    public String error;
    public SignUpResponse signUpResponse;
    public OtpGenerateResponse resendOtpResponse;
    public ValidateOtpResponse validateOtpResponse;
    public OtpCreationResponse otpCreationResponse;

    public OtpAction(int action, OtpCreationResponse otpCreationResponse) {
        this.action = action;
        this.otpCreationResponse = otpCreationResponse;
    }

    public OtpCreationResponse getOtpCreationResponse() {
        return otpCreationResponse;
    }

    public void setOtpCreationResponse(OtpCreationResponse otpCreationResponse) {
        this.otpCreationResponse = otpCreationResponse;
    }

    public OtpAction(int action, ValidateOtpResponse validateOtpResponse) {
        this.action = action;
        this.validateOtpResponse = validateOtpResponse;
    }

    public ValidateOtpResponse getValidateOtpResponse() {
        return validateOtpResponse;
    }

    public void setValidateOtpResponse(ValidateOtpResponse validateOtpResponse) {
        this.validateOtpResponse = validateOtpResponse;
    }

    public OtpAction(int action, OtpGenerateResponse resendOtpResponse) {
        this.action = action;
        this.resendOtpResponse = resendOtpResponse;
    }

    public OtpAction(int action, SignUpResponse signUpResponse) {
        this.action = action;
        this.signUpResponse = signUpResponse;
    }

    public OtpAction(int action) {
        this.action = action;
    }

    public OtpAction(int action, String error) {
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

    public SignUpResponse getSignUpResponse() {
        return signUpResponse;
    }

    public void setSignUpResponse(SignUpResponse signUpResponse) {
        this.signUpResponse = signUpResponse;
    }

    public OtpGenerateResponse getResendOtpResponse() {
        return resendOtpResponse;
    }

    public void setResendOtpResponse(OtpGenerateResponse resendOtpResponse) {
        this.resendOtpResponse = resendOtpResponse;
    }
}
