package com.riviresa.custmate.ogl.payment.razor_pay.pojo

data class GetOrderIdResponse(
    val Code: String,
    val Data: OrderData,
    val Message: String,
    val Status: String
)

data class OrderData(
    val amount: String,
    val amount_due: String,
    val amount_paid: String,
    val attempts: String,
    val created_at: String,
    val currency: String,
    val entity: String,
    val id: String,
    val notes: List<String>,
    val offer_id: String,
    val receipt: String,
    val status: String
)