package coder.giz.android.uidemo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ItemDebugInfoCardBinding
import coder.giz.android.uidemo.helper.DataGenerator
import coder.giz.android.uidemo.helper.printRecyclerViewLog
import coder.giz.android.yfutility.util.dp2px

/**
 * Created by GizFei on 2022/7/19
 */
class GridCardAdapter : RecyclerView.Adapter<GridCardAdapter.GridCardViewHolder>() {

    var mItemTouchCallback: GridCardItemCallback? = null

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

        fun bind(data: Pair<String, String>) {
            mBinding.run {
                cvDebugInfoCard.cardElevation = mBinding.root.context.dp2px(2)
                tvTitle.text = data.first
                tvInfo.text = data.second
            }
            itemView.setOnLongClickListener {
                mItemTouchCallback?.isLongPressToDragEnabled = false
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

class GridCardItemCallback(private val mAdapter: GridCardAdapter) : ItemTouchHelper.Callback() {

    var isLongPressToDragEnabled = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or
                ItemTouchHelper.DOWN or
                ItemTouchHelper.RIGHT or
                ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val curPos = viewHolder.adapterPosition
        val targetPos = target.adapterPosition
        mAdapter.swapItem(curPos, targetPos)
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return isLongPressToDragEnabled
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        printRecyclerViewLog { "onMoved ${viewHolder.itemView.top}" }
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        printRecyclerViewLog { "onSelectedChanged actionState: ${actionState}" }
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_DRAG -> {
//                viewHolder?.itemView?.let {
//                    it.scaleX = 1.2f
//                    it.scaleY = 1.2f
//                    (it.findViewById(R.id.cv_debug_info_card) as? CardView)?.cardElevation = it.context.dp2px(8f)
//                }
                (viewHolder as? GridCardAdapter.GridCardViewHolder)?.onSelected()
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        (viewHolder as? GridCardAdapter.GridCardViewHolder)?.onClear()
        isLongPressToDragEnabled = true
//        viewHolder.itemView.let {
//            it.scaleX = 1f
//            it.scaleY = 1f
//            (it.findViewById(R.id.cv_debug_info_card) as? CardView)?.cardElevation = it.context.dp2px(2f)
//        }
    }
}

class GridCardItemTouchHelper(private val callback: ItemTouchHelper.Callback) : ItemTouchHelper(callback) {
}