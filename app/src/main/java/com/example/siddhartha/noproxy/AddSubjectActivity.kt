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
import org.json.JSONException

class AddSubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        val batch = arrayOf("--Select Batch--", "1", "2")
        var arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, batch)
        batchSpinner.adapter = arrayAdapter

        val slot = arrayOf("--Select Slot--", "A", "B", "C", "D", "E", "F", "G")
        arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, slot)
        slotSpinner.adapter = arrayAdapter
    }

    fun addFacultySubject(view: View) {

        if(facultyId.text.toString() != "" && subjectCode.text.toString() != "" &&  batchSpinner.selectedItem.toString()
                != "--Select Batch--" && slotSpinner.selectedItem.toString() != "--Select Slot--"
                && dOrder.text.toString() != "") {

            var errorMsg = ""

            if(facultyId.text.toString().length != 6) {
                errorMsg += "Faculty ID is invalid\r\n"
            }
            if(subjectCodeGet.text.toString().length != 7 && subjectCodeGet.text.toString().length != 8) {
                errorMsg += "Subject code is invalid"
            }

            if(errorMsg != "") {
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            } else {

                val url1 = "http://<website-link>/json/?fac=1"
                val url2 = "&tid=" + facultyId.text + "&scode=" + subjectCodeGet.text
                val url3 = "&batch=" + batchSpinner.selectedItem + "&slot=" + slotSpinner.selectedItem + "&do=" + dOrder.text
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
                                gotoSubjectList.putExtra("id", "101357")
                                gotoSubjectList.putExtra("type", "1")
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
