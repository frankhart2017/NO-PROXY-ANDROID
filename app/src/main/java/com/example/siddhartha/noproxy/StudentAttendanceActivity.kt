package com.example.siddhartha.noproxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_student_attendance.*

class StudentAttendanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_attendance)

        val dOrder = arrayOf("--Select day order--", "1", "2", "3", "4", "5")
        val doArrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dOrder)
        doSpinner.adapter = doArrayAdapter

        val hour = arrayOf("--Select hour--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val hourAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, hour)
        hourSpinner.adapter = hourAdapter

    }
}
