package com.example.siddhartha.noproxy

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class SubjectsAdapter(val context: Context, val subject: List<Subjects>, val itemClick: (Subjects) -> Unit): RecyclerView.Adapter<SubjectsAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindAd(subject[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.subject_list, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return subject.count()
    }

    inner class Holder(itemView: View?, val itemClick: (Subjects) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val slotAndCode = itemView?.findViewById<TextView>(R.id.subDetailsText)

        fun bindAd(subject: Subjects, context: Context) {
            slotAndCode?.text = subject.scode + ":" + subject.slot
            itemView.setOnClickListener { itemClick(subject) }
        }
    }
}