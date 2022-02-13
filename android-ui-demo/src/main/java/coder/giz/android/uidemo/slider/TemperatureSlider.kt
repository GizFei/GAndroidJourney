package coder.giz.android.uidemo.slider

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.annotation.FloatRange
import androidx.core.animation.doOnEnd
import coder.giz.android.uidemo.slider.elasticscale.ElasticScaleEffect
import coder.giz.android.uidemo.slider.elasticscale.ElasticScaleEffectHelper
import coder.giz.android.uidemo.slider.elasticscale.VerticalElasticScaleEffectHelper
import coder.giz.android.yfutility.util.YFLog
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * 自定义温度调整滑块
 * 因为TRV的特殊性，用0表示TRV Off的特殊情况。
 *
 * @author Guo Yongfei created on 2021/3/31
 */
class TemperatureSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ElasticScaleEffectHelper.Factors {

    private val mMaxWidth = 150.dp
    private val mMinHeight = 200.dp
    private val mMaxHeight = 500.dp
    private val mCornerRadius = 36.dp

    private val mBgGradientDrawable by lazy {
        GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(0x14000000, 0x1E000000)).apply {
            setBounds(0, 0, width, height)
        }
    }

    private val mRoundRectF = RectF()
    private val mRoundRectPath = Path()

    private val mSliderPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    /**
     * 真实的取值范围
     */
    private var mMinValue: Int = 0
    private var mMaxValue: Int = 0

    /**
     * OffFlag = true:
     * - [1, mMaxProgress] => [min, max]
     * - 0表示Off状态
     *
     * OffFlag = false:
     * - [0, mMaxProgress] => [min, max]
     *
     * - [mProgress] 取值范围：[0, mMaxProgress]
     *
     * 如最小值、最大值分别为5、30。
     * OffFlag = true:
     * 则 progress: [0, 26]。其中[1, 26]映射到[5, 30]。0为特殊情况
     *
     * OffFlag = false:
     * 则 progress: [0, 25]。其中[0, 25]映射到[5, 30]。
     */
    private val mMinProgress: Int = 0       // 最小值，恒为0
    private var mMaxProgress: Int = 0
    private var mProgress: Int = 0
    private val mOffFlag = true    // 表示是否使用OFF（特殊值progress = 0）的状态。true表示使用

    private var mSliderChangeListener: OnSliderChangeListener? = null
    private var mLastTouchY = 0f
    @FloatRange(from = 0.0, to = 0.0)
    private var mSlidePercent = 0f  // 滑动百分比
    private var mSliderAnimator: ValueAnimator? = null
    private var mIsSliding = false  // 是否正在拖动

    private var mRawPercentWhenSliding = mSlidePercent

    private val mElasticScaleEffect = object : ElasticScaleEffect() {
        override fun getParams(direction: Direction): EffectParams {
            return when (direction) {
                Direction.UPWARDS -> object : EffectParams.Upwards() {
                    override val targetScaleX: Float = 0.95f
                    override val targetTranslation: Float = -64f
                }
                else -> super.getParams(direction)
            }
        }
    }
    private val mElasticScaleEffectHelper: ElasticScaleEffectHelper =
        VerticalElasticScaleEffectHelper(this, this, mElasticScaleEffect)

    init {
        setWillNotDraw(false)
        updateProgressRange(5, 30)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 限制最大宽高
        val w = min(measuredWidth, mMaxWidth.toInt())
        val h = max(mMinHeight.toInt(), min(measuredHeight, mMaxHeight.toInt()))
        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mRoundRectF.set(0f, 0f, width.toFloat(), height.toFloat())
        mRoundRectPath.reset()
        mRoundRectPath.addRoundRect(mRoundRectF, mCornerRadius, mCornerRadius, Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.clipPath(mRoundRectPath)
        mBgGradientDrawable.draw(canvas)
        canvas.drawRect(0f, calcSliderTop(), width.toFloat(), height.toFloat(), mSliderPaint)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        alpha = if (enabled) 1f else 0.6f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false    // 禁用时不能滑动
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mIsSliding = true
                mLastTouchY = event.y
                parent.requestDisallowInterceptTouchEvent(true)

                mRawPercentWhenSliding = mSlidePercent
            }
            MotionEvent.ACTION_MOVE -> {
                mIsSliding = true
                val deltaY = mLastTouchY - event.y
                updateProgressWhenSliding(deltaY / height)
                mLastTouchY = event.y
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                mIsSliding = false
                mLastTouchY = 0f
                optimizeProgressOnStop()
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }

        mElasticScaleEffectHelper.handleMotionEvent(event)

        return true
    }

    private fun calcSliderTop():Float = (1f - mSlidePercent) * height

    private fun updateProgressWhenSliding(deltaPercent: Float) {
        mRawPercentWhenSliding += deltaPercent

        mSlidePercent = min(max(0f, mRawPercentWhenSliding), 1f)
        mProgress = (mSlidePercent * (mMaxProgress - mMinProgress)).roundToInt()

        invalidate()
        callback()
    }

    override val maxEffectFactor: Float get() = 0.8f
    override fun getCurrentRawFactor(): Float = mRawPercentWhenSliding

    /**
     * 结束拖动时，调整边界情况的进度百分比
     */
    private fun optimizeProgressOnStop() {
        mSlidePercent = when (mProgress) {
            mMinProgress -> 0f
            mMaxProgress -> 1f
            else -> mSlidePercent
        }
        invalidate()
        callback(true)
    }

    /**
     * 回调监听器。
     *
     * @param onStop 是否停止拖动
     */
    private fun callback(onStop: Boolean = false) {
        mSliderChangeListener?.onProgressChange(mProgress, convertProgressToValue())
        if (onStop) {
            mSliderChangeListener?.onStopSlidingTouch(mProgress, convertProgressToValue())
        }
    }

    /**
     * 将进度值转换成对应的真实值
     */
    private fun convertProgressToValue() = if (mOffFlag) {
        mProgress + mMinValue - 1
    } else {
        mProgress + mMinValue
    }

    /**
     * 将真实值转换成对应的进度值。这里不校验 [value] 是否在范围内
     */
    private fun convertValueToProgress(value: Int) = if (mOffFlag) {
        value - mMinValue + 1
    } else {
        value - mMinValue
    }

    /**
     * 将进度值转换成滑动百分比
     */
    private fun calcSlidePercent(): Float {
        mSlidePercent = (mProgress - mMinProgress).toFloat() / (mMaxProgress - mMinProgress)
        return mSlidePercent
    }

    /**
     * 当前进度值：[0, mMaxProgress]
     */
    fun getProgress(): Int = mProgress

    fun getMaxProgress(): Int = mMaxProgress

    fun getMinProgress(): Int = mMinProgress

    fun getMaxValue(): Int = mMaxValue

    fun getMinValue(): Int = mMinValue

    /**
     * 更新进度范围。
     *
     * @param minVal 最小真实值
     * @param maxVal 最大真实值
     */
    fun updateProgressRange(minVal: Int, maxVal: Int) {
        if (minVal > maxVal) { return }

        mMinValue = minVal
        mMaxValue = maxVal
        mMaxProgress = if (mOffFlag) {
            maxVal - minVal + 1   // 算上特殊值0
        } else {
            maxVal - minVal
        }
    }

    fun setOnSliderChangeListener(listener: OnSliderChangeListener?) {
        mSliderChangeListener = listener
    }

    /**
     * 设置当前的 [value] 值。不触发回调。
     */
    fun setProgressFromValue(value: Int, anim: Boolean = false) {
        if (mIsSliding || value !in mMinValue..mMaxValue) {
            return
        }

        mProgress = convertValueToProgress(value)
        if (anim) {
            val startP = mSlidePercent
            val endP = calcSlidePercent()
            ValueAnimator.ofFloat(startP, endP).run {
                addUpdateListener {
                    mSlidePercent = it.animatedValue as Float
                    invalidate()
                }
                duration = 200
                start()
            }
        } else {
            calcSlidePercent()
            invalidate()
        }
    }

    /**
     * 变成OFF状态
     *
     * @param anim 是否播放动画
     */
    fun toOffState(anim: Boolean = true) {
        mSliderAnimator?.cancel()
        if (anim) {
            ensureSliderAnimator().run {
                setFloatValues(mSlidePercent, 0f)
                start()
            }
        } else {
            mSlidePercent = 0f
            mProgress = mMinProgress
            invalidate()
        }
    }

    private fun ensureSliderAnimator(): ValueAnimator {
        val anim = mSliderAnimator
        if (anim != null) {
            return anim
        } else {
            return ValueAnimator.ofFloat(mSlidePercent, 0f).apply {
                addUpdateListener {
                    mSlidePercent = it.animatedValue as Float
                    invalidate()
                }
                doOnEnd {
                    mSlidePercent = 0f
                    mProgress = mMinProgress
                }
                duration = 200
                mSliderAnimator = this
            }
        }
    }

    interface OnSliderChangeListener {
        /**
         * 进度变化回调。
         *
         * @param progress 进度值。范围：[0, mMaxProgress]
         * @param value 对应的真实值。有效范围：[mMinValue, mMaxValue]。
         *              OffFlag = true: 当progress为0时，返回 mMinValue - 1。
         *              OffFlag = false: 当progress为0时，返回 mMinValue。
         */
        fun onProgressChange(progress: Int, value: Int)

        /**
         * 手指抬起时，停止滑动的回调
         *
         * @param progress 进度值。范围：[0, mMaxProgress]
         * @param value 对应的真实值。有效范围：[mMinValue, mMaxValue]。
         *              OffFlag = true: 当progress为0时，返回 mMinValue - 1。
         *              OffFlag = false: 当progress为0时，返回 mMinValue。
         */
        fun onStopSlidingTouch(progress: Int, value: Int)
    }

    private val Int.dp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), context.resources.displayMetrics)

    private inline fun log(msg: () -> String) {
        YFLog.w("TemperatureSlider", msg())
    }
}