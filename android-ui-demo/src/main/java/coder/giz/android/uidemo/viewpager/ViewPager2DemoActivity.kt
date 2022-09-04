package coder.giz.android.uidemo.viewpager

import android.graphics.Color
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityViewPager2DemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.util.dp2px
import com.google.android.material.tabs.TabLayout

/**
 * Created by GizFei on 2022/9/4
 */
class ViewPager2DemoActivity : DataBindingBaseActivity<ActivityViewPager2DemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_view_pager2_demo

    override fun initView() {
        val dp16 = dp2px(16).toInt()
        mBinding.viewPager.adapter = PagerAdapter(this)
        mBinding.viewPager.run {
            offscreenPageLimit = 2
            (getChildAt(0) as? RecyclerView)?.run {
                clipToPadding = false
                setPadding(dp16, 0, dp16, 0)
            }
        }
        for (i in 0..15) {
            val tab = mBinding.tabLayout.newTab().setCustomView(R.layout.layout_tab_view)
            mBinding.tabLayout.addTab(tab)
        }
//        mBinding.tabLayout.indica
//        val drawable = ContextCompat.getDrawable(this, R.drawable.bg_round_corner_16)
//        drawable?.intrinsicHeight
        mBinding.tabLayout.setSelectedTabIndicatorGravity(TabLayout.INDICATOR_GRAVITY_CENTER)
        mBinding.tabLayout.setSelectedTabIndicator(R.drawable.tab_dot_indicator)
        mBinding.tabLayout.setSelectedTabIndicatorColor(Color.BLUE)
//        mBinding.tabLayout.tabMode = TabLayout.MODE_FIXED
//        mBinding.tabLayout.tabGravity
//        mBinding.tabLayout.setSelectedTabIndicatorHeight(90)
//        mBinding.tabLayout.post {
//            mBinding.tabLayout.forEach {
//                it.updateLayoutParams<ViewGroup.LayoutParams> {
//                    width = dp2px(24).toInt()
//                }
//            }
//        }
    }

    class MarginItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val dp16 = view.context.dp2px(16).toInt()
            val dp48 = view.context.dp2px(48).toInt()
            outRect.set(dp16, dp48, dp16, dp48)
        }
    }

}