package coder.giz.android.yfui.helper

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

/**
 * 当手指按下时，控件缩小。手指抬起时，控件恢复到原大小。
 * 该监听器返回false，不消费事件，所以如果没有另外设置onClickListener，则会导致视图无法弹起
 *
 * @param minScale 最小尺寸 [0, 1]
 * @param duration 动画时长
 * @param targetView 进行动画的目标视图
 */
open class OnPressScaleChangeTouchListener(
    private var minScale: Float = 0.88f,
    private var duration: Long = 240L,
    private val targetView: View? = null
) : View.OnTouchListener {

    private var currentScale = 1.0f

    init {
        if (minScale !in 0f..1.0f) {
            minScale = 0.8f
        }
    }

    private val STATE_DOWN = 0
    private val STATE_UP = 1
    private var state: Int = STATE_UP

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val v = targetView ?: view
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                onActionDown()
                if (state == STATE_UP) {
                    startAnimation(v, currentScale, minScale)
                    state = STATE_DOWN
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (state == STATE_DOWN) {
                    onActionUpOrCancel()
                    startAnimation(v, currentScale, 1.0f)
                    state = STATE_UP
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.x !in 0f..v.width.toFloat()
                    || event.y !in 0f..v.height.toFloat()
                ) {
                    if (state == STATE_DOWN) {
                        onActionMoveOut()
                        startAnimation(v, currentScale, 1.0f)
                        state = STATE_UP
                    }
                }
            }
        }
        return false // 继续执行onTouchEvent，使OnClickListener等有效
    }

    private fun startAnimation(v: View, fromScale: Float, toScale: Float) {
        val animator = ValueAnimator.ofFloat(fromScale, toScale)
        animator.duration = duration
        animator.interpolator = FastOutSlowInInterpolator()
        animator.addUpdateListener {
            currentScale = it.animatedValue as Float
            v.scaleX = currentScale
            v.scaleY = currentScale
        }
        animator.start()
    }

    open fun onActionDown() {}
    open fun onActionUpOrCancel() {}
    open fun onActionMoveOut() {}
}