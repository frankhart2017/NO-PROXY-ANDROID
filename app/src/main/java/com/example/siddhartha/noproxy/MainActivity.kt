package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun forgotBtnClicked(view: View) {
        val gotoForgot = Intent(this, ForgotActivity::class.java)
        startActivity(gotoForgot)
    }

    fun logSignupBtnClicked(view: View) {
        val gotoSignup = Intent(this, SignupActivity::class.java)
        startActivity(gotoSignup)
    }
}
