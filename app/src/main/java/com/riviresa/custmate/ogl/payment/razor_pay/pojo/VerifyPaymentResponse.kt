package com.riviresa.custmate.ogl.payment.razor_pay.pojo

data class VerifyPaymentResponse(
    val Code: String,
    val Data: Data,
    val Message: String,
    val Status: String
)

data class Data(
    val Message: String,
    val Status: String
)