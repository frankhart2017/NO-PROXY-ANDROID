package com.example.siddhartha.noproxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_subject_list.*

class SubjectListActivity : AppCompatActivity() {

    lateinit var adapter: SubjectsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)

        val id = arrayOf("1", "2", "3", "4", "5", "6", "7")
        val slot = arrayOf("A", "B", "C", "D", "E", "F", "G")
        val scode = arrayOf("15CS201", "15CS202", "15CS203", "15CS204", "15CS205", "15CS206", "15CS207")

        var listSubjects = arrayListOf<Subjects>()

        var i = 0
        while(i < id.size) {
            listSubjects.add(Subjects(id[i], slot[i], scode[i]))
            i++
        }

        adapter = SubjectsAdapter(this, listSubjects) {

        }
        subjectList.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        subjectList.layoutManager = layoutManager
        subjectList.setHasFixedSize(true)
    }
}
