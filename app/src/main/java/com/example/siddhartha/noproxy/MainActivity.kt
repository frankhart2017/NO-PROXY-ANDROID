package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn.setOnClickListener {

            if(logEmail1Text.text.toString() == "" || logPassword1Text.text.toString() == "") {
                Toast.makeText(this,"fill the form", Toast.LENGTH_SHORT).show()
            } else if (!isEmailValid(logEmail1Text.text.toString())) {
                Toast.makeText(this, "email galat hai", Toast.LENGTH_SHORT).show()
            } else {
//                radioGroup.setOnCheckedChangeListener(
//                        RadioGroup.OnCheckedChangeListener { group, checkedId ->
//                            val radio: RadioButton = findViewById(checkedId)
//                            Toast.makeText(applicationContext," On checked change : ${radio.text}",
//                                    Toast.LENGTH_SHORT).show()
//                        } )

                var id: Int = radioGroup.checkedRadioButtonId
                val radio:RadioButton = findViewById(id)
                if (radio.text.toString() == "Faculty") {
                    val url1 = "http://interconnect-com.stackstaging.com/json/?log=2&login=1"
                    val url2 = "&email=" + logEmail1Text.text.toString() + "&password=" + logPassword1Text.text.toString()
                    val url = url1 + url2
                    val loginRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                        try {
                            val jsonStatus = response.getString("logStatus")

                            if (jsonStatus.toInt() == 0) {
                                Toast.makeText(this, "Email address or password is incorrect!",
                                        Toast.LENGTH_SHORT).show()
                            } else if (jsonStatus.toInt() == 1) {
                                Toast.makeText(this, "login successfull!",
                                        Toast.LENGTH_SHORT).show()
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
                } else if (radio.text.toString() == "Student") {
                    val url1 = "http://interconnect-com.stackstaging.com/json/?log=2&login=2"
                    val url2 = "&email=" + logEmail1Text.text.toString() + "&password=" + logPassword1Text.text.toString()
                    val url = url1 + url2
                    val loginRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                        try {
                            val jsonStatus = response.getString("logStatus")

                            if (jsonStatus.toInt() == 0) {
                                Toast.makeText(this, "Email address or password is incorrect!",
                                        Toast.LENGTH_SHORT).show()
                            } else if (jsonStatus.toInt() == 1) {
                                Toast.makeText(this, "login successfull!",
                                        Toast.LENGTH_SHORT).show()
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
                } else {
                    Toast.makeText(this,"Please select the type",Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    fun forgotBtnClicked( view: View) {
        val gotoForgot = Intent(this, ForgotActivity::class.java)
        startActivity(gotoForgot)
    }

    fun logSignupBtnClicked(view: View) {
        val gotoSignup = Intent(this, SignupActivity::class.java)
        startActivity(gotoSignup)
    }


    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}
