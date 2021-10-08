package coder.giz.android.journey.yfui_demo.dialog

import android.graphics.Color
import android.view.View
import coder.giz.android.journey.R
import coder.giz.android.journey.databinding.ActivityBottomPopupDialogFragmentDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfui.dialog.BottomPopupDialogFragment

/**
 * [BottomPopupDialogFragment] 示例页面。
 */
class BottomPopupDialogFragmentDemoActivity : DataBindingBaseActivity<ActivityBottomPopupDialogFragmentDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_bottom_popup_dialog_fragment_demo

    override fun initView() {
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window?.statusBarColor = Color.TRANSPARENT

        mBinding.ivBgAnimal.setOnClickListener { showBottomPopupDialogFragment() }
    }

    private fun showBottomPopupDialogFragment() {
        BottomPopupDialogFragment().apply {
            setContentView(R.layout.activity_bottom_popup_dialog_fragment_demo)
        }.show(supportFragmentManager, null)
    }

}