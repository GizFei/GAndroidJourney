package coder.giz.android.playground.timer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import coder.giz.android.yfutility.util.dp2px
import kotlin.math.max
import kotlin.math.min

/**
 * Created by GizFei on 2022/3/22
 */
class CountdownLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mTotalLength: Float = context.dp2px(240f)
    private var mRemainLength: Float = mTotalLength

    private val mStrokeWidth = context.dp2px(48f)
    private val mBgPaint = Paint().apply {
        isAntiAlias = true
        color = Color.LTGRAY
        strokeWidth = mStrokeWidth
        strokeCap = Paint.Cap.BUTT
    }
    private val mFgPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        strokeWidth = mStrokeWidth
        strokeCap = Paint.Cap.BUTT
    }

    private var mCountDownTimer: CountDownTimer? = null

    override fun onDraw(canvas: Canvas) {
        val y = mStrokeWidth / 2f
        val x = mStrokeWidth / 2f
        // 绘制背景
        canvas.drawLine(x, y, x + mTotalLength, y, mBgPaint)

        // 绘制进度
        val startX = mTotalLength - normLength(mRemainLength)
        canvas.drawLine(x + startX, y, x + mTotalLength, y, mFgPaint)
    }

    private fun normLength(length: Float): Float {
        return max(0f, min(length, mTotalLength))
    }

    fun setRemainPercent(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        val normPercent = max(0f, min(percent, 1f))
        mRemainLength = normLength(mTotalLength * normPercent)
        invalidate()
    }

    /**
     * 从头开始倒计时。
     *
     * @param totalMillis 总毫秒数。
     */
    fun startCountDown(totalMillis: Long) {
        destroyCountDownTimer()
        mCountDownTimer = createCountDownTimer(totalMillis, totalMillis).apply {
            start()
        }
    }

    /**
     * 从中间某个点开始倒计时。
     *
     * @param remainMillis 剩余毫秒数。
     * @param totalMillis 总毫秒数。
     */
    fun startCountDown(remainMillis: Long, totalMillis: Long) {
        destroyCountDownTimer()
        if (remainMillis <= 0 || totalMillis <= 0) {
            setRemainPercent(0f)
            return
        }
        if (totalMillis <= remainMillis) {
            setRemainPercent(0f)
            return
        }
        mCountDownTimer = createCountDownTimer(remainMillis, totalMillis).apply {
            start()
        }
    }

    fun stopCountDown() {
        destroyCountDownTimer()
    }

    private fun createCountDownTimer(millisInFuture: Long, totalMillis: Long) =
        MyCountdownTimer(millisInFuture, totalMillis)

    private fun destroyCountDownTimer() {
        mCountDownTimer?.let {
            it.cancel()
            mCountDownTimer = null
        }
    }

    private inner class MyCountdownTimer(
        millisInFuture: Long,
        totalMillis: Long,
        countDownInterval: Long = DEFAULT_COUNTDOWN_INTERVAL,
    ) : CountDownTimer(millisInFuture, countDownInterval) {

        private val mTotalMillis = totalMillis.toFloat()

        override fun onTick(millisUntilFinished: Long) {
            val percent = millisUntilFinished / mTotalMillis
            setRemainPercent(percent)
        }

        override fun onFinish() {
            setRemainPercent(0f)
        }
    }

    companion object {
        // 默认倒计时间隔：100ms
        private const val DEFAULT_COUNTDOWN_INTERVAL = 100L
    }

}