package coder.giz.android.journey.yfui.button

import androidx.core.view.forEach
import coder.giz.android.journey.R
import coder.giz.android.journey.databinding.FragmentYfuiButtonOutlinedDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseFragment
import coder.giz.android.yfutility.components.toast
import com.google.android.material.button.MaterialButton

/**
 * Created by GizFei on 2021/10/5
 */
class YFUiButtonOutlinedDemoFragment
    : DataBindingBaseFragment<FragmentYfuiButtonOutlinedDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_yfui_button_outlined_demo

    override fun initView() {
        mBinding.llButtons.forEach {
            if (it.isEnabled && it is MaterialButton) {
                val btn = it
                it.setOnClickListener { context?.toast(btn.text.toString()) }
            }
        }
    }
}