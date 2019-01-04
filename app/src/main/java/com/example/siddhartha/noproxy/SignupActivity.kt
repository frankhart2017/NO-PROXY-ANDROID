package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)




        signUpBtn.setOnClickListener {

            var errors = 0
            if (logNameText.text.toString() != "" && logEmail1Text.text.toString() != "" && logIdText.text.toString() != ""
                    && logPassword1Text.text.toString() != "" && logConfPassText.text.toString() != "") {
            if (!isEmailValid(logEmail1Text.text.toString())) {
                Toast.makeText(this, "email", Toast.LENGTH_SHORT).show()
                errors += 1
            }
            if (logIdText.text.toString().length == 7 || logIdText.text.toString().length == 15) {
                errors -= 1
            }else {
                Toast.makeText(this, "registration", Toast.LENGTH_SHORT).show()
                errors += 1
            }
            if (logPassword1Text.text.toString() != logConfPassText.text.toString()) {
                Toast.makeText(this, "confirm", Toast.LENGTH_SHORT).show()
                errors += 1
            } else if (logPassword1Text.text.toString().length < 8) {
                Toast.makeText(this, "length", Toast.LENGTH_SHORT).show()
                errors += 1
            }

            if (errors > 0) {
                Toast.makeText(this, "galat hai", Toast.LENGTH_SHORT).show()
            } else {
                //sign up of faculty
                if (logIdText.text.toString().length == 7) {

                    val url1 = "http://<website-link>/json/?log=1&sign=1"
                    val url2 = "&name=" + devoidOfSpace(logNameText.text.toString()) + "&email=" + logEmail1Text.text.toString()
                    val url3 = "&tid=" + logIdText.text.toString() + "&password=" + logPassword1Text.text.toString()
                    val url = url1 + url2 + url3
                    val loginRequest = object: JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                        try {
                            val jsonStatus = response.getString("signup")

                            if (jsonStatus.toInt() == 0) {
                                Toast.makeText(this, "Email address or id is already linked with another account!",
                                        Toast.LENGTH_SHORT).show()
                            } else if (jsonStatus.toInt() == 1) {
                                Toast.makeText(this, "Signup successfull!\r\nVerify your email address!",
                                        Toast.LENGTH_SHORT).show()
                                val gotoLogin = Intent(this, MainActivity::class.java)
                                gotoLogin.putExtra("email", logEmail1Text.text.toString())
                                gotoLogin.putExtra("password", logPassword1Text.text.toString())
                                startActivity(gotoLogin)
                            }

                        } catch (e: JSONException) {
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
                    //sign up of student
                } else {
                    val url1 = "http://<website-link>/json/?log=1&sign=2"
                    val url2 = "&name=" + devoidOfSpace(logNameText.text.toString()) + "&email=" + logEmail1Text.text.toString()
                    val url3 = "&reg=" + logIdText.text.toString() + "&password=" + logPassword1Text.text.toString()
                    val url = url1 + url2 + url3
                    val loginRequest = object: JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                        try {
                            val jsonStatus = response.getString("signup")

                            if(jsonStatus.toInt() == 0) {
                                Toast.makeText(this, "Email address or id is already linked with another account!",
                                        Toast.LENGTH_SHORT).show()
                            } else if(jsonStatus.toInt() == 1) {
                                Toast.makeText(this, "Signup successfull!",
                                        Toast.LENGTH_SHORT).show()
                                val gotoLogin = Intent(this, MainActivity::class.java)
                                gotoLogin.putExtra("email", logEmail1Text.text.toString())
                                gotoLogin.putExtra("password", logPassword1Text.text.toString())
                                startActivity(gotoLogin)
                            }

                        } catch (e: JSONException) {
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
            } else {
                Toast.makeText(this,"complete the form", Toast.LENGTH_SHORT).show()
            }
        }//sign up button
    }//oncreate



    fun devoidOfSpace(str: String): String {
        return str.replace("\\s".toRegex(), "%20")
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
