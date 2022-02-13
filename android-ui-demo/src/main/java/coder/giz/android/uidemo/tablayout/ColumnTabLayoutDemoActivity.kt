package coder.giz.android.uidemo.tablayout

import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityVerticalTabLayoutDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity

class ColumnTabLayoutDemoActivity : DataBindingBaseActivity<ActivityVerticalTabLayoutDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_vertical_tab_layout_demo

    override fun initView() {
        mBinding.ivImage1.setOnClickListener {
            mBinding.columnTabLayout.selectPosition(0)
        }
        mBinding.ivImage2.setOnClickListener {
            mBinding.columnTabLayout.selectPosition(1)
        }
    }

}