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
class SimpleListItemTwoAdapter : RecyclerView.Adapter<SimpleListItemTwoAdapter.ItemTwoHolder>() {

    private val mData = mutableListOf<Pair<String, Any>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTwoHolder {
        return ItemTwoHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.simple_list_item_2, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemTwoHolder, position: Int) {
        holder.tvText1.text = mData[position].first
        holder.tvText2.text = mData[position].second.toString()
    }

    override fun getItemCount(): Int = mData.size

    fun updateData(data: List<Pair<String, Any>>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    class ItemTwoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvText1: TextView = itemView.findViewById(android.R.id.text1)
        val tvText2: TextView = itemView.findViewById(android.R.id.text2)
    }

}