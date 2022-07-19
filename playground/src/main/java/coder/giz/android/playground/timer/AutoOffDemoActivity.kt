package coder.giz.android.playground.timer

import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coder.giz.android.playground.R
import coder.giz.android.playground.databinding.ActivityAutoOffDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity

class AutoOffDemoActivity : DataBindingBaseActivity<ActivityAutoOffDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_auto_off_demo

    override fun initView() {
        mBinding.btnStartTimer.setOnClickListener { startCountdownTimer() }
        mBinding.btnStartTimerOut.setOnClickListener { startCountdownTimerTimeOut() }
        mBinding.btnCurTimestamp.setOnClickListener { showCurTimestamp() }
    }

    private fun startCountdownTimer() {
        val endTime = mBinding.etEndTime.text?.toString()?.toIntOrNull()
            ?: ((System.currentTimeMillis() / 1000L).toInt() + 10)
        val delayMin = 1
        startCountdownTimer(endTime, delayMin)
    }

    private fun startCountdownTimerTimeOut() {
        val endTime = (System.currentTimeMillis() / 1000L).toInt() + 800
        val delayMin = 5
        startCountdownTimer(endTime, delayMin)
    }

    /**
     * @param autoOffEndTime Auto Off结束的时间戳。单位：秒。
     * @param delayMin Auto Off配置的定时时长。单位：分钟。
     */
    private fun startCountdownTimer(autoOffEndTime: Int, delayMin: Int) {
        val totalMillis = delayMin * 60 * 1000L
        val remainMillis = autoOffEndTime * 1000L - System.currentTimeMillis()
        val outRange = remainMillis <= 0 || remainMillis > totalMillis
        if (outRange) {
            // 当前时间已超过结束时间或还未开始倒计时，停止倒计时并隐藏视图
            mBinding.countdownRingView.run {
                stopCountDown()
                isInvisible = true
            }
        } else {
            // 否则，显示视图并开始倒计时
            mBinding.countdownRingView.run {
                startCountDown(remainMillis, totalMillis)
                isVisible = true
            }
        }
    }

    private fun showCurTimestamp() {
        mBinding.btnCurTimestamp.text = "${System.currentTimeMillis()}"
    }

}