package coder.giz.android.journey

import androidx.recyclerview.widget.GridLayoutManager
import coder.giz.android.journey.databinding.ActivityMainBinding
import coder.giz.android.journey.helper.DataGenerators
import coder.giz.android.journey.main.MainNavItemAdapter
import coder.giz.android.yfui.base.DataBindingBaseActivity

class MainActivity : DataBindingBaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        with(mBinding.rvMainNav) {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = MainNavItemAdapter(this@MainActivity).apply {
                updateData(DataGenerators.MainNavItemDataList)
                setItemClickListener { data, _ ->
                    data.activity.navigate()
                }
            }
        }
    }
}