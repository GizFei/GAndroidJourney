package coder.giz.android.journey

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coder.giz.android.journey.databinding.ActivityMainBinding
import coder.giz.android.journey.helper.DataGenerator
import coder.giz.android.journey.main.MainNavItemAdapter
import coder.giz.android.yfui.base.DataBindingBaseActivity

class MainActivity : DataBindingBaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        with(mBinding.rvMainNav) {
            // layoutManager = GridLayoutManager(this@MainActivity, 2)
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = MainNavItemAdapter(this@MainActivity).apply {
                updateData(DataGenerator.MainNavItemDataList)
                setItemClickListener { data, _ ->
                    data.activity.navigate()
                }
            }
        }
    }
}