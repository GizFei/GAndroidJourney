package coder.giz.android.yfutility.navigation.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.yfutility.databinding.ItemNavHeaderBinding
import coder.giz.android.yfutility.navigation.HeaderNavHolder
import coder.giz.android.yfutility.navigation.base.BaseNavItem.Companion.ITEM_TYPE_HEADER

/**
 * Created by GizFei on 2021/10/30
 */
abstract class BaseNavAdapter(protected val mContext: Context)
    : RecyclerView.Adapter<BaseNavHolder<out BaseNavItem, *>>() {

    protected val mLayoutInflater = LayoutInflater.from(mContext)
    protected var mItemClickListener: ((BaseNavItem, Int) -> Unit)? = null
    protected val mNavItems = mutableListOf<BaseNavItem>()

    final override fun getItemViewType(position: Int): Int = mNavItems[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseNavHolder<out BaseNavItem, *> {
        return if (viewType == ITEM_TYPE_HEADER) {
            HeaderNavHolder(ItemNavHeaderBinding.inflate(mLayoutInflater, parent, false))
        } else {
            createNavItemHolder(parent, viewType)
                ?: HeaderNavHolder(ItemNavHeaderBinding.inflate(mLayoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseNavHolder<out BaseNavItem, *>, position: Int) {
        val item = mNavItems[position]

        if (holder is HeaderNavHolder) {
            holder.bind(item)
            holder.itemView.setOnClickListener(null)
        } else {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                mItemClickListener?.invoke(item, position)
            }
        }
    }

    override fun getItemCount(): Int = mNavItems.size

    abstract fun createNavItemHolder(parent: ViewGroup, viewType: Int): BaseNavHolder<out BaseNavItem, *>?

    fun updateItems(items: List<BaseNavItem>) {
        mNavItems.clear()
        mNavItems.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    fun setItemClickListener(listener: (BaseNavItem, Int) -> Unit) {
        mItemClickListener = listener
    }

}