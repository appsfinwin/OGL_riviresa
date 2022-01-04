package com.riviresa.custmate.ogl.payment.razor_pay.pojo

data class GetKeyResponse(
    val Code: String,
    val Data: KeyData,
    val Message: String,
    val Status: String
)

data class KeyData(
    val Status: String,
    val key: String
)