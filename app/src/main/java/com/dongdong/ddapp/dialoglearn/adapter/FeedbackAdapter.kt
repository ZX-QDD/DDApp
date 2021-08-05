package com.dongdong.ddapp.dialoglearn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.dongdong.ddapp.R

class FeedbackAdapter(
    val context: Context,
    val callback: FeedbackItemClickCallback
) : RecyclerView.Adapter<FeedbackViewHolder>() {

    val data = mutableListOf<String>()

    fun setData(list: List<String>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_item_feedback, parent, false)
        return FeedbackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val item = data[position]
        holder.checkedTextView.text = item
        holder.checkedTextView.setOnClickListener {
            val checkedTextView = it as CheckedTextView
            val checked = checkedTextView.isChecked
            checkedTextView.isChecked = !checked
            callback.callback(item, !checked)
        }
    }
}

class FeedbackViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val checkedTextView: CheckedTextView = itemView.findViewById(R.id.ctOption)
}

interface FeedbackItemClickCallback {
    fun callback(option: String, checked: Boolean)
}