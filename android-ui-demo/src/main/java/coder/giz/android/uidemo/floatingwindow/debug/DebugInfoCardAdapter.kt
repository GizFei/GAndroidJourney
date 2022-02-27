package coder.giz.android.uidemo.floatingwindow.debug

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.databinding.ItemDebugInfoCardBinding

/**
 * Created by GizFei on 2022/2/24
 */
class DebugInfoCardAdapter(private val mContext: Context) :
    RecyclerView.Adapter<DebugInfoCardAdapter.DebugInfoCardVH>() {

    private val mData = mutableListOf<DebugInfoItem>()

    fun updateData(newData: List<DebugInfoItem>) {
        mData.clear()
        mData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebugInfoCardVH {
        return DebugInfoCardVH(ItemDebugInfoCardBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: DebugInfoCardVH, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    class DebugInfoCardVH(
        private val mBinding: ItemDebugInfoCardBinding
    ) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: DebugInfoItem) {
            mBinding.tvTitle.text = item.title
            mBinding.flDebugInfoContent.run {
                removeAllViews()
                item.removeContentViewFromParent()
                addView(item.infoContentView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT))
            }
        }

    }
}