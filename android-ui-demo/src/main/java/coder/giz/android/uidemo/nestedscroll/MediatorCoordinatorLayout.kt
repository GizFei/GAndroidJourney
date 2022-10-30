package coder.giz.android.uidemo.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild2
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import coder.giz.android.yfutility.data.toJson
import coder.giz.android.yfutility.util.YFLog

/**
 * 处理列表分发上来的滚动事件。
 * 处理AppBarLayout消费后剩下的滚动事件。
 *
 * Created by GizFei on 2022/10/7
 */
class MediatorCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CoordinatorLayout(context, attrs, defStyleAttr), NestedScrollingChild2 {

    private var _nestedScrollingChildHelper: NestedScrollingChildHelper? = null
    private val mNestedScrollingChildHelper: NestedScrollingChildHelper get() {
        return _nestedScrollingChildHelper ?: NestedScrollingChildHelper(this).also {
            _nestedScrollingChildHelper = it
        }
    }

    // region NestedScrollingParent2能力

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        val handled = super.onStartNestedScroll(child, target, axes, type)
        return if (handled) {
            true
        } else {
            (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
        }
    }

    override fun onNestedScrollAccepted(
        child: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ) {
        // 接收子视图的嵌套滑动请求。同时，自身作为子视图，开启嵌套滑动。
        super.onNestedScrollAccepted(child, target, nestedScrollAxes, type)
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        log { "onNestedPreScroll dx: $dx dy: $dy type: $type consumed: ${consumed.toJson()}" }

        // 自身只当作Child，向父视图传滑动。
//        dispatchNestedPreScroll(dx, dy, consumed, null, type)

        // 同时当Child和Parent。
        if (dy > 0) {
            // 向上滑动
            // 先当作Child，先分发，向父视图传递滑动事件。因为向上滑动时，顶部ScanningLayout先折叠
            dispatchNestedPreScroll(dx, dy, consumed, null, type)
            // 再当作Parent，消费剩余的滑动事件
            val dx2 = dx - consumed[0]
            val dy2 = dy - consumed[1]
            log { "onNestedPreScroll after dispatchNestedPreScroll dx2: $dx2 dy2: $dy2 consumed: ${consumed.toJson()}" }
            if (dy2 > 0) {
                super.onNestedPreScroll(target, dx, dy, consumed, type)
            }
        } else if (dy < 0) {
            // 向下滑动
            // 先当作Parent，处理滑动事件。
//            super.onNestedPreScroll(target, dx, dy, consumed, type)
            // 再当作Child，向父视图传递滑动事件。因为向下滑动时，顶部ScanningLayout后展开。
            val dx2 = dx - consumed[0]
            val dy2 = dy - consumed[1]
            log { "onNestedPreScroll after super.onNestedPreScroll dx2: $dx2 dy2: $dy2 consumed: ${consumed.toJson()}" }
            if (dy2 < 0) {
//                dispatchNestedPreScroll(dx, dy, consumed, null, type)
            }
        }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        log { "onNestedScroll dyConsumed: $dyConsumed dyUnconsumed: $dyUnconsumed consumed: ${consumed.toJson()}" }
        // 子视图RecyclerView滑动后，由自己滑动。
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
        // 自己滑动后，再交由父视图处理
        log { "onNestedScroll after self.onNestedScroll dyConsumed: $dyConsumed dyUnconsumed: $dyUnconsumed consumed: ${consumed.toJson()}" }
        if (dyUnconsumed > 0) {
            // 向上滑动

        } else if (dyUnconsumed < 0) {
            // 向下滑动
            val dy = dyUnconsumed - consumed[1]
            if (dy < 0) {
                // 还有可滑动量
                log { "onNestedScroll after self.onNestedScroll dispatchNestedScroll" }
                mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                    dxUnconsumed, dyUnconsumed, null, type, consumed)
            }
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        super.onStopNestedScroll(target, type)
    }

    // endregion

    // region NestedScrollingChild2能力

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mNestedScrollingChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return mNestedScrollingChildHelper.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll(type: Int) {
        mNestedScrollingChildHelper.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return mNestedScrollingChildHelper.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        log { "dispatchNestedScroll" }
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        log { "dispatchNestedPreScroll" }
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    // endregion

    private inline fun log(msg: () -> String) {
        YFLog.w("MediatorCoordinatorLayout", msg())
    }
}