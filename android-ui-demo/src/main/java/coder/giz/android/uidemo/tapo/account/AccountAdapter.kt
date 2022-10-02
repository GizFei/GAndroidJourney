package coder.giz.android.uidemo.tapo.account

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ItemAccountBinding

/**
 * Created by GizFei on 2022/2/28
 */
class AccountAdapter : RecyclerView.Adapter<AccountAdapter.AccountVH>() {

    private val mData = mutableListOf<AccountItem>()

    private var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountVH {
        return AccountVH(ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AccountVH, position: Int) {
        if (position in mData.indices) {
            holder.bind(mData[position])
        } else {
            // 最后一个是“添加”视图
            holder.setAddNewView()
        }
    }

    override fun getItemCount(): Int = mData.size + 1

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<AccountItem>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    inner class AccountVH(private val mBinding: ItemAccountBinding) : RecyclerView.ViewHolder(mBinding.root) {

        var isAddNew = false

        fun bind(accountItem: AccountItem) {
            isAddNew = false
            mBinding.run {
                tvAccountUsername.text = accountItem.username
                ivAccountAvatar.setImageResource(accountItem.avatarRes)
                ivAccountAvatar.imageTintList = null
                root.setOnClickListener {
                    mItemClickListener?.onAccountItemClick(itemView, accountItem)
                }
            }
        }

        fun setAddNewView() {
            isAddNew = true
            mBinding.run {
                tvAccountUsername.text = "添加新账号"
                ivAccountAvatar.setImageResource(R.drawable.ic_round_add)
                ivAccountAvatar.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(ivAccountAvatar.context, R.color.black)
                )
                root.setOnClickListener {
                    mItemClickListener?.onAddNewClick()
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onAccountItemClick(itemView: View, accountItem: AccountItem)
        fun onAddNewClick()
    }

}