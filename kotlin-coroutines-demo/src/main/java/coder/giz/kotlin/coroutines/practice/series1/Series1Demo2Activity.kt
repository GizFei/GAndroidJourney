package coder.giz.kotlin.coroutines.practice.series1

import coder.giz.kotlin.coroutines.R
import coder.giz.kotlin.coroutines.base.KoroutineDataBindingBaseActivity
import coder.giz.kotlin.coroutines.databinding.ActivitySeries1Demo2Binding
import coder.giz.kotlin.coroutines.widget.LogcatScrollView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * 参考文章：
 * https://juejin.cn/post/6956115368578383902
 *
 * Created by GizFei on 2022/10/23
 */
class Series1Demo2Activity : KoroutineDataBindingBaseActivity<ActivitySeries1Demo2Binding>() {

    override val mLogcatScrollView: LogcatScrollView by lazy {
        mBinding.slLogcat
    }

    override fun getLayoutId(): Int = R.layout.activity_series1_demo2

    override fun initView() {
        mBinding.btnStartGlobalScope.setOnClickListener {
            startGlobalScopeCount()
        }
    }

    private fun startGlobalScopeCount() {
        GlobalScope.launch {
            logcat("launch", "启动协程：".appendThreadInfo())
            for (i in 1..50) {
                Thread.sleep(1000)
                logcat("for loop", "idx: $i")
            }
        }

        MainScope()
    }

}