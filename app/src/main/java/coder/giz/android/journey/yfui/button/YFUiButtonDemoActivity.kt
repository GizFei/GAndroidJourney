package coder.giz.android.journey.yfui.button

import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import coder.giz.android.journey.R
import coder.giz.android.journey.databinding.ActivityYfuiButtonDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.components.toast
import com.google.android.material.tabs.TabLayoutMediator

class YFUiButtonDemoActivity : DataBindingBaseActivity<ActivityYfuiButtonDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_yfui_button_demo

    override fun initView() {
        supportActionBar?.title = "YFUi Button Demo"

        mBinding.viewPagerButtonDemo.adapter = ButtonDemoPagerAdapter()
        val tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = when (position) {
                0 -> "Solid"
                1 -> "Solid Light"
                2 -> "Outlined"
                else -> null
            }
        }
        TabLayoutMediator(mBinding.tabLayoutButtonDemo, mBinding.viewPagerButtonDemo, tabConfigurationStrategy)
            .attach()
    }

    private inner class ButtonDemoPagerAdapter : FragmentStateAdapter(this) {
        private val mFragments = listOf(
            YFUiButtonSolidDemoFragment(),
            YFUiButtonSolidLightDemoFragment(),
            YFUiButtonOutlinedDemoFragment(),
        )

        override fun getItemCount(): Int = mFragments.size

        override fun createFragment(position: Int): Fragment = mFragments[position]
    }

}