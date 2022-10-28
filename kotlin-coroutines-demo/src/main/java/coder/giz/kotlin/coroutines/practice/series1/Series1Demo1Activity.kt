package coder.giz.kotlin.coroutines.practice.series1

import coder.giz.kotlin.coroutines.R
import coder.giz.kotlin.coroutines.base.KoroutineDataBindingBaseActivity
import coder.giz.kotlin.coroutines.databinding.ActivitySeries1Demo1Binding
import coder.giz.kotlin.coroutines.widget.LogcatScrollView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 参考文章：
 * https://juejin.cn/post/6953441828100112392
 *
 * Created by GizFei on 2022/10/22
 */
class Series1Demo1Activity : KoroutineDataBindingBaseActivity<ActivitySeries1Demo1Binding>() {

    override val mLogcatScrollView: LogcatScrollView by lazy {
        mBinding.slLogcat
    }

    override fun getLayoutId(): Int = R.layout.activity_series1_demo1

    override fun initView() {
        mBinding.btnClearLogcat.setOnClickListener {
            mBinding.slLogcat.clearLogcat()
        }
        mBinding.btnStart.setOnClickListener {
            startCoroutine()
        }
        mBinding.btnStartWithReturnValue.setOnClickListener {
            startCoroutineWithReturnValue()
        }
        mBinding.btnStartInCoroutine.setOnClickListener {
            startCoroutineInOtherCoroutine()
        }
        mBinding.btnStartInDiffDispatcher.setOnClickListener {
            startCoroutineInDifferentDispatcher()
        }
        mBinding.btnExecuteMultiSuspendFun.setOnClickListener {
            executeMultiSuspendFunInCoroutine()
        }
        mBinding.btnExecuteMultiSuspendFun2.setOnClickListener {
            executeMultiSuspendFunInCoroutine2()
        }
        mBinding.btnExecuteMultiSuspendFunWithReturn.setOnClickListener {
            executeMultiSuspendFunWithReturnValueInCoroutine()
        }
        mBinding.btnExecuteMultiSuspendFunWithReturn2.setOnClickListener {
            executeMultiSuspendFunWithReturnValueInCoroutine2()
        }
    }

    private fun startCoroutine() {
        runBlocking {
            logcat("runBlocking", "启动一个协程 $threadName")
//            sleep(2000)
//            logcat("runBlocking", "sleep(2s)后")
        }
        GlobalScope.launch {
            logcat("launch", "启动一个协程 $threadName")
        }
        GlobalScope.async {
            logcat("async", "启动一个协程 $threadName")
        }

        GlobalScope.launch {
            val tag = "launchInLaunchTest"
            logcat(tag, "CoroutineScope: $this".appendThreadInfo())
            launch {
                // this是新的CoroutineScope实例
                logcat(tag, "launch in launch CoroutineScope: $this".appendThreadInfo())
                launch {
                    // this是新的CoroutineScope实例
                    logcat(tag, "launch in launch in launch CoroutineScope: $this".appendThreadInfo())
                }
            }
        }
    }

    private fun startCoroutineWithReturnValue() {
        val runBlockingJob = runBlocking {
            logcat("runBlocking", "启动一个协程 $threadName")
            41
        }
        logcat("runBlockingJob", "job: $runBlockingJob")

        val launchJob = GlobalScope.launch {
            logcat("launch", "启动一个协程 $threadName")
        }
        logcat("launchJob", "job: $launchJob")

        val asyncJob = GlobalScope.async {
            logcat("async", "启动一个协程 $threadName")
            "async返回值"
        }
        logcat("asyncJob", "job: $asyncJob")
    }

    /**
     * 在协程中启动协程
     */
    private fun startCoroutineInOtherCoroutine() {
        GlobalScope.launch {
            val launchJob = launch {
                logcat("launch", "启动一个协程")
            }
            logcat("launchJob", "$launchJob")

            val asyncJob = async {
                logcat("async", "启动一个协程")
                "我是async的返回值"
            }
            // await会挂起调用协程（GlobalScope）
            // 所以 logcat("asyncJob", "$asyncJob") 必定在 logcat("asyncJob.await()", asyncJob.await()) 之后打印
            logcat("asyncJob.await()", asyncJob.await())
            logcat("asyncJob", "$asyncJob")
        }
    }

    private fun startCoroutineInDifferentDispatcher() {
        GlobalScope.launch {
            logcat("TITLE", "在Main调度器中启动协程 - 同步执行")
            GlobalScope.launch(Dispatchers.Main) {
                for (i in 1..10) {
                    // 同步执行
                    launch {
                        logcat("launch$i", "启动一个协程")
                    }
                }
            }.join()

            logcat("TITLE", "在默认调度器中启动协程 - 并行执行")
            GlobalScope.launch {
                for (i in 1..10) {
                    // 并行执行
                    launch {
                        logcat("launch$i", "启动一个协程")
                    }
                }
            }.join()

            logcat("TITLE", "偶数子协程在Main调度器中启动")
            GlobalScope.launch {
                for (i in 1..10) {
                    if (i % 2 == 0) {
                        launch(Dispatchers.Main) {
                            logcat("launch$i-Main", "在Main调度器中启动一个协程")
                        }
                    } else {
                        launch {
                            logcat("launch$i", "启动一个协程")
                        }
                    }
                }
            }.join()
        }
    }

