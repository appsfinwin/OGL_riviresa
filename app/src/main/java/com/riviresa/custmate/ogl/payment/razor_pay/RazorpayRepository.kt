package com.riviresa.custmate.ogl.payment.razor_pay

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.riviresa.custmate.ogl.payment.razor_pay.action.RazorpayAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object RazorpayRepository {

    lateinit var mAction: MutableLiveData<RazorpayAction>

    @SuppressLint("CheckResult")
    fun getOrderId(apiInterface: com.riviresa.custmate.ogl.retrofit.ApiInterface, body: RequestBody) {
        val observable = apiInterface.getOrderId(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.Data != null) {
                        if (response.Status == "Y") {
                            mAction.value = RazorpayAction(
                                RazorpayAction.GET_ORDER_ID_SUCCESS,
                                response
                            )
                        } else {
                            mAction.value = RazorpayAction(
                                RazorpayAction.API_ERROR,
                                response.Message
                            )
                        }
                    }else {
                        mAction.value = RazorpayAction(
                            RazorpayAction.API_ERROR,
                            response.Message
                        )
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    "Timeout!! Please try again")
                        }
                        is UnknownHostException -> {
                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    "No Internet")
                        }
                        else -> {

                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }


    @SuppressLint("CheckResult")
    fun getKey(apiInterface: com.riviresa.custmate.ogl.retrofit.ApiInterface, body: RequestBody) {
        val observable = apiInterface.getRazorPayKey(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.Data != null) {
                        if (response.Status == "Y") {
                            mAction.value = RazorpayAction(
                                RazorpayAction.GET_RAZOR_KEY_SUCCESS,
                                response
                            )
                        } else {
                            mAction.value = RazorpayAction(
                                RazorpayAction.API_ERROR,
                                response.Message
                            )
                        }
                    }else {
                        mAction.value = RazorpayAction(
                            RazorpayAction.API_ERROR,
                            response.Message
                        )
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    "Timeout!! Please try again")
                        }
                        is UnknownHostException -> {
                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    "No Internet")
                        }
                        else -> {

                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }


    @SuppressLint("CheckResult")
    fun verifyPayment(apiInterface: com.riviresa.custmate.ogl.retrofit.ApiInterface, body: RequestBody) {
        val observable = apiInterface.verifyPayment(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.Data != null) {
                        if (response.Data.Status == "success") {
                            mAction.value = RazorpayAction(
                                RazorpayAction.PAYMENT_SUCCESS,
                                response
                            )
                        } else {
                            mAction.value = RazorpayAction(
                                RazorpayAction.PAYMENT_ERROR,
                                response.Message
                            )
                        }
                    }else {
                        mAction.value = RazorpayAction(
                            RazorpayAction.API_ERROR,
                            response.Message
                        )
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    "Timeout!! Please try again")
                        }
                        is UnknownHostException -> {
                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    "No Internet")
                        }
                        else -> {

                            mAction.value =
                                RazorpayAction(
                                    RazorpayAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

}