package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_generate_otp.*
import kotlinx.android.synthetic.main.activity_mark_attendance.*
import org.json.JSONException

class MarkAttendanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_attendance)

        val dayOrder = arrayOf("--Select Day Order--", "1", "2", "3", "4", "5")
        var arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dayOrder)
        dayOrderText.adapter = arrayAdapter

        val hour = arrayOf("--Select Hour--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, hour)
        hourText.adapter = arrayAdapter

        submitOtpBtn.setOnClickListener {

            val reg = intent.getStringExtra("id")
            val scode = intent.getStringExtra("scode")

            if(dayOrderText.selectedItem.toString() != "--Select Day Order--" && hourText.selectedItem.toString() !=
                    "--Select Hour--" && otpText.text.toString() != "") {

                val url1 = "http://interconnect-com.stackstaging.com/json/?attendance=1"
                val url2 = "&reg=$reg&scode=$scode"
                val url3 = "&do=" +dayOrderText.selectedItem.toString() + "&hour=" + hourText.selectedItem.toString()
                val url4 = "&otp=" +otpText.text.toString()
                val url = url1 + url2 +url3 + url4
                val loginRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                    try {
                        val jsonStatus = response.getString("status")

                        when {
                            jsonStatus.toInt() == 0 -> Toast.makeText(this, "Oops there was some error," +
                                    " please come back later!",Toast.LENGTH_SHORT).show()
                            jsonStatus.toInt() == 1 -> {
                                Toast.makeText(this,"Attendance marked successfully!", Toast.LENGTH_SHORT).show()
                                val gotoSubjectList = Intent(this, SubjectListActivity::class.java)
                                startActivity(gotoSubjectList)
                            }
                            jsonStatus.toInt() == 2 -> Toast.makeText(this,"OTP expired!",Toast.LENGTH_SHORT).show()
                            jsonStatus.toInt() == 3 -> Toast.makeText(this, "Incorrect OTP!", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "Complete the form!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
