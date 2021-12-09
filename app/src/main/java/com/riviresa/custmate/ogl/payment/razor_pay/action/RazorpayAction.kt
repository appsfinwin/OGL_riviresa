package com.riviresa.custmate.ogl.payment.razor_pay.action
import com.riviresa.custmate.ogl.payment.razor_pay.pojo.GetOrderIdResponse
import com.riviresa.custmate.ogl.payment.razor_pay.pojo.VerifyPaymentResponse


class RazorpayAction {

    companion object{
        const val DEFAULT=-1
        const val API_ERROR=1
        const val GET_ORDER_ID_SUCCESS=2
        const val PAYMENT_SUCCESS=3
        const val PAYMENT_ERROR=4
    }
    var action: Int?=null
    var getOrderIdResponse: GetOrderIdResponse?= null
    var error: String ?=null
    var verifyPaymentResponse: VerifyPaymentResponse? =null


    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, error: String?) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, getOrderIdResponse: GetOrderIdResponse?) {
        this.action = action
        this.getOrderIdResponse = getOrderIdResponse
    }

    constructor(action: Int?, verifyPaymentResponse: VerifyPaymentResponse?) {
        this.action = action
        this.verifyPaymentResponse = verifyPaymentResponse
    }


}