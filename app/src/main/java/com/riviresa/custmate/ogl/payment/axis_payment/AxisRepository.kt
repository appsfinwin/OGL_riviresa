package com.riviresa.custmate.ogl.payment.axis_payment

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.riviresa.custmate.ogl.payment.action.PaymentAction
import com.riviresa.custmate.ogl.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AxisRepository {

    lateinit var INSTANCE: AxisRepository
    lateinit var mAction: MutableLiveData<com.riviresa.custmate.ogl.payment.action.PaymentAction>

    fun getInstance(): AxisRepository {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = AxisRepository()
        }
        return INSTANCE
    }

    @SuppressLint("CheckResult")
    fun getToken(apiInterface: com.riviresa.custmate.ogl.retrofit.ApiInterface, body: RequestBody) {
        val observable = apiInterface.generateToken(body)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            if (response.Data != null) {
                                if (response.Status == "Y") {
                                    mAction.value =
                                        com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                            com.riviresa.custmate.ogl.payment.action.PaymentAction.TOKEN_GENERATION_SUCCESS,
                                            response
                                        )
                                } else {
                                    mAction.value =
                                        com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                            com.riviresa.custmate.ogl.payment.action.PaymentAction.TOKEN_GENERATION_ERROR,
                                            response.Message
                                        )
                                }
                            }else {
                                mAction.value =
                                    com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                        com.riviresa.custmate.ogl.payment.action.PaymentAction.TOKEN_GENERATION_ERROR,
                                        response.Message
                                    )
                            }
                        }, { error ->

                    if (error is SocketTimeoutException) {
                        mAction.value =
                            com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                com.riviresa.custmate.ogl.payment.action.PaymentAction.TOKEN_GENERATION_ERROR,
                                "Timeout!! Please try again"
                            )
                    } else if (error is UnknownHostException) {
                        mAction.value =
                            com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                com.riviresa.custmate.ogl.payment.action.PaymentAction.TOKEN_GENERATION_ERROR,
                                "No Internet"
                            )
                    } else {

                        mAction.value =
                            com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                com.riviresa.custmate.ogl.payment.action.PaymentAction.TOKEN_GENERATION_ERROR,
                                error.message.toString()
                            )
                    }
                }
                )

    }
    @SuppressLint("CheckResult")
    fun getPaymentResponse(apiInterface: com.riviresa.custmate.ogl.retrofit.ApiInterface, body : String) {
        val observable = apiInterface.getPaymentResponse(body)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            if (response.Data != null) {
                                if (response.Status == "Y") {
                                    mAction.value =
                                        com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                            com.riviresa.custmate.ogl.payment.action.PaymentAction.AXIS_PAYMENT_SUCCESS,
                                            response
                                        )
                                } else {
                                    mAction.value =
                                        com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                            com.riviresa.custmate.ogl.payment.action.PaymentAction.API_ERROR,
                                            response.Message
                                        )
                                }
                            }
                        }, { error ->

                    if (error is SocketTimeoutException) {
                        mAction.value =
                            com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                com.riviresa.custmate.ogl.payment.action.PaymentAction.API_ERROR,
                                "Timeout!! Please try again"
                            )
                    } else if (error is UnknownHostException) {
                        mAction.value =
                            com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                com.riviresa.custmate.ogl.payment.action.PaymentAction.API_ERROR,
                                "No Internet"
                            )
                    } else {

                        mAction.value =
                            com.riviresa.custmate.ogl.payment.action.PaymentAction(
                                com.riviresa.custmate.ogl.payment.action.PaymentAction.API_ERROR,
                                error.message.toString()
                            )
                    }
                }
                )

    }

}