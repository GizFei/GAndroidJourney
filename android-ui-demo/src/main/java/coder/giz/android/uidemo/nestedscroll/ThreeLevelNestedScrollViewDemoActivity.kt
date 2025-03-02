package coder.giz.android.uidemo.nestedscroll

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityNestedScrollViewDemoBinding
import coder.giz.android.uidemo.recyclerview.GridCardAdapter
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.util.YFLog
import coder.giz.android.yfutility.util.dp2px
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import kotlin.random.Random

/**
 * 三级嵌套滑动。
 *
 * Created by GizFei on 2022/8/28
 */
class ThreeLevelNestedScrollViewDemoActivity : DataBindingBaseActivity<ActivityNestedScrollViewDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_nested_scroll_view_demo

    override fun initView() {
        mBinding.recyclerView.run {
            adapter = GridCardAdapter()
            addItemDecoration(ItemDecoration())
        }
        mBinding.recyclerViewHorizontal.run {
            adapter = HorizontalGridCardAdapter()
            addItemDecoration(ItemDecoration())
        }
        mBinding.btnCollapse.setOnClickListener {
            mBinding.headerLayout.collapse()
        }
        mBinding.btnExpand.setOnClickListener {
            mBinding.headerLayout.expand()
        }
        mBinding.btnTextLen.setOnClickListener {
            mBinding.scanningLayout.changeTextLength()
        }
        mBinding.btnRvSize.setOnClickListener {
            toggleRecyclerViewSize()
        }
        mBinding.btnCountView.setOnClickListener {
            toggleCountView()
        }
        mBinding.scanningLayout.post {
            // 更新滑动偏移量
            mBinding.headerLayout.updateHeaderScrollOffset(mBinding.scanningLayout.calcScrollOffsetY())
        }

        val appBarLayout: AppBarLayout = mBinding.appBarLayout
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            // 超过一半距离，则显示数字。
            YFLog.d("ThreeLevelNestedScrollViewDemoActivity", "offset: $verticalOffset")

            val showCount = abs(verticalOffset) > appBarLayout.totalScrollRange / 2
            mBinding.scanningLayout.updateCountViewVisibility(showCount)
        })
    }

    private fun toggleRecyclerViewSize() {
        val size = if (Random.nextBoolean()) 4 else 0
        mBinding.recyclerViewHorizontal.adapter = HorizontalGridCardAdapter(size)
        if (size == 0) {
            mBinding.headerLayout.setHeaderScrollEnabled(true, true)
        } else {
            mBinding.headerLayout.setHeaderScrollEnabled(false, false)
        }
    }

    private var mIsCountViewShown = false
    private fun toggleCountView() {
        mIsCountViewShown = !mIsCountViewShown
        mBinding.scanningLayout.updateCountViewVisibility(mIsCountViewShown)
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