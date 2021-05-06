package com.decagon.android.sq007

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class PhoneContactsAdapter(var arrayList: ArrayList<ContactModel>) :
    RecyclerView.Adapter<PhoneContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.tvName.text = model.fullName
        holder.tvNumber.text = model.contactNumber
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvNumber: TextView = itemView.findViewById(R.id.tv_number)
    }

    init {
        notifyDataSetChanged()
    }
}
