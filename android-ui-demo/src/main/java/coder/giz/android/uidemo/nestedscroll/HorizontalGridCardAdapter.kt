package coder.giz.android.uidemo.nestedscroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ItemDebugInfoCardBinding
import coder.giz.android.uidemo.helper.DataGenerator
import coder.giz.android.yfutility.util.dp2px

/**
 * Created by GizFei on 2022/7/19
 */
class HorizontalGridCardAdapter : RecyclerView.Adapter<HorizontalGridCardAdapter.GridCardViewHolder>() {

//    var mItemTouchCallback: GridCardItemCallback? = null

    private val mData = buildList {
        addAll(DataGenerator.CompanyProductMap.toList())
        addAll(DataGenerator.CompanyProductMap.toList())
        addAll(DataGenerator.CompanyProductMap.toList())
        addAll(DataGenerator.CompanyProductMap.toList())
        addAll(DataGenerator.CompanyProductMap.toList())
    }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridCardViewHolder {
        val binding: ItemDebugInfoCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_debug_info_card, parent, false)
        return GridCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridCardViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    fun swapItem(srcPos: Int, targetPos: Int) {
        mData[targetPos] = mData[srcPos].also { mData[srcPos] = mData[targetPos] }
        notifyItemMoved(srcPos, targetPos)
    }

    inner class GridCardViewHolder(private val mBinding: ItemDebugInfoCardBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.cvDebugInfoCard.updateLayoutParams<ViewGroup.LayoutParams> {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }

        fun bind(data: Pair<String, String>) {
            mBinding.run {
                cvDebugInfoCard.cardElevation = mBinding.root.context.dp2px(2)
                tvTitle.text = data.first
                tvInfo.text = data.second
            }
            itemView.setOnLongClickListener {
//                mItemTouchCallback?.isLongPressToDragEnabled = false
                return@setOnLongClickListener true
            }
        }

        fun onSelected() {
            itemView.let {
                it.scaleX = 1.2f
                it.scaleY = 1.2f
            }
        }

        fun onClear() {
            itemView.let {
                it.scaleX = 1f
                it.scaleY = 1f
            }
        }

    }

}