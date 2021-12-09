package com.riviresa.custmate.ogl.payment.razor_pay.pojo

data class RazorpayError(
    val checkout_logo: String,
    val custom_branding: Boolean,
    val error: Error,
    val http_status_code: Int,
    val org_logo: String,
    val org_name: String
)

data class Error(
    val code: String,
    val description: String,
    val metadata: Metadata,
    val reason: String,
    val source: String,
    val step: String
)

