package coder.giz.android.uidemo.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityDraggableRecyclerViewBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.util.dp2px

/**
 * Created by GizFei on 2022/7/19
 */
class DraggableRecyclerViewActivity : DataBindingBaseActivity<ActivityDraggableRecyclerViewBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_draggable_recycler_view

    override fun initView() {
        mBinding.recyclerView.run {
            val gridCardAdapter = GridCardAdapter()
            val itemTouchHelper = GridCardItemTouchHelper(GridCardItemCallback(gridCardAdapter))
            adapter = gridCardAdapter
            addItemDecoration(ItemDecoration())
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private class ItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val margin = view.context.dp2px(8).toInt()
            outRect.set(margin, margin, margin, margin)
        }
    }
}