package coder.giz.coretopics

import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.navigation.ActivityNavAdapter
import coder.giz.coretopics.databinding.ActivityCoreTopicsMainBinding
import coder.giz.coretopics.helper.DataGenerator

class CoreTopicsMainActivity : DataBindingBaseActivity<ActivityCoreTopicsMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_core_topics_main

    override fun initView() {
        mBinding.rvMainNav.adapter = ActivityNavAdapter(this).apply {
            updateItems(DataGenerator.CoreTopicsNavItems)
        }
    }

}