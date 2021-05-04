package com.rivaresa.cusmateogl.payment.axis_payment.pojo

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    val Code: String,
    @SerializedName("Data")
    val Data: PaymentData,
    val Message: String,
    val Status: String
)

data class PaymentData(
    val AMT: String,
    val BRN: String,
    val CID: String,
    val CKS: String,
    val CNY: String,
    val CRN: String,
    val PMD: String,
    val RID: String,
    val RMK: String,
    val STC: String,
    val TET: String,
    val TRN: String,
    val TYP: String,
    val VER: String
)