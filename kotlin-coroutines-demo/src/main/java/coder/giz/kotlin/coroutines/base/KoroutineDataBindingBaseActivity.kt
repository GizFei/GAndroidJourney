package coder.giz.kotlin.coroutines.base

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.kotlin.coroutines.widget.LogcatScrollView
import kotlinx.coroutines.launch

/**
 * Created by GizFei on 2022/10/22
 */
abstract class KoroutineDataBindingBaseActivity<VDB: ViewDataBinding> : DataBindingBaseActivity<VDB>() {

    abstract val mLogcatScrollView: LogcatScrollView?

    protected val threadName: String get() = Thread.currentThread().name

    protected fun String.appendThreadInfo() = "$this | Thread[Name: $threadName]"

    protected fun logThread(tag: String = "") {
        Log.e("Thread", "Thread Name: ${Thread.currentThread().name} - Tag: $tag")
    }

    protected fun logcat(tag: String, msg: String) {
        lifecycleScope.launch {
            // lifecycleScope运行在主线程
            mLogcatScrollView?.logcat(tag, msg)
        }
    }

}