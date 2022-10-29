package coder.giz.publicapis

import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.navigation.ActivityNavAdapter
import coder.giz.publicapis.databinding.ActivityMainBinding
import coder.giz.publicapis.helper.DataGenerator

class MainActivity : DataBindingBaseActivity<ActivityMainBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        mBinding.rvMainNav.adapter = ActivityNavAdapter(this).apply {
            updateItems(DataGenerator.PublicApisAppNavItems)
        }
    }

}