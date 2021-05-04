package com.rivaresa.cusmateogl.payment.axis_payment.pojo

data class TokenResponse(
    val Code: String,
    val Data: Data,
    val Message: String,
    val Status: String
)

data class Data(
    val iData: String,
    val token: String
)