package coder.giz.android.network

import coder.giz.android.network.databinding.ActivityNetworkMainBinding
import coder.giz.android.network.helper.DataGenerator
import coder.giz.android.network.helper.RxJavaHook
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.navigation.ActivityNavAdapter

class NetworkMainActivity : DataBindingBaseActivity<ActivityNetworkMainBinding>() {

    init {
        RxJavaHook.init()
    }

    override fun getLayoutId(): Int = R.layout.activity_network_main

    override fun initView() {
        mBinding.rvMainNav.adapter = ActivityNavAdapter(this).apply {
            updateItems(DataGenerator.NetworkNavItems)
        }
    }

}