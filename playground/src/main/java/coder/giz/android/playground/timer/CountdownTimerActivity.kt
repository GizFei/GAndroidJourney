package coder.giz.android.playground.timer

import android.os.CountDownTimer
import androidx.annotation.FloatRange
import coder.giz.android.playground.R
import coder.giz.android.playground.databinding.ActivityCountdownTimerBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.util.YFLog

/**
 * 倒计时练习页面。
 *
 * Created by GizFei on 2022/3/22
 */
class CountdownTimerActivity : DataBindingBaseActivity<ActivityCountdownTimerBinding>() {

    private val mCountDownTimer = object : CountDownTimer(10000, 100) {
        override fun onTick(millisUntilFinished: Long) {
            mBinding.btnStartTimer.text = "剩余时长：${millisUntilFinished} ms"
            val percent = millisUntilFinished / 10000f
            mBinding.countdownLineView.setRemainPercent(percent)
            mBinding.countdownRingView.setRemainPercent(percent)
        }

        override fun onFinish() {
            mBinding.btnStartTimer.text = "开始倒计时"
            mBinding.countdownLineView.setRemainPercent(0f)
            mBinding.countdownRingView.setRemainPercent(0f)
            mBinding.btnStartTimer.isEnabled = true
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_countdown_timer

    override fun initView() {
        mBinding.run {
            btnStartTimer.setOnClickListener { startCountdownTimer() }
            btnLineTimerBegin.setOnClickListener { startCountdownTimer2() }
            btnLineTimerFrom20.setOnClickListener { startCountdownTimer2From(0.2f) }
            btnLineTimerFrom50.setOnClickListener { startCountdownTimer2From(0.5f) }
            btnLineTimerFrom80.setOnClickListener { startCountdownTimer2From(0.8f) }
            btnLineTimerStop.setOnClickListener { mBinding.countdownRingView2.stopCountDown() }
            btnCurTimestamp.setOnClickListener { YFLog.w(TAG, "System.currentTimeMillis(): ${System.currentTimeMillis()}") }
        }
    }

    private fun startCountdownTimer() {
        mBinding.btnStartTimer.isEnabled = false
        mCountDownTimer.start()
    }

    private fun startCountdownTimer2() {
        mBinding.countdownRingView2.startCountDown(10000L)
    }

    private fun startCountdownTimer2From(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        val totalMillis = 10000L
        val remainMillis = (totalMillis * (1 - percent)).toLong()
        mBinding.countdownRingView2.startCountDown(remainMillis, totalMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }

    companion object {
        private const val TAG = "CountdownTimerAct"
    }
}