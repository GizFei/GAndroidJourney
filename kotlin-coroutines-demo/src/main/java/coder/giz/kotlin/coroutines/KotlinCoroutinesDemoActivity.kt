package coder.giz.kotlin.coroutines

import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.navigation.ActivityNavAdapter
import coder.giz.kotlin.coroutines.databinding.ActivityKotlinCoroutinesDemoBinding
import coder.giz.kotlin.coroutines.helper.DataGenerator

class KotlinCoroutinesDemoActivity : DataBindingBaseActivity<ActivityKotlinCoroutinesDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_kotlin_coroutines_demo

    override fun initView() {
        mBinding.rvMainNav.adapter = ActivityNavAdapter(this).apply {
            updateItems(DataGenerator.KotlinCoroutinesDemoNavItems)
        }
    }
}