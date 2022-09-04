package coder.giz.android.uidemo.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import coder.giz.android.yfutility.util.YFLog

/**
 * Created by GizFei on 2022/8/28
 */
class StickyNavLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent2 {

    private val mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private var mTopView: ScanningLayout? = null
    private var mTopScrollDistance = 0

    init {
        orientation = VERTICAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            mTopView = getChildAt(0) as? ScanningLayout
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopView?.let { mTopScrollDistance = it.measuredHeight / 2 }
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mNestedScrollingParentHelper.onStopNestedScroll(target, type)
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
        if (dyUnconsumed < 0 && type == ViewCompat.TYPE_NON_TOUCH) {
            scrollBy(0, dyUnconsumed)
        }
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        log { "onNestedPreScroll: dx=$dx  dy=$dy  consumed=$consumed  type=$type" }
        val scrollUp = dy > 0 && scrollY < mTopScrollDistance
        val scrollDown = dy < 0 && scrollY >= 0 && !target.canScrollVertically(-1)
        if (scrollUp || scrollDown) {
            scrollBy(0, dy)
            consumed[1] = dy

            mTopView?.move(-dy.toFloat() * 3)
        }
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun getNestedScrollAxes(): Int {
        return mNestedScrollingParentHelper.nestedScrollAxes
    }

    private inline fun log(msg: () -> String) {
        YFLog.w("StickyNavLayout", msg())
    }
}