    private fun executeMultiSuspendFunInCoroutine() {
        logThreadInfo("executeMultiSuspendFunInCoroutine out launch")
        GlobalScope.launch {
            logThreadInfo("executeMultiSuspendFunInCoroutine")
            // 打印0，挂起，1秒后恢复
            suspendLogcat(0)
            // 上一个suspend函数恢复后。开始执行。打印1，挂起，1秒后恢复
            suspendLogcat(1)
            // 以此类推
            suspendLogcat(2)
            suspendLogcat(3)
            suspendLogcat(4)
        }
    }

    private fun executeMultiSuspendFunInCoroutine2() {
        logThreadInfo("executeMultiSuspendFunInCoroutine out launch")
        GlobalScope.launch {
            // 五个launch，开启了5个子协程，同时运行。
            launch {
                logThreadInfo("executeMultiSuspendFunInCoroutine")
                suspendLogcat(0)
                // suspendLogcat挂起，1秒后恢复，继续执行剩余代码
                logcat("after 0")
            }
            launch {
                logThreadInfo("executeMultiSuspendFunInCoroutine")
                suspendLogcat(1)
                logcat("after 1")
            }
            launch {
                logThreadInfo("executeMultiSuspendFunInCoroutine")
                suspendLogcat(2)
                logcat("after 2")
            }
            launch {
                logThreadInfo("executeMultiSuspendFunInCoroutine")
                suspendLogcat(3)
                logcat("after 3")
            }
            launch {
                logThreadInfo("executeMultiSuspendFunInCoroutine")
                suspendLogcat(4)
                logcat("after 4")
            }
        }
    }

    private suspend fun suspendLogcat(idx: Int) {
        logcat("Suspend Fun", "Idx：$idx".appendThreadInfo())
        delay(1000)
        logcat("Suspend Fun", "After delay Idx：$idx".appendThreadInfo())
    }

    /**
     * 日志输出：
     * 2022-10-23 13:16:21.642 W/: Suspend Fun return value 0 | Thread[Name: DefaultDispatcher-worker-1]
     * 2022-10-23 13:16:22.644 W/: Suspend Fun return value 1 | Thread[Name: DefaultDispatcher-worker-1]
     * 2022-10-23 13:16:23.645 W/Suspend Fun Result: res1: Idx: 0, res2: Idx: 1
     */
    private fun executeMultiSuspendFunWithReturnValueInCoroutine() {
        GlobalScope.launch {
            // 执行，挂起，1秒后恢复。返回结果。
            val res1 = suspendReturnValue(0)
            // 上一个suspend方法恢复后，开始执行，挂起，1秒后恢复，返回结果。
            val res2 = suspendReturnValue(1)
            // 2秒后，上面两个结果返回，打印日志
            logcat("Suspend Fun Result", "res1: $res1, res2: $res2")
        }
    }

    /**
     * 2022-10-23 13:27:40.847 W/: Suspend Fun return value 0 | Thread[Name: DefaultDispatcher-worker-2]
     * 2022-10-23 13:27:40.848 W/: Suspend Fun return value 1 | Thread[Name: DefaultDispatcher-worker-3]
     * 2022-10-23 13:27:41.850 W/Suspend Fun Result: res1: Idx: 0
     * 2022-10-23 13:27:41.851 W/Suspend Fun Result: res2: Idx: 1
     * 2022-10-23 13:27:41.852 W/Suspend Fun Result: res1: Idx: 0, res2: Idx: 1
     */
    private fun executeMultiSuspendFunWithReturnValueInCoroutine2() {
        GlobalScope.launch {
            // 执行，挂起，1秒后恢复。返回结果。
            val res1 = async {
                suspendReturnValue(0)
            }
            // 上一个suspend方法恢复后，开始执行，挂起，1秒后恢复，返回结果。
            val res2 = async {
                suspendReturnValue(1)
            }
            // 2秒后，上面两个结果返回，打印日志
            logcat("Suspend Fun Result", "res1: ${res1.await()}")
            logcat("Suspend Fun Result", "res2: ${res2.await()}")
            logcat("Suspend Fun Result", "res1: ${res1.await()}, res2: ${res2.await()}")
        }
    }

    private suspend fun suspendReturnValue(idx: Int): String {
        logcat("Suspend Fun return value $idx".appendThreadInfo())
        delay(1000)
        return "Idx: $idx"
    }

//    private fun logcat(tag: String, msg: String) {
//        Log.w(tag, msg)
//        val log = "LOG/[$tag] $msg"
//        lifecycleScope.launch {
//            // lifecycleScope运行在主线程
//            logThread("lifecycleScope.launch")
//            val allLog = "${mBinding.tvLogcat.text}\n$log"
//            mBinding.tvLogcat.text = allLog
//            mBinding.tvLogcat.post {
//                mBinding.slLogcat.fullScroll(View.FOCUS_DOWN)
//            }
//        }
//    }

}