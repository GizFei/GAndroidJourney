package coder.giz.android.uidemo.tablayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.FloatRange
import androidx.core.animation.addListener
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import coder.giz.android.uidemo.R
import coder.giz.android.yfutility.util.YFLog
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * 纵向排列的标签布局
 *
 * Created By GizFei on 2021/02/16
 */
class ColumnTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "ColumnTabLayout"

        private inline fun log(msg: () -> String) = YFLog.e(TAG, msg())
    }

    private var mTabSelectedIndicator: Drawable = GradientDrawable()

    private val mSlidingTabIndicator = SlidingTabIndicator(context)

    init {
        super.addView(mSlidingTabIndicator, 0,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

        val ta = context.obtainStyledAttributes(attrs, R.styleable.ColumnTabLayout)

        setSelectedTabIndicator(ta.getDrawable(R.styleable.ColumnTabLayout_ctl_indicator))

        ta.recycle()
    }

    override fun addView(child: View?) {
        addViewInternal(child)
    }

    override fun addView(child: View?, index: Int) {
        addViewInternal(child)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        addViewInternal(child, params)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        addViewInternal(child, LayoutParams(width, height))
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        addViewInternal(child, params)
    }

    private fun addViewInternal(child: View?, params: ViewGroup.LayoutParams? = null) {
        child ?: return

//        mSlidingTabIndicator.addView(child, LinearLayout.LayoutParams(600, 600))
//        mSlidingTabIndicator.addView(child, LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        val generatedParams = params ?: LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        mSlidingTabIndicator.addView(child, generatedParams)
    }

    fun setSelectedTabIndicator(drawable: Drawable?) {
        mTabSelectedIndicator = drawable ?: GradientDrawable()
    }

    fun selectPosition(position: Int) {
        mSlidingTabIndicator.updateIndicatorAnimation(position)
    }

    private var mIntercepted = false
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mIntercepted = isDownInCurPositionView(ev)
            }
            MotionEvent.ACTION_UP -> {
                mIntercepted = false
            }
        }
        return mIntercepted
    }

    private fun isDownInCurPositionView(ev: MotionEvent): Boolean {
        val curView: View? = mSlidingTabIndicator.getChildAt(mCurPosition)
        return if (curView != null) {
            val bounds = Rect(curView.left, curView.top, curView.right, curView.bottom)
            bounds.contains(ev.x.toInt(), ev.y.toInt())
        } else {
            false
        }
    }

    private var mDownY = 0f
    private var mCurPosition = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val curView: View? = mSlidingTabIndicator.getChildAt(mCurPosition)
                val targetView: View? = mSlidingTabIndicator.getChildAt(mCurPosition + 1)

                val deltaY = event.y - mDownY // <0 向上；>0 向下

                val dist = if (targetView != null && curView != null) (targetView.height / 2f + (curView.bottom - mDownY)) else 0f
                val offset = if (dist != 0f) {
                        if (deltaY > 0) {
                            deltaY / dist
                        } else {
                            1 + (deltaY / dist)
                        }
                } else {
                    0f
                }
                val normOffset = max(0f, min(1f, offset))

                mSlidingTabIndicator.setIndicatorPositionFromTabPosition(mCurPosition, normOffset)

                if (deltaY > 0 && offset >= 1f) {
                    mDownY = event.y
                    mCurPosition += 1
                } else if (deltaY < 0 && offset <= 0f) {
                    mDownY = event.y
                    mCurPosition -= 1
                }

                when {
                    normOffset == 1f && mCurPosition == 0 -> {
                        mDownY = event.y
                        mCurPosition = 1
                    }
//                    normOffset == 1f && mCurPosition == 1 -> {
//                        mDownY = event.y
//                        mCurPosition = 0
//                    }
//                    normOffset == 0f && mCurPosition == 1 -> {
//                        mDownY = event.y
//                        mCurPosition = 0
//                    }
//                    normOffset == 0f && mCurPosition == 0 -> {
//                        mDownY = event.y
//                        mCurPosition = 1
//                    }
                }

//                mLastTouchY = event.y
            }
            MotionEvent.ACTION_UP -> {
                mDownY = 0f
            }
        }
        return true
    }

    private inner class SlidingTabIndicator(context: Context) : LinearLayout(context) {

        private var mSelectedPosition = -1
        private var mIndicatorAnimator: ValueAnimator? = null
        private val mIndicatorInterpolator = TabIndicatorInterpolator()

        private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.YELLOW
        }

        init {
            setWillNotDraw(false)   // 允许绘制
            orientation = VERTICAL
        }

        override fun draw(canvas: Canvas) {
            log { "draw width: ${mTabSelectedIndicator.intrinsicWidth} height: ${mTabSelectedIndicator.intrinsicHeight}" }
//            mTabSelectedIndicator.draw(canvas)

            log { "draw bounds: ${mTabSelectedIndicator.bounds}" }
            val bounds = mTabSelectedIndicator.copyBounds()
            bounds.set(0, bounds.top, mTabSelectedIndicator.intrinsicWidth, bounds.bottom)
            canvas.drawRect(bounds, mPaint)

            super.draw(canvas)
        }

        override fun onDraw(canvas: Canvas) {
            mTabSelectedIndicator.draw(canvas)
        }

        fun setIndicatorPositionFromTabPosition(position: Int, positionOffset: Float) {
            mSelectedPosition = position

            val currentView: View? = getChildAt(mSelectedPosition)
            val nextView: View? = getChildAt(mSelectedPosition + 1)

            tweenIndicatorPosition(currentView, nextView, positionOffset)
        }

        fun updateIndicatorAnimation(toPosition: Int) {
            val currentView: View? = getChildAt(mSelectedPosition)
            val targetView: View? = getChildAt(toPosition)

            val updateListener = ValueAnimator.AnimatorUpdateListener {
                tweenIndicatorPosition(currentView, targetView, it.animatedFraction)
            }

            mIndicatorAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                interpolator = FastOutLinearInInterpolator()
                duration = 800
                addUpdateListener(updateListener)
                addListener(
                    onStart = {
                        mSelectedPosition = toPosition
                    },
                    onEnd = {
                        mSelectedPosition = toPosition
                    }
                )
                start()
            }
        }

        fun tweenIndicatorPosition(startView: View?, endView: View?, fraction: Float) {
            // 计算指示条的范围
            mIndicatorInterpolator.setIndicatorBoundsForOffset(startView, endView, fraction,
                mTabSelectedIndicator)
            // 刷新布局
            postInvalidateOnAnimation()
        }

    }

    class TabView(context: Context) : FrameLayout(context)

    private class TabIndicatorInterpolator {
        fun setIndicatorBoundsForOffset(
            startView: View?,
            endView: View?,
            @FloatRange(from = 0.0, to = 1.0) offset: Float,
            indicator: Drawable,
        ) {
            val startIndicator = calcIndicatorBound(startView)
            val endIndicator = calcIndicatorBound(endView)
            val top = lerp(startIndicator.top, endIndicator.top, offset)
            val bottom = lerp(startIndicator.bottom, endIndicator.bottom, offset)
            indicator.setBounds(
                indicator.bounds.left,
                top,
                indicator.bounds.right,
                bottom
            )
        }

        private fun calcIndicatorBound(view: View?): Rect {
            view ?: return Rect()

            return Rect(view.left, view.top, view.right, view.bottom)
        }

        private fun lerp(start: Int, end: Int, offset: Float): Int {
            return start + ((end - start) * offset).roundToInt()
        }
    }

}