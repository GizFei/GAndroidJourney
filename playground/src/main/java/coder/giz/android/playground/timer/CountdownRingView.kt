package coder.giz.android.playground.timer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import coder.giz.android.playground.R
import coder.giz.android.yfutility.util.dp2px
import kotlin.math.max
import kotlin.math.min

/**
 * 倒计时圆环视图。
 *
 * ✅1、实现倒计时功能
 * 2、自定义样式：
 *    · 圆环背景色
 *    · 圆环色
 *    · 圆环宽度
 * 3、*倒计时时，流动的渐变淡白色光弧效果
 *
 * Created by GizFei on 2022/3/22
 */
class CountdownRingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // region 自定义样式属性
    @ColorInt
    private var mRingBgColor: Int = DEFAULT_RING_BG_COLOR
    @ColorInt
    private var mRingColor: Int = DEFAULT_RING_COLOR
    private var mRingWidth: Float = context.dp2px(DEFAULT_RING_WIDTH_DP)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CountdownRingView).run {
            mRingBgColor = getColor(R.styleable.CountdownRingView_crvRingBgColor, DEFAULT_RING_BG_COLOR)
            mRingColor = getColor(R.styleable.CountdownRingView_crvRingColor, DEFAULT_RING_COLOR)
            mRingWidth = getDimension(R.styleable.CountdownRingView_crvRingWidth, mRingWidth)
            recycle()
        }
    }
    // endregion

    private val mTotalLength: Float = context.dp2px(240f)
    private var mRemainLength: Float = mTotalLength

    private val mBgPaint = Paint().apply {
        isAntiAlias = true
        color = mRingBgColor
        strokeWidth = mRingWidth
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private val mRingPaint = Paint().apply {
        isAntiAlias = true
        color = mRingColor
        strokeWidth = mRingWidth
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private val mArcRect = RectF()

    private var mCountDownTimer: CountDownTimer? = null

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        val radius = min(width, height) / 2f - mRingWidth / 2f
        // 绘制背景
        canvas.drawCircle(cx, cy, radius, mBgPaint)
        // 绘制进度
        val sweepAngle = normLength(mRemainLength) / mTotalLength * 360f
        mArcRect.set(cx - radius, cy - radius, cx + radius, cy + radius)
        canvas.drawArc(mArcRect, -90f, sweepAngle, false, mRingPaint)
    }

    private fun normLength(length: Float): Float {
        return max(0f, min(length, mTotalLength))
    }

    fun setRingBgColor(@ColorInt bgColor: Int) {
        mBgPaint.color = bgColor
        invalidate()
    }

    fun setRingColor(@ColorInt color: Int) {
        mRingPaint.color = color
        invalidate()
    }

    fun setRingWidth(width: Float) {
        mRingWidth = width
        mBgPaint.strokeWidth = width
        mRingPaint.strokeWidth = width
        invalidate()
    }

    fun setRemainLength(remainLen: Float) {
        mRemainLength = normLength(remainLen)
        invalidate()
    }

    fun setRemainPercent(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        val normPercent = max(0f, min(percent, 1f))
        setRemainLength(mTotalLength * normPercent)
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
            // 结束时置为0
            setRemainPercent(0f)
        }
    }

    companion object {
        // 默认倒计时间隔：100ms
        private const val DEFAULT_COUNTDOWN_INTERVAL = 100L

        private const val DEFAULT_RING_BG_COLOR: Int = 0xFFEEEEEE.toInt()
        private const val DEFAULT_RING_COLOR: Int = Color.CYAN
        private const val DEFAULT_RING_WIDTH_DP: Int = 8
    }

}