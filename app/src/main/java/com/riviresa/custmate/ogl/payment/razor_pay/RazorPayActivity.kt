package com.riviresa.custmate.ogl.payment.razor_pay

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.riviresa.custmate.R
import com.riviresa.custmate.databinding.ActivityRazorPayBinding
import com.riviresa.custmate.ogl.payment.razor_pay.action.RazorpayAction
import com.riviresa.custmate.ogl.payment.razor_pay.pojo.RazorpayError
import com.riviresa.custmate.ogl.payment.razor_pay.pojo.RazorpayError3
import org.json.JSONObject

class RazorPayActivity : com.riviresa.custmate.ogl.BaseActivity(), PaymentResultWithDataListener {

    private val TAG = "RazorPayActivity"
    var flag: String? = null
    var scheme_code: kotlin.String? = null
    var scheme_name: kotlin.String? = null
    var scheme_interest: kotlin.String? = null
    var scheme_period: kotlin.String? = null
    var net_amount: kotlin.String? = null
    var scheme_period_type: String? = null
    var loanAmount: kotlin.String? = null
    var inventoryNo: kotlin.String? = null
    var customerId: kotlin.String? = null
    var loan_account_number: kotlin.String? = null
    var paymentType: kotlin.String? = null
    var ifsc = ""
    var account_number: kotlin.String? = ""
    private lateinit var viewModel: RazorPayViewModel
    private lateinit var binding: ActivityRazorPayBinding
    lateinit var warningDialog: Dialog
    lateinit var successDialog: Dialog
    var orderId = ""
    var razorKey = ""
    var amount: Double = 0.0
    var tranId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_razor_pay)
        viewModel = ViewModelProvider(this)[RazorPayViewModel::class.java]
        binding.viewModel = viewModel
        Checkout.preload(applicationContext)

        if (intent.getStringExtra("from") == "gold_loan") {
            net_amount = intent.getStringExtra("net_amount")
            scheme_code = intent.getStringExtra("scheme_code")
            scheme_name = intent.getStringExtra("scheme_name")
            scheme_interest = intent.getStringExtra("scheme_interest")
            scheme_period = intent.getStringExtra("scheme_period")
            loanAmount = intent.getStringExtra("loan_amount")
            scheme_period_type = intent.getStringExtra("scheme_period_type")
            inventoryNo = sharedPreferences.getString("inventory_number", "")
            customerId = sharedPreferences.getString("cust_id", "")
            loan_account_number = sharedPreferences.getString("account_number", "")
        } else if (intent.getStringExtra("from") == "pay_online") {
            loan_account_number = sharedPreferences.getString("account_number", "")
            net_amount = intent.getStringExtra("net_amount")
            flag = intent.getStringExtra("flag")
            paymentType = intent.getStringExtra("paymentType")

            //viewModel.initLoading(this)
            //viewModel.getToken(loan_account_number, net_amount, DataHolder.getInstance().loginData.name)
            amount = (net_amount?.toDouble())?.times(100)!!
            viewModel.initLoading(this)
            viewModel.getRazorKey()

            //startPayment(amount)
        }

        viewModel.mAction.observe(this, Observer {
            viewModel.cancelLoading()
            when (it.action) {
                RazorpayAction.API_ERROR -> {

                    var dialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    dialog.setTitleText("ERROR!")
                    dialog.setCancelable(false)
                    dialog.setConfirmClickListener {
                        it.dismiss()
                        finish()
                    }
                    dialog.setContentText(it.error)
                    dialog.show()

                }
                RazorpayAction.GET_ORDER_ID_SUCCESS -> {
                    orderId = it.getOrderIdResponse?.Data?.id.toString()
                    if (razorKey != "") {
                        startPayment(amount, orderId)
                    }
                }
                RazorpayAction.PAYMENT_SUCCESS -> {
                    showSuccess(tranId)
                }
                RazorpayAction.PAYMENT_ERROR -> {
                    showError(error = "Payment Failed!", 111)
                }
                RazorpayAction.GET_RAZOR_KEY_SUCCESS -> {
                    razorKey = it.getKeyResponse?.Data?.key ?: ""
                    viewModel.initLoading(this)
                    viewModel.getOrderId(amount = amount.toInt(), loan_account_number)
                }

            }
        })

    }

    private fun startPayment(amount: Double?, orderId: String) {
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID(razorKey)
        try {
            val image = R.drawable.riviresa_logo // Can be any drawable
            co.setImage(image)
            co.setFullScreenDisable(false)
            val options = JSONObject()
            options.put("name", paymentType)
            options.put("description", "Riviresa Nidhi ltd")
            options.put("order_id", orderId) //from response of step 3.
            options.put("theme.color", "#c91854")
            options.put("currency", "INR")
            options.put("amount", amount) //pass amount in currency subunits
            options.put("prefill.email", sharedPreferences.getString("email", ""))
            options.put("prefill.contact", sharedPreferences.getString("phone", ""))
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 2)
            options.put("retry", retryObj)
            co.open(activity, options)
        } catch (e: Exception) {
            e.printStackTrace()
            showError(error = "Payment Failure!", 111)
        }
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Cancel payment?")
            .setMessage("Do you want to cancel the payment ?")
            .setPositiveButton("Yes") { dialog, which ->
//                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
//                    startActivity(intent)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    @SuppressLint("ResourceAsColor")
    fun showError(error: String?, i: Int) {
        warningDialog = Dialog(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.layout_payment_error, null)
        warningDialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        warningDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val errorMessage = view.findViewById<TextView>(R.id.error_title)
        val ok = view.findViewById<Button>(R.id.btn_ok)
        var imgPayment = view.findViewById<ImageView>(R.id.img_error)

        errorMessage.text = error
        when (i) {
            101 -> {
                imgPayment.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_payment_pending // Drawable
                    )
                )
            }
            111 -> {
                imgPayment.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_payment_error // Drawable
                    )
                )
            }
        }

        ok.setOnClickListener {
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            warningDialog.dismiss()
        }
        warningDialog.setContentView(view)

        warningDialog.getWindow()?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        warningDialog.setCanceledOnTouchOutside(false)
        warningDialog.setCancelable(false)
        warningDialog.show()
    }

    fun showSuccess(tran_id: String?) {
        successDialog = Dialog(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.layout_payment_success, null)
        val tranId = view.findViewById<TextView>(R.id.tv_tran_id)
        val ok = view.findViewById<Button>(R.id.btn_ok)
        tranId.text = tran_id
        // val window: Window? = successDialog.window

        ok.setOnClickListener {
            //                finish();
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            //Intent intent1=new Intent(ConfirmationActivity.this, RenewLoanActivity.class);

            val intent1 = Intent(
                this@RazorPayActivity,
                com.riviresa.custmate.ogl.renew_loan.RenewLoanActivity::class.java
            )

            intent1.putExtra("net_amount", net_amount)
            intent1.putExtra("flag", flag)
            intent1.putExtra("from", "pay_online")
            startActivity(intent1)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish();

            successDialog.dismiss()
        }
        successDialog.setContentView(view)
        successDialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        successDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE));
        successDialog.setCanceledOnTouchOutside(false)
        successDialog.setCancelable(false)
        successDialog.show()
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {


        Log.d(TAG, "onPaymentSuccess: $p0 , data: ${p1.toString()}")
        if (p0 != null) {
            tranId = p0
        }
        var paymentData = p1
        viewModel.initLoading(this)
        if (paymentData != null) {
            viewModel.verifyPayment(
                paymentData.orderId,
                paymentData.paymentId,
                paymentData.signature
            )
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

        if (razorKey.startsWith("rzp_test")) {

            var gson = Gson()

            if (p1 != null) {
                if (p1.contains("errror:")) {

                    val error: RazorpayError3 = gson.fromJson(p1, RazorpayError3::class.java)
                    showError(error = "Payment Failure!", 111)
                } else {
                    val error: RazorpayError = gson.fromJson(p1, RazorpayError::class.java)
                    if (error.error.description == "Payment processing cancelled by user")
                        showError(error = "Payment Cancelled!", 111)
                    else
                        showError(error = "Payment Failed!", 111)
                }



                Log.d(TAG, "onPaymentError:$p0, $p1 , $p2")
            }
        } else {
            if (p2 != null) {
                var paymentData = p2
                paymentData = p2
                showError(error = "Payment Failed!", 111)
            }
        }
    }
}