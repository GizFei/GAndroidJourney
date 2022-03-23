package coder.giz.android.playground

import coder.giz.android.playground.databinding.ActivityPlaygroundMainBinding
import coder.giz.android.playground.helper.DataGenerator
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.navigation.ActivityNavAdapter

class PlaygroundMainActivity : DataBindingBaseActivity<ActivityPlaygroundMainBinding>() {

    private val mAdapter by lazy { ActivityNavAdapter(this) }

    override fun getLayoutId(): Int = R.layout.activity_playground_main

    override fun initView() {
        mBinding.rvNav.adapter = mAdapter
        mAdapter.updateItems(DataGenerator.AndroidUiDemoNavItems)
    }

}