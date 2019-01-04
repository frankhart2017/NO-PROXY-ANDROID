package com.example.siddhartha.noproxy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("login", Context.MODE_PRIVATE)

        if(sp.getBoolean("logged", false)) {
            val gotoSubjectList = Intent(this, SubjectListActivity::class.java)
            startActivity(gotoSubjectList)
        }

        loginBtn.setOnClickListener {

            if(logEmail1Text.text.toString() == "" || logPassword1Text.text.toString() == "") {
                Toast.makeText(this,"fill the form", Toast.LENGTH_SHORT).show()
            } else if (!isEmailValid(logEmail1Text.text.toString())) {
                Toast.makeText(this, "email galat hai", Toast.LENGTH_SHORT).show()
            } else {

                var id: Int = radioGroup.checkedRadioButtonId
                val radio:RadioButton = findViewById(id)
                if (radio.text.toString() == "Faculty") {
                    val url1 = "http://<website-link>/json/?log=2&login=1"
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
                                val gotoShowSubjects = Intent(this, SubjectListActivity::class.java)
                                sp.edit().putBoolean("logged", true).apply()
                                sp.edit().putString("id", response.getString("tid")).apply()
                                sp.edit().putString("type", "1").apply()
                                startActivity(gotoShowSubjects)
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
                    val url1 = "http://<website-link>json/?log=2&login=2"
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
                                val gotoShowSubjects = Intent(this, SubjectListActivity::class.java)
                                sp.edit().putBoolean("login", true).apply()
                                sp.edit().putString("id", response.getString("reg")).apply()
                                sp.edit().putString("type", "2").apply()
                                startActivity(gotoShowSubjects)
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

    override fun onRestart() {
        super.onRestart()

        if(sp.getBoolean("logged", false)) {
            val gotoSubjectList = Intent(this, SubjectListActivity::class.java)
            startActivity(gotoSubjectList)
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
