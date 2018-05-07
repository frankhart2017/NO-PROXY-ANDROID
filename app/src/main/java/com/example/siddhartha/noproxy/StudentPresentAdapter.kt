package com.example.siddhartha.noproxy

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StudentPresentAdapter(val context: Context, val present: List<StudentPresent>): RecyclerView.Adapter<StudentPresentAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindAd(present[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.present_student, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return present.count()
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val reg = itemView?.findViewById<TextView>(R.id.studentReg)

        fun bindAd(present: StudentPresent, context: Context) {
            reg?.text = present.reg
        }
    }
}