package coder.giz.android.playground.timer

import android.content.Context
import android.graphics.BlurMaskFilter
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
import coder.giz.android.yfutility.util.YFLog
import coder.giz.android.yfutility.util.dp2px
import kotlin.math.max
import kotlin.math.min

/**
 * 倒计时圆环视图。
 *
 * ✅1、实现倒计时功能
 * ✅2、自定义样式：
 *    · 圆环背景色
 *    · 圆环色
 *    · 圆环宽度
 *    · 光弧颜色
 * ✅3、*倒计时时，流动的渐变淡白色光弧效果
 * ✅4、与图片结合，圈住图片
 * ✅5、根据时间戳计算，并倒计时。如果已经结束或超时，则隐藏。
 * ✅6、动态调整光弧绕行速度
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
    private var mLightArcColor: Int = DEFAULT_LIGHT_ARC_COLOR

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CountdownRingView).run {
            mRingBgColor = getColor(R.styleable.CountdownRingView_crvRingBgColor, DEFAULT_RING_BG_COLOR)
            mRingColor = getColor(R.styleable.CountdownRingView_crvRingColor, DEFAULT_RING_COLOR)
            mRingWidth = getDimension(R.styleable.CountdownRingView_crvRingWidth, mRingWidth)
            mLightArcColor = getColor(R.styleable.CountdownRingView_crvLightArcColor, DEFAULT_LIGHT_ARC_COLOR)
            recycle()
        }
    }
    // endregion

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
    private val mLightArcPaint = Paint().apply {
        isAntiAlias = true
        color = mLightArcColor
        strokeWidth = mRingWidth
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
        maskFilter = BlurMaskFilter(48f, BlurMaskFilter.Blur.INNER)
    }
    private val mArcRect = RectF()
    @FloatRange(from = 0.0, to = 1.0)
    private var mRemainPercent: Float = 1f
    @FloatRange(from = 0.0, to = 1.0)
    private var mLightArcPercent: Float = 1f

    private var mCountDownTimer: MyCountDownTimer? = null
    private var mLightArcCountDownTimer: MyLightArcCountDownTimer? = null

    /**
     * 光弧绕行一圈所需的毫秒数。毫秒数越小，表示绕行一圈的速度越快。
     */
    private var mLightArcTimeMillisPerLap: Int = DEFAULT_LIGHT_ARC_MILLIS_PER_LAP

    override fun onDraw(canvas: Canvas) {
        YFLog.e("CountDown", "mPercent: $mRemainPercent mArcPercent: $mLightArcPercent")
        val cx = width / 2f
        val cy = height / 2f
        val radius = min(width, height) / 2f - mRingWidth / 2f
        // 绘制背景
        canvas.drawCircle(cx, cy, radius, mBgPaint)
        // 绘制进度
        val sweepAngle = mRemainPercent * 360f
        mArcRect.set(cx - radius, cy - radius, cx + radius, cy + radius)
        canvas.drawArc(mArcRect, -90f, sweepAngle, false, mRingPaint)

        if (mCountDownTimer?.isFinished == false) {
            // 还在倒计时中，则绘制光弧
            val lightArcAngle = mLightArcPercent * 360f
            if (lightArcAngle in 0f..(sweepAngle + 30)) {
                val lightArcStartAngle = -90f + min(lightArcAngle, sweepAngle)
                val lightArcSweepAngle = if (lightArcAngle <= sweepAngle) {
                    -min(lightArcAngle, 30f)
                } else {
                    val delta = lightArcAngle - sweepAngle
                    -min(sweepAngle, 30 - delta)
                }
                canvas.drawArc(mArcRect, lightArcStartAngle, lightArcSweepAngle ,
                    false, mLightArcPaint)
            }
        }
    }

    fun setRingBgColor(@ColorInt bgColor: Int) {
        mRingBgColor = bgColor
        mBgPaint.color = bgColor
        invalidate()
    }

    fun setRingColor(@ColorInt color: Int) {
        mRingColor = color
        mRingPaint.color = color
        invalidate()
    }

    fun setRingWidth(width: Float) {
        mRingWidth = width
        mBgPaint.strokeWidth = width
        mRingPaint.strokeWidth = width
        mLightArcPaint.strokeWidth = width
        invalidate()
    }

    fun setLightArcColor(@ColorInt color: Int) {
        mLightArcColor = color
        mLightArcPaint.color = color
        invalidate()
    }

    fun setRemainPercent(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        mRemainPercent = max(0f, min(percent, 1f))
        invalidate()
    }

    /**
     * 从头开始倒计时。
     *
     * @param totalMillis 总毫秒数。
     */
    fun startCountDown(totalMillis: Long) {
        destroyAllCountDownTimers()
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
        destroyAllCountDownTimers()
        if (remainMillis <= 0 || totalMillis <= 0) {
            setRemainPercent(0f)
            return
        }
        if (totalMillis <= remainMillis) {
            setRemainPercent(1f)
            return
        }
        // 先走到起始位置
        val startRemainPercent = remainMillis / totalMillis.toFloat()
        setRemainPercent(startRemainPercent)
        // 再开始倒计时
        mCountDownTimer = createCountDownTimer(remainMillis, totalMillis).apply {
            start()
        }
        mLightArcCountDownTimer = MyLightArcCountDownTimer().apply {
            start()
        }
    }

    fun stopCountDown() {
        destroyAllCountDownTimers()
        invalidate()
    }

    private fun createCountDownTimer(millisInFuture: Long, totalMillis: Long) =
        MyCountDownTimer(millisInFuture, totalMillis)

    private fun destroyCountDownTimer() {
        mCountDownTimer?.let {
            it.cancel()
            mCountDownTimer = null
        }
    }

    private fun setLightArcPercent(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        mLightArcPercent = max(0f, min(percent, 1f))
        invalidate()
    }

    private fun startLightArcCountDownTimer() {
        destroyLightArcCountDownTimer()
        mLightArcCountDownTimer = MyLightArcCountDownTimer().apply {
            start()
        }
    }

    private fun destroyLightArcCountDownTimer() {
        mLightArcCountDownTimer?.let {
            it.cancel()
            mLightArcCountDownTimer = null
        }
        setLightArcPercent(0f)
    }

    private fun destroyAllCountDownTimers() {
        destroyCountDownTimer()
        destroyLightArcCountDownTimer()
    }

    fun reset() {
        destroyAllCountDownTimers()
        mRemainPercent = 1f
        mLightArcPercent = 1f
        invalidate()
    }

    private fun dynamicAdjustLightArcTimeMillisPerLap() {
        mLightArcTimeMillisPerLap = when (mRemainPercent) {
            in 0f..0.1f -> 1000
            in 0.1f..0.25f -> 2000
            in 0.25f..0.5f -> 3000
            in 0.5f..0.75f -> 4000
            in 0.75f..1f -> 5000
            else -> DEFAULT_LIGHT_ARC_MILLIS_PER_LAP
        }
    }

    private inner class MyCountDownTimer(
        millisInFuture: Long,
        totalMillis: Long,
        countDownInterval: Long = DEFAULT_COUNTDOWN_INTERVAL,
    ) : CountDownTimer(millisInFuture, countDownInterval) {

        private val mTotalMillis = totalMillis.toFloat()
        var isFinished: Boolean = true
            private set

        override fun onTick(millisUntilFinished: Long) {
            isFinished = false
            val percent = millisUntilFinished / mTotalMillis
            setRemainPercent(percent)
            dynamicAdjustLightArcTimeMillisPerLap()
        }

        override fun onFinish() {
            // 结束时置为0
            setRemainPercent(0f)
            isFinished = true
            // 同时结束光弧的定时器
            destroyLightArcCountDownTimer()
        }
    }

    private inner class MyLightArcCountDownTimer :
        CountDownTimer(mLightArcTimeMillisPerLap.toLong(), 50) {

        private val mTotalMillis: Float = mLightArcTimeMillisPerLap.toFloat()

        override fun onTick(millisUntilFinished: Long) {
            val arcPercent = (millisUntilFinished / mTotalMillis) * 1f
            setLightArcPercent(arcPercent)
        }

        override fun onFinish() {
            setLightArcPercent(0f)
            // 立即开始下一个光弧定时器
            if (mCountDownTimer?.isFinished == false) {
                startLightArcCountDownTimer()
            }
        }
    }

    companion object {
        // 默认倒计时间隔：100ms
        private const val DEFAULT_COUNTDOWN_INTERVAL = 100L

        private const val DEFAULT_RING_BG_COLOR: Int = 0xFFEEEEEE.toInt()
        private const val DEFAULT_RING_COLOR: Int = Color.CYAN
        private const val DEFAULT_RING_WIDTH_DP: Int = 8
        private const val DEFAULT_LIGHT_ARC_COLOR: Int = 0x3AFFFFFF
        private const val DEFAULT_LIGHT_ARC_MILLIS_PER_LAP: Int = 2000
    }

}