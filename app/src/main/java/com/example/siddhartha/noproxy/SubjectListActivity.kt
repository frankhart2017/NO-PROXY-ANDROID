package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_subject_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import org.json.JSONArray
import org.json.JSONException

class SubjectListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)

        ParseJSONSubjects()

    }

    private fun ParseJSONSubjects() {
        val id = "RA1611003010876"
        val type = "2"

        val url1 = "http://interconnect-com.stackstaging.com/json/?allSub=" + type
        var url2 = "&reg=" + id
        if(type == "1") {
            url2 = "&tid=" + id
        }
        val url = url1+url2
        val loginRequest = object: JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                val status = response.getString("status")

                if(status == "1") {

                    val ids = response.getJSONArray("ids")
                    val slots = response.getJSONArray("slots")
                    var sids = JSONArray()

                    if(type == "1") {
                        sids = response.getJSONArray("scodes")
                    } else if(type == "2") {
                        sids = response.getJSONArray("sids")
                    }

                    val listSubjects = arrayListOf<Subjects>()
                    var adapter: SubjectsAdapter

                    var i = 0
                    while(i < ids.length()) {
                        listSubjects.add(Subjects(ids[i].toString(), slots[i].toString(), sids[i].toString()))
                        i++
                    }

                    adapter = SubjectsAdapter(this, listSubjects) {

                    }
                    subjectList.adapter = adapter

                    val layoutManager = LinearLayoutManager(this)
                    subjectList.layoutManager = layoutManager
                    subjectList.setHasFixedSize(true)

                } else {
                    alert("Nothing to show, add subjects!") {
                        title = "No Proxy"
                        yesButton { }
                    }.show()
                }

            } catch (e: JSONException) {
                Log.d("JSON", "EXC: " + e.localizedMessage)
            }
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not send mail: $error")
        }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        Volley.newRequestQueue(this).add(loginRequest)
    }
}
