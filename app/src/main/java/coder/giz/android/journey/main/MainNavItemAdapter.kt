package coder.giz.android.journey.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.journey.databinding.ItemMainNavItemBinding

/**
 * Created by GizFei on 2021/10/1
 */
class MainNavItemAdapter(context: Context) : RecyclerView.Adapter<MainNavItemAdapter.MainNavItemHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)
    private var mItemClickListener: ((MainNavItemData, Int) -> Unit)? = null
    private val mData = mutableListOf<MainNavItemData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainNavItemHolder {
        return MainNavItemHolder(ItemMainNavItemBinding.inflate(mLayoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MainNavItemHolder, position: Int) {
        val data = mData[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            mItemClickListener?.invoke(data, position)
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateData(data: List<MainNavItemData>) {
        mData.clear()
        mData.addAll(data)
        notifyItemRangeChanged(0, data.size)
    }

    fun setItemClickListener(listener: (MainNavItemData, Int) -> Unit) {
        mItemClickListener = listener
    }

    class MainNavItemHolder(
        private val mBinding: ItemMainNavItemBinding
    ) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: MainNavItemData) {
            mBinding.data = data
        }

    }

}