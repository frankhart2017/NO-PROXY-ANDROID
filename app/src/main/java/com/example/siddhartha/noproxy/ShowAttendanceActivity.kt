package com.example.siddhartha.noproxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_show_attendance.*
import org.json.JSONException

class ShowAttendanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_attendance)

        ParseJSONRegNums()

    }

    fun ParseJSONRegNums() {

        val scode = intent.getStringExtra("scode")
        val tid = intent.getStringExtra("id")

        subjectCodeGet.text = scode

        val url1 = "http://interconnect-com.stackstaging.com/json/?getAttendance=1"
        val url2 = "&scode=$scode&tid=$tid"
        val url = url1 + url2
        val loginRequest = object: JsonObjectRequest(Method.GET, url, null, Listener { response ->
            try {
                val regs = response.getJSONArray("regs")

                val listPresent = arrayListOf<StudentPresent>()
                var adapter: StudentPresentAdapter

                var i = 0
                while(i < regs.length()) {
                    listPresent.add(StudentPresent(regs[i].toString()))
                    i++
                }

                adapter = StudentPresentAdapter(this, listPresent)
                presentStudents.adapter = adapter

                val layoutManager = LinearLayoutManager(this)
                presentStudents.layoutManager = layoutManager
                presentStudents.setHasFixedSize(true)

            } catch (e: JSONException) {
                Toast.makeText(this,e.localizedMessage, Toast.LENGTH_SHORT).show()
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
