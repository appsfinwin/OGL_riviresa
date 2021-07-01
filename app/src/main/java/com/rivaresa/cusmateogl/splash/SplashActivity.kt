package com.rivaresa.cusmateogl.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.rivaresa.cusmateogl.R
import com.rivaresa.cusmateogl.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.statusBarColor = resources.getColor(R.color.white);
        }

        Handler().postDelayed({
            run {
                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }
        }, 3000)
    }
}