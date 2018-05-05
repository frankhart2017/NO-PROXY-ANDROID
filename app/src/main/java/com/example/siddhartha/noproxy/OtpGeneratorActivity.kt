package com.example.siddhartha.noproxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_otp_generator.*

class OtpGeneratorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_generator)

        otpBtn.setOnClickListener {
            Toast.makeText(this,"otp generated",Toast.LENGTH_SHORT).show()
        }
    }
}
