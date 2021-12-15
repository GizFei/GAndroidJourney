package coder.giz.android.uidemo

import coder.giz.android.uidemo.databinding.ActivityAndroidUiDemoMainBinding
import coder.giz.android.uidemo.helper.DataGenerator
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.navigation.ActivityNavAdapter

class AndroidUiDemoMainActivity : DataBindingBaseActivity<ActivityAndroidUiDemoMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_android_ui_demo_main

    override fun initView() {
        mBinding.rvMainNav.adapter = ActivityNavAdapter(this).apply {
            updateItems(DataGenerator.AndroidUiDemoNavItems)
        }
    }

}