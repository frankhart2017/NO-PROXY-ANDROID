package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_forgot.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.util.regex.Pattern

class ForgotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
    }

    fun forgotBtnClicked(view: View) {

        var type = "1"
        if(studentForgot.isChecked) {
            type = "2"
        }

        if(forgotEmailText.text.toString() != "" && (facultyForgot.isChecked || studentForgot.isChecked)) {

            if(!isEmailValid(forgotEmailText.text.toString())) {
                Toast.makeText(this, "Invalid email address!", Toast.LENGTH_SHORT).show()
            } else {

                val url1 = "http://interconnect-com.stackstaging.com/json/?forgot=" + type
                val url2 = "&email=" + forgotEmailText.text.toString()
                val url = url1 + url2
                val loginRequest = object: JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                    try {
                        val status = response.getString("status")

                        if(status == "0") {
                            Toast.makeText(this, "Oops there was some error, please come back later!", Toast.LENGTH_SHORT).show()
                        } else if(status == "1") {
                            Toast.makeText(this, "Password reset mail sent successfully!", Toast.LENGTH_SHORT).show()
                            val gotoMain = Intent(this, MainActivity::class.java)
                            startActivity(gotoMain)
                        } else if(status == "2") {
                            Toast.makeText(this, "There is no account linked with this email!", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: JSONException) {
                        Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
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

        } else {
            Toast.makeText(this, "Complete the form!", Toast.LENGTH_SHORT).show()
        }

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
