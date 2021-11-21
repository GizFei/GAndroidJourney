package coder.giz.android.yfui.recyclerview.adapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by GizFei on 2021/11/2
 */
class SimpleListItemOneAdapter : RecyclerView.Adapter<SimpleListItemOneAdapter.ItemOneHolder>() {

    private val mData = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemOneHolder {
        return ItemOneHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: ItemOneHolder, position: Int) {
        holder.tvText1.text = mData[position]
    }

    override fun getItemCount(): Int = mData.size

    fun updateData(data: List<String>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    class ItemOneHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvText1: TextView = itemView.findViewById(android.R.id.text1)
    }

}