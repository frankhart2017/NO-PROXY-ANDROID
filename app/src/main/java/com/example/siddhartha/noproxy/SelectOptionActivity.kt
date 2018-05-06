package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SelectOptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_option)
    }

    fun takeAttendanceClicked(view: View) {
        val gotoGenerateOTP = Intent(this, GenerateOTPActivity::class.java)
        startActivity(gotoGenerateOTP)
    }

    fun fetchAttendanceClicked(view: View) {
        val gotoGetAttendance = Intent(this, ShowAttendanceActivity::class.java)
        startActivity(gotoGetAttendance)
    }
}
