package com.riviresa.custmate.ogl.payment.razor_pay

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riviresa.custmate.ogl.payment.razor_pay.action.RazorpayAction
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class RazorPayViewModel : ViewModel() {


    var mAction: MutableLiveData<RazorpayAction> = MutableLiveData()
    init {
        RazorpayRepository.mAction=mAction
    }


    var loading: Dialog? = null
    fun initLoading(context: Context?) {
        loading = com.riviresa.custmate.ogl.utils.Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    public fun getOrderId(amount: Int, accountNumber: String?) {

        var refNumber= com.riviresa.custmate.ogl.utils.Services.getRandomNumber()
        val jsonParams: MutableMap<String?, Any?> = HashMap()
        val notesArray= arrayListOf<String>()
        jsonParams["amount"] = amount
        jsonParams["currency"] = "INR"
        jsonParams["receipt"] = "rcptid_$refNumber"
        notesArray.add("accno : $accountNumber")
        jsonParams["notes"] = notesArray


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        var apiInterface = com.riviresa.custmate.ogl.retrofit.RetrofitClient.RetrofitRazorPay()?.create(
            com.riviresa.custmate.ogl.retrofit.ApiInterface::class.java)!!
        RazorpayRepository.getOrderId(apiInterface, body)
    }

    public fun verifyPayment(orderId: String, paymentId: String, signature: String) {

        var refNumber= com.riviresa.custmate.ogl.utils.Services.getRandomNumber()
        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["razorpay_payment_id"] = paymentId
        jsonParams["razorpay_order_id"] = orderId
        jsonParams["razorpay_signature"] = signature


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        var apiInterface = com.riviresa.custmate.ogl.retrofit.RetrofitClient.RetrofitRazorPay()?.create(
            com.riviresa.custmate.ogl.retrofit.ApiInterface::class.java)!!
        RazorpayRepository.verifyPayment(apiInterface, body)
    }

}

