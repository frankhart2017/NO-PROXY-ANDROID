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
        val id = intent.getStringExtra("id")
        val type = intent.getStringExtra("type")
        val scode = intent.getStringExtra("scode")
        val slot = intent.getStringExtra("slot")

        val gotoGenerateOTP = Intent(this, GenerateOTPActivity::class.java)
        gotoGenerateOTP.putExtra("id", id)
        gotoGenerateOTP.putExtra("type", type)
        gotoGenerateOTP.putExtra("scode", scode)
        gotoGenerateOTP.putExtra("slot", slot)
        startActivity(gotoGenerateOTP)
    }

    fun fetchAttendanceClicked(view: View) {
        val id = intent.getStringExtra("id")
        val type = intent.getStringExtra("type")
        val scode = intent.getStringExtra("scode")
        val slot = intent.getStringExtra("slot")

        val gotoGetAttendance = Intent(this, ShowAttendanceActivity::class.java)
        gotoGetAttendance.putExtra("id", id)
        gotoGetAttendance.putExtra("type", type)
        gotoGetAttendance.putExtra("scode", scode)
        gotoGetAttendance.putExtra("slot", slot)
        startActivity(gotoGetAttendance)
    }
}
