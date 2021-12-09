package com.riviresa.custmate.ogl.payment.axis_payment

import android.app.Application
import android.app.Dialog
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.riviresa.custmate.ogl.payment.action.PaymentAction
import com.riviresa.custmate.ogl.retrofit.ApiInterface
import com.riviresa.custmate.ogl.retrofit.RetrofitClient
import com.riviresa.custmate.ogl.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class AxisViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var apiInterface: com.riviresa.custmate.ogl.retrofit.ApiInterface
    var mAction: MutableLiveData<com.riviresa.custmate.ogl.payment.action.PaymentAction> = MutableLiveData()
    val repository: AxisRepository= AxisRepository().getInstance()

    init {
        repository.mAction=mAction
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

    public fun getToken(loan_account_number: String?, net_amount: String?, name: String) {

        var refNumber= com.riviresa.custmate.ogl.utils.Services.getRandomNumber()
        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["_REF_NO"] = refNumber
        jsonParams["_strAMT"] = net_amount
        jsonParams["_ACC_NO"] = loan_account_number
        jsonParams["_ACC_NAME"] = name
//        jsonParams["Password"] = ob_password.get()

        val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                JSONObject(jsonParams).toString()
        )

        apiInterface = com.riviresa.custmate.ogl.retrofit.RetrofitClient.RetrofitAxis()?.create(com.riviresa.custmate.ogl.retrofit.ApiInterface::class.java)!!
        repository.getToken(apiInterface, body)
    }

    public fun payment(i: String) {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["i"] =i

        val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                JSONObject(jsonParams).toString()
        )

        apiInterface = com.riviresa.custmate.ogl.retrofit.RetrofitClient.RetrofitAxis()?.create(com.riviresa.custmate.ogl.retrofit.ApiInterface::class.java)!!
        repository.getPaymentResponse(apiInterface, i)
    }
}