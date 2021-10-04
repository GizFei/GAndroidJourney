package coder.giz.android.journey.bottomsheet

import coder.giz.android.bottomsheet.util.BottomSheetOptions
import coder.giz.android.journey.R
import coder.giz.android.journey.bottomsheet.practice.FullFeaturedBottomSheetPreviewFragment
import coder.giz.android.journey.databinding.ActivityBottomSheetFragmentDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity

/**
 * 作为 Fragment 的 BottomSheet 的演示页面
 */
class BottomSheetFragmentDemoActivity : DataBindingBaseActivity<ActivityBottomSheetFragmentDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_bottom_sheet_fragment_demo

    override fun initView() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, getFullFeaturedPreviewFragment())
            .commitAllowingStateLoss()
    }

    private fun getFullFeaturedPreviewFragment() = FullFeaturedBottomSheetPreviewFragment.newInstance(
        BottomSheetOptions()
            .fullscreen()
            .topRoundCorner()
    )

}