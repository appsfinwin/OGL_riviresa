package com.rivaresa.cusmateogl.payment.axis_payment

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rivaresa.cusmateogl.BaseActivity
import com.rivaresa.cusmateogl.R
import com.rivaresa.cusmateogl.databinding.ActivityAxisPaymentBinding
import com.rivaresa.cusmateogl.payment.action.PaymentAction
import com.rivaresa.cusmateogl.renew_loan.RenewLoanActivity
import com.rivaresa.cusmateogl.utils.DataHolder
import com.rivaresa.cusmateogl.utils.Services

class AxisPaymentActivity : BaseActivity() {

    lateinit var webView: WebView
    lateinit var viewModel: AxisViewModel
    lateinit var binding: ActivityAxisPaymentBinding
    var webUrl = "https://uat-etendering.axisbank.co.in/easypay2.0/frontend/api/sdkpayment"
    var i = ""
    var j = ""
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
    var ifsc = ""
    var account_number: kotlin.String? = ""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var warningDialog: Dialog
    lateinit var successDialog: Dialog
    private var mySwipeRefreshLayout: SwipeRefreshLayout? = null
    private val TAG = "AxisPaymentActivity"
    var loading: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_axis_payment)
        viewModel = ViewModelProvider(this)[AxisViewModel::class.java]
        binding.viewModel = viewModel
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)


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
        }
        else if (intent.getStringExtra("from") == "pay_online") {
            loan_account_number = sharedPreferences.getString("account_number", "")
            net_amount = intent.getStringExtra("net_amount")
            flag = intent.getStringExtra("flag")

            viewModel.initLoading(this)
            viewModel.getToken(loan_account_number, net_amount, DataHolder.getInstance().loginData.name)
        }
        webView = binding.webView

        val webSettings: WebSettings = webView.getSettings()
        webSettings.javaScriptEnabled = true

        viewModel.mAction.observe(this, Observer {
            viewModel.cancelLoading()
            when (it.action) {
                PaymentAction.TOKEN_GENERATION_SUCCESS -> {
                    viewModel.cancelLoading()
                    i = it.tokenResponse.Data.iData
                    j = it.tokenResponse.Data.token

                    webView.loadUrl("$webUrl?i=$i&j=$j")
                }

                PaymentAction.API_ERROR -> {
                    showError(it.error, 101)
                }
                PaymentAction.TOKEN_GENERATION_ERROR -> {
                    warningDialog = Dialog(this)
                    val inflater = this.layoutInflater
                    val view = inflater.inflate(R.layout.layout_popup, null)
                    val errorMessage = view.findViewById<TextView>(R.id.txt_msg)
                    val ok = view.findViewById<TextView>(R.id.tv_email)
                    errorMessage.text = it.error
                    ok.text = "OK"
                    ok.setTextColor(resources.getColor(R.color.colorPrimary))
                    ok.textSize = 16f
                    ok.setOnClickListener {
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                    }
                    warningDialog.setContentView(view)
                    //warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    warningDialog.getWindow()?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                    warningDialog.setCanceledOnTouchOutside(false)
                    warningDialog.setCancelable(true)
                    warningDialog.show()
                }
                PaymentAction.AXIS_PAYMENT_SUCCESS -> {
                    webView.visibility = View.GONE
                    viewModel.cancelLoading()
                    var response = it.paymentResponse

                    if (response.Data.STC.equals("000")) {
                        showSuccess(response.Data.TRN)
                    } else if (response.Data.STC.equals("101")) {
                        showError("Payment Pending!", 101)
                    } else if (response.Data.STC.equals("111")) {
                        showError("Payment Failure!", 111)
                    }
                }
            }
        })

//        if (isNetworkOnline()) {
//            initLoading(this)
//            layoutNoInternet.visibility = View.GONE
//            webView.visibility = View.VISIBLE
//        } else {
//            layoutNoInternet.visibility = View.VISIBLE
//            webView.visibility = View.GONE
//            //mySwipeRefreshLayout?.visibility= View.GONE
//        }

        //webView.loadUrl("")

        webView.webViewClient = WebViewClient()

        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true


        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true;


        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {


                Log.d(TAG, "url: $url ")
//                if (url.startsWith("http://uat-etendering.axisbank.co.in/easypay2.0/frontend/api/payment"))
//                {
//                    viewModel.initLoading(this@AxisPaymentActivity)
//                    return true
//                }
//               else
                if (url.startsWith("http://riviresapaymentbridge.digicob.in/pgmanager/easypay/v1/recresponse")) {
                    viewModel.initLoading(this@AxisPaymentActivity)
                    var token = Uri.parse(url).getQueryParameter("i")
                    Log.d(TAG, "axis_token: $token ")
                    if (token != null) {
                        viewModel.payment(token)
                    }
                    return true
                }

                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                viewModel.cancelLoading()
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                mySwipeRefreshLayout?.isRefreshing = false
                viewModel.cancelLoading()
            }

            override fun onReceivedError(
                    view: WebView?,
                    errorCod: Int,
                    description: String,
                    failingUrl: String?
            ) {
                viewModel.cancelLoading()
                showError("Your Internet Connection May not be active Or $description", 101)

            }
        })
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (event.action == KeyEvent.ACTION_DOWN) {
//            when (keyCode) {
//                KeyEvent.KEYCODE_BACK -> {
//                        super.onKeyDown(keyCode, event)
//                    return true
//                }
//            }
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
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

    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    fun showError(error: String?, i: Int) {
        warningDialog = Dialog(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.layout_payment_error, null)
        warningDialog.getWindow()?.setLayout(this.resources.displayMetrics.widthPixels, WindowManager.LayoutParams.WRAP_CONTENT)
        warningDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
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
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            warningDialog.dismiss()
        }
        warningDialog.setContentView(view)

        warningDialog.getWindow()?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        warningDialog.setCanceledOnTouchOutside(false)
        warningDialog.setCancelable(true)
        warningDialog.show()
    }

    fun showSuccess(tran_id: String?) {
        successDialog = Dialog(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.layout_payment_success, null)
        val tranId = view.findViewById<TextView>(R.id.tv_tran_id)
        val ok = view.findViewById<Button>(R.id.btn_ok)
        tranId.text = tran_id

        ok.setOnClickListener {
            //                finish();
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            //Intent intent1=new Intent(ConfirmationActivity.this, RenewLoanActivity.class);
            val intent1 = Intent(this@AxisPaymentActivity, RenewLoanActivity::class.java)

            intent1.putExtra("net_amount", net_amount)
            intent1.putExtra("flag", flag)
            intent1.putExtra("from", "pay_online")
            startActivity(intent1)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish();
            successDialog.dismiss()
        }
        successDialog.setContentView(view)
        //warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.getWindow()?.setLayout(this.resources.displayMetrics.widthPixels, WindowManager.LayoutParams.WRAP_CONTENT)
        successDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        successDialog.setCanceledOnTouchOutside(false)
        successDialog.setCancelable(true)
        successDialog.show()
    }

}