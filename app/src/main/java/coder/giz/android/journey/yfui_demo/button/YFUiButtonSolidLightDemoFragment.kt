package coder.giz.android.journey.yfui_demo.button

import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.forEach
import coder.giz.android.journey.R
import coder.giz.android.journey.databinding.FragmentYfuiButtonSolidLightDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseFragment
import coder.giz.android.yfutility.components.toast

/**
 * Created by GizFei on 2021/10/4
 */
class YFUiButtonSolidLightDemoFragment
    : DataBindingBaseFragment<FragmentYfuiButtonSolidLightDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_yfui_button_solid_light_demo

    override fun initView() {
        mBinding.llButtons.forEach { btn ->
            if (btn.isEnabled && btn is AppCompatButton) {
                btn.setOnClickListener { context?.toast(btn.text.toString()) }
            }
        }
    }
}