package com.example.siddhartha.noproxy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
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

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)

        sp = getSharedPreferences("login", Context.MODE_PRIVATE)

        ParseJSONSubjects()

    }

    private fun ParseJSONSubjects() {
        val id = sp.getString("id", "")
        val type = sp.getString("type", "")

        val url1 = "http://<website-link>/json/?allSub=" + type
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

                    adapter = SubjectsAdapter(this, listSubjects) { clicked ->
                        if(type == "1") {
                            val gotoOptions = Intent(this, SelectOptionActivity::class.java)
                            gotoOptions.putExtra("id", id)
                            gotoOptions.putExtra("type", type)
                            gotoOptions.putExtra("scode", clicked.scode)
                            gotoOptions.putExtra("slot", clicked.slot)
                            startActivity(gotoOptions)
                        } else if(type == "2") {
                            val gotoMarkAttendance = Intent(this, MarkAttendanceActivity::class.java)
                            gotoMarkAttendance.putExtra("id", id)
                            gotoMarkAttendance.putExtra("type", type)
                            gotoMarkAttendance.putExtra("scode", clicked.scode)
                            gotoMarkAttendance.putExtra("slot", clicked.slot)
                            startActivity(gotoMarkAttendance)
                        }
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

    fun addBtnClicked(view: View) {

        if(sp.getString("type", "") == "1") {

            val gotoFacultyAddSubject = Intent(this, AddSubjectActivity::class.java)
            startActivity(gotoFacultyAddSubject)

        } else if(sp.getString("type", "") == "2") {

            val gotoStudentAddSubject = Intent(this, StudentAddSubjectActivity::class.java)
            startActivity(gotoStudentAddSubject)

        }

    }

    fun logoutBtnClicked(view: View) {

        sp.edit().putBoolean("logged", false).apply()
        sp.edit().putString("id", "").apply()
        sp.edit().putString("type", "").apply()
        val gotoMain = Intent(this, MainActivity::class.java)
        startActivity(gotoMain)

    }
}
