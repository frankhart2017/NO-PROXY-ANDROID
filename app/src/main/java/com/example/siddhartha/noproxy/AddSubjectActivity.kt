package com.example.siddhartha.noproxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_subject.*

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

        val dayOrder = arrayOf("--Select Day Order--", "1", "2", "3", "4", "5")
        arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dayOrder)
        dayOrderSpinner.adapter = arrayAdapter

    }

    fun addFacultySubject(view: View) {

        if(facultyId.text.toString() != "" && subjectCode.text.toString() != "" &&  batchSpinner.selectedItem.toString()
                != "--Select Batch--" && slotSpinner.selectedItem.toString() != "--Select Slot--"
                && dayOrderSpinner.selectedItem.toString() != "--Select Day Order--") {

            var errorMsg = ""

            if(facultyId.text.toString().length != 7) {
                errorMsg += "Faculty ID is invalid\r\n"
            }
            if(subjectCode.text.toString().length != 7 && subjectCode.text.toString().length != 8) {
                errorMsg += "Subject code is invalid"
            }

            if(errorMsg != "") {
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "All correct!", Toast.LENGTH_SHORT).show()

            }

        } else {
            Toast.makeText(this, "Complete the form!", Toast.LENGTH_SHORT).show()
        }

    }
}
