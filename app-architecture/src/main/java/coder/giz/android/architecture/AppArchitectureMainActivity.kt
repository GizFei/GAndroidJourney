package coder.giz.android.architecture

import coder.giz.android.architecture.databinding.ActivityAppArchitectureMainBinding
import coder.giz.android.architecture.helper.DataGenerator
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.navigation.ActivityNavAdapter

class AppArchitectureMainActivity : DataBindingBaseActivity<ActivityAppArchitectureMainBinding>() {

    private lateinit var mNavAdapter: ActivityNavAdapter

    override fun getLayoutId(): Int = R.layout.activity_app_architecture_main

    override fun initView() {
        mNavAdapter = ActivityNavAdapter(this).apply {
            updateItems(DataGenerator.AppArchitectureNavItems)
        }
        mBinding.rvNavigation.adapter = mNavAdapter
    }

}