package coder.giz.android.uidemo.animation.attention

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import coder.giz.android.yfutility.util.dp2px
import kotlin.math.abs

/**
 * 垂直/水平方向的摇动动画。
 *
 * Created by GizFei on 2022/7/24
 */
class ShakeAnimation(private val mView: View) {

    companion object {
        private const val PROPERTY_TRANSLATION_Y = "translationY"
        private const val PROPERTY_TRANSLATION_X = "translationX"
    }

    private var mTranslationDistance: Float = mView.context.dp2px(16)
    private var mRepeatCount: Int = ValueAnimator.INFINITE
    private var mDirection: Direction = Direction.VERTICAL_TOP

    /**
     * 动画步骤是：向上（下）平移至最远处，再从最远处平移至原点。该参数控制这两个步骤是否分开，如果分开，则插值器分别运用于
     * 两个步骤。如果不分开，这两步算作一整步。
     */
    private var mIsSplitStep: Boolean = true

    private var mAnimator: ObjectAnimator? = null

    /**
     * 创建新动画。
     */
    fun createAnimator(config: ObjectAnimator.() -> Unit = {}) {
        val targetTranslationY = when (mDirection) {
            Direction.VERTICAL_TOP, Direction.HORIZONTAL_LEFT -> -mTranslationDistance
            Direction.VERTICAL_BOTTOM, Direction.HORIZONTAL_RIGHT -> mTranslationDistance
        }
        val propertyName = when (mDirection) {
            Direction.VERTICAL_TOP, Direction.VERTICAL_BOTTOM -> PROPERTY_TRANSLATION_Y
            Direction.HORIZONTAL_LEFT, Direction.HORIZONTAL_RIGHT -> PROPERTY_TRANSLATION_X
        }
        if (mIsSplitStep) {
            mAnimator = ObjectAnimator.ofFloat(
                mView, propertyName,
                0f, targetTranslationY
            ).apply {
                repeatCount = mRepeatCount
                repeatMode = ValueAnimator.REVERSE
                this.config()
            }
        } else {
            mAnimator = ObjectAnimator.ofFloat(
                mView, propertyName,
                0f, targetTranslationY, 0f
            ).apply {
                repeatCount = mRepeatCount
                repeatMode = ValueAnimator.RESTART
                this.config()
            }
        }
    }

    /**
     * 开始动画。
     */
    fun startAnim() {
        ensureAnimator()
        mAnimator?.start()
    }

    /**
     * 当视图回到原点时，停止动画。
     */
    fun stopAnim() {
        if (mAnimator?.isRunning == false) {
            return
        }
        mAnimator?.run {
            removeAllUpdateListeners()
            addUpdateListener {
                val isZero = abs(it.animatedValue as Float) < 0.1f
                if (isZero) {
                    mAnimator?.cancel()
                    mView.translationY = 0f
                    removeAllUpdateListeners()
                }
            }
        }
    }

    /**
     * 结束动画，立即停留在动画的最后一帧
     */
    fun endAnim() {
        mAnimator?.end()
    }

    /**
     * 取消动画。停留在取消时的一帧。
     */
    fun cancelAnim() {
        mAnimator?.cancel()
    }

    private fun ensureAnimator() {
        if (mAnimator == null) {
            createAnimator()
        }
    }

    fun setTranslationDistance(distance: Float) {
        mTranslationDistance = distance
    }

    fun setTranslationDistanceInDp(distanceInDp: Float) {
        mTranslationDistance = mView.context.dp2px(distanceInDp)
    }

    fun setRepeatCount(value: Int) {
        mRepeatCount = value
    }

    fun setRepeatInfinite() {
        mRepeatCount = ValueAnimator.INFINITE
    }

    fun setDirection(direction: Direction) {
        mDirection = direction
    }

    fun setSplitStep(value: Boolean) {
        mIsSplitStep = value
    }

    enum class Direction {
        // 向上摇动
        VERTICAL_TOP,
        // 向下摇动
        VERTICAL_BOTTOM,
        // 向左摇动
        HORIZONTAL_LEFT,
        // 向右摇动
        HORIZONTAL_RIGHT,
    }

}