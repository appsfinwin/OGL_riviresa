package com.riviresa.custmate.ogl.payment.razor_pay.pojo

data class RazorpayError3(
    val code: String,
    val description: String,
    val metadata: Metadata,
    val reason: String,
    val source: String,
    val step: String
)

data class Metadata(
    val order_id: String
)