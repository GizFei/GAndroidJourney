package coder.giz.android.uidemo.floatingwindow.debug

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.databinding.ItemDebugSettingCardBinding

/**
 * Created by GizFei on 2022/2/26
 */
class DebugSettingCardAdapter(private val mContext: Context) :
    RecyclerView.Adapter<DebugSettingCardAdapter.DebugSettingCardVH>() {

    private val mData = mutableListOf<DebugSettingItem>()

    private var mCallback: DebugSettingCallback? = null

    fun updateData(newData: List<DebugSettingItem>) {
        mData.clear()
        mData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setCallback(callback: DebugSettingCallback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebugSettingCardVH {
        return DebugSettingCardVH(
            ItemDebugSettingCardBinding.inflate(LayoutInflater.from(mContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DebugSettingCardVH, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class DebugSettingCardVH(
        private val mBinding: ItemDebugSettingCardBinding
    ) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: DebugSettingItem) {
            mBinding.run {
                tvTitle.text = item.title
                settingEnabled = item.enabled
                executePendingBindings()
            }
            mBinding.switchSettingEnabled.setOnCheckedChangeListener { _, isChecked ->
                mCallback?.onSettingChanged(item.itemType, isChecked)
            }
        }

    }

    interface DebugSettingCallback {
        fun onSettingChanged(itemType: DebugInfoItemManager.ItemType, enabled: Boolean)
    }
}