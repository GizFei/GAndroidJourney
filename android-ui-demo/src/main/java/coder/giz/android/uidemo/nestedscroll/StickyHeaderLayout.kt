package coder.giz.android.uidemo.nestedscroll

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import coder.giz.android.yfutility.data.toJson
import coder.giz.android.yfutility.util.YFLog
import kotlin.math.max
import kotlin.math.min

/**
 * 三级嵌套滚动的顶级布局。
 * 负责处理ScanningLayout的折叠和展开。
 *
 * Created by GizFei on 2022/8/28
 */
class StickyHeaderLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent2 {

    private val mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private var mHeaderView: ScanningLayout? = null
    private var mHeaderScrollOffset = 0

    /**
     * 纵向滑动距离值。范围：0 ~ [mHeaderScrollOffset]
     */
    private var mScrollOffsetY = 0
    private var mOffsetAnimator: ValueAnimator? = null
    private var mIsHeaderExpanded = true

    private var mIsHeaderScrollEnabled = true

    init {
        orientation = VERTICAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            mHeaderView = getChildAt(0) as? ScanningLayout
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mHeaderScrollOffset > 0) {
            log { "onMeasure $mHeaderScrollOffset measure again." }
            val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                measuredHeight + mHeaderScrollOffset,
                MeasureSpec.getMode(heightMeasureSpec)
            )
            super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
        }
    }

//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//        mHeaderView?.let { mHeaderScrollOffset = it.measuredHeight / 2 }
//    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        log { "onStopNestedScroll" }
        mNestedScrollingParentHelper.onStopNestedScroll(target, type)
        moveToFinalState()
    }

    private fun moveToFinalState() {
//        val toExpand = if (mIsHeaderExpanded) {
//            mScrollOffsetY < mHeaderScrollOffset * 0.2f
//        } else {
//            mScrollOffsetY < mHeaderScrollOffset * 0.8f
//        }
        val toExpand = mScrollOffsetY < mHeaderScrollOffset / 2
        log { "moveToFinalState: curOffset $mScrollOffsetY maxOffset $mHeaderScrollOffset toExpand: $toExpand" }
        moveToState(toExpand)
    }

    private fun moveToState(toExpand: Boolean) {
        if (toExpand) {
            mIsHeaderExpanded = true
            animateToOffset(0)
        } else {
            mIsHeaderExpanded = false
            animateToOffset(mHeaderScrollOffset)
        }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        log { "onNestedScroll:  dyConsumed=$dyConsumed  dyUnconsumed=$dyUnconsumed  type=$type" }
        if (dyUnconsumed < 0) {
            // 向下滑动
            if (mScrollOffsetY + dyUnconsumed > 0) {
                scrollOffsetYBy(dyUnconsumed)
            } else {
                val consumedY = -mScrollOffsetY
                scrollOffsetYBy(consumedY)
            }
        }
    }

    /**
     * dy > 0：向上滑动
     * dy < 0：向下滑动
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        log { "onNestedPreScroll: dx=$dx  dy=$dy  consumed=${consumed.toJson()}  type=$type. scrollY: $scrollY" }

        fun handle() {
            if (dy > 0) {
                // 向上滑动
                if (mScrollOffsetY + dy <= mHeaderScrollOffset) {
                    scrollOffsetYBy(dy)
                    consumed[1] = dy
                } else {
                    val consumedY = mHeaderScrollOffset - mScrollOffsetY
                    scrollOffsetYBy(consumedY)
                    consumed[1] = consumedY
                }
            } else {
                // 向下滑动
                if (mScrollOffsetY + dy > 0) {
                    scrollOffsetYBy(dy)
                    consumed[1] = dy
                } else {
                    val consumedY = -mScrollOffsetY
                    scrollOffsetYBy(consumedY)
                    consumed[1] = consumedY
                }

            }
        }

        if (mIsHeaderScrollEnabled) {
            handle()
        } else {
            consumed[1] = 0
        }
    }

    private fun scrollOffsetYBy(dy: Int) {
        if (!mIsHeaderScrollEnabled) {
            return
        }

        scrollBy(0, dy)
        mScrollOffsetY += dy
        mHeaderView?.updateOffset(mScrollOffsetY)
    }

    private fun scrollOffsetYTo(offset: Int) {
//        if (!mIsHeaderScrollEnabled) {
//            return
//        }

        scrollTo(0, offset)
        mScrollOffsetY = offset
        mHeaderView?.updateOffset(mScrollOffsetY)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun getNestedScrollAxes(): Int {
        return mNestedScrollingParentHelper.nestedScrollAxes
    }

    override fun scrollTo(x: Int, y: Int) {
        // 限制滑动距离
        if (y > 0) {
            val actualY = min(y, mHeaderScrollOffset)
            super.scrollTo(x, actualY)
        } else {
            super.scrollTo(x, y)
        }
    }

    fun expand() {
        animateToOffset(0)
    }

    fun collapse() {
        animateToOffset(mHeaderScrollOffset)
    }

    @SuppressLint("Recycle")
    private fun animateToOffset(offset: Int) {
//        if (!mIsHeaderScrollEnabled) {
//            return
//        }

        val currentOffset = mScrollOffsetY
        val endOffset = max(0, min(offset, mHeaderScrollOffset))
        if (endOffset == currentOffset) {
            mOffsetAnimator?.let {
                if (it.isRunning) {
                    it.cancel()
                }
            }
            return
        }

        val offsetAnimator = mOffsetAnimator?.also { it.cancel() }
            ?: ValueAnimator().apply {
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    scrollOffsetYTo(it.animatedValue as Int)
                }
            }
        offsetAnimator.run {
            duration = 300
            setIntValues(currentOffset, endOffset)
            start()
        }
    }

    fun updateHeaderScrollOffset(offset: Int) {
        log { "updateHeaderScrollOffset offset: $offset" }
        if (offset > 0) {
            mHeaderScrollOffset = offset
            requestLayout()
        }
    }

    /**
     * 设置头视图是否允许滑动。
     */
    fun setHeaderScrollEnabled(enabled: Boolean, expanded: Boolean) {
        mIsHeaderScrollEnabled = enabled
        moveToState(expanded)
    }

    private inline fun log(msg: () -> String) {
        YFLog.w("StickyHeaderLayout", msg())
    }
}