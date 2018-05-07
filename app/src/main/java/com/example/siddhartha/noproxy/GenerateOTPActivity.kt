package com.example.siddhartha.noproxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_generate_otp.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class GenerateOTPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_otp)

        otpBtn.setOnClickListener {

            val slot = intent.getStringExtra("slot")
            val sid = intent.getStringExtra("scode")
            val tid = intent.getStringExtra("id")

            val url1 = "http://interconnect-com.stackstaging.com/json/?generate=1"
            val url2 = "&slot=$slot&sid=$sid&tid=$tid"
            val url = url1 + url2
            val loginRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                try {
                    val jsonStatus = response.getString("status")

                    if (jsonStatus.toInt() == 0) {
                        Toast.makeText(this, "Oops there was some error, please come back later!",
                                Toast.LENGTH_SHORT).show()
                    } else if (jsonStatus.toInt() == 1) {
                        val otp = response.getString("otp")
                        otpView.text = otp
                        Toast.makeText(this,"OTP generated",Toast.LENGTH_SHORT).show()
                    }

                } catch (e: JSONException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    Log.d("JSON", "EXC: " + e.localizedMessage)
                }
            }, Response.ErrorListener { error ->
                Log.d("ERROR", "Could not login user: $error")
            }) {

                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            Volley.newRequestQueue(this).add(loginRequest)
        }
        }
    }

