package com.example.siddhartha.noproxy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_add_subject.*
import kotlinx.android.synthetic.main.activity_student_add_subject.*
import org.json.JSONException

class StudentAddSubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_add_subject)

        val slot = arrayOf("--Select Slot--", "A", "B", "C", "D", "E", "F", "G")
        val arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, slot)
        slotSpinnerStudent.adapter = arrayAdapter

    }

    fun addStudentSubject(view: View) {

        if(registerNumber.text.toString() != "" && subjectCodeStudent.text.toString() != "" &&
                slotSpinnerStudent.selectedItem.toString() != "--Select Slot--" && facultyIdStudent.text.toString() != "") {

            var errorMsg = ""

            if(facultyIdStudent.text.toString().length != 6) {
                errorMsg += "Faculty ID is invalid\r\n"
            }

            if(subjectCodeStudent.text.toString().length != 7 && subjectCodeStudent.text.toString().length != 8) {
                errorMsg += "Subject code is invalid\r\n"
            }

            if(registerNumber.text.toString().length != 15) {
                errorMsg += "Register number is invalid\r\n"
            }

            if(errorMsg != "") {
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            } else {

                val url1 = "http://<website-link>/json/?fac=2"
                val url2 = "&reg=" + registerNumber.text + "&sid=" + subjectCodeStudent.text
                val url3 = "&tid=" + facultyIdStudent.text + "&slot=" + slotSpinnerStudent.selectedItem
                val url = url1 + url2 + url3
                val loginRequest = object: JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                    try {
                        val status = response.getString("status")

                        when (status) {
                            "0" -> Toast.makeText(this, "Oops there was some error, please come back later!",
                                    Toast.LENGTH_SHORT).show()
                            "1" -> {
                                Toast.makeText(this, "Subject added successfully!", Toast.LENGTH_SHORT).show()
                                val gotoSubjectList = Intent(this, SubjectListActivity::class.java)
                                gotoSubjectList.putExtra("id", "RA1611003010876")
                                gotoSubjectList.putExtra("type", "2")
                                startActivity(gotoSubjectList)
                            }
                            "2" -> Toast.makeText(this, "The data exists already!", Toast.LENGTH_SHORT).show()
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
}
