package com.decagon.android.sq007

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class SavedContactsAdapter(var arrayList: ArrayList<ContactModel>, var onSavedContactsListener: OnSavedContactsListener) :
    RecyclerView.Adapter<SavedContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedContactsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)

        return ViewHolder(view, onSavedContactsListener)
    }

    inner class ViewHolder(itemView: View, var onSavedContactsListener: OnSavedContactsListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvNumber: TextView = itemView.findViewById(R.id.tv_number)
        var tvId: TextView = itemView.findViewById(R.id.tv_id)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onSavedContactsListener.onContactClick(adapterPosition)
        }
    }

    override fun onBindViewHolder(holder: SavedContactsAdapter.ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.tvName.text = model.fullName
        holder.tvNumber.text = model.contactNumber
        holder.tvId.text = model.id
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    interface OnSavedContactsListener {
        fun onContactClick(position: Int)
    }

    init {
        notifyDataSetChanged()
    }
}
