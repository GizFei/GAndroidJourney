package coder.giz.android.uidemo.animation.attention

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Created by GizFei on 2022/7/24
 */
object ShakeAnimationHelper {

    // 默认平移距离
    private const val DEFAULT_DISTANCE_IN_DP = 32f
    // 默认动画时长：1000ms
    private const val DEFAULT_DURATION = 1000L

    fun createShakeVerticalTopAnimation(
        view: View,
        translationDistanceInDp: Float = DEFAULT_DISTANCE_IN_DP,
        animatorConfig: (ObjectAnimator.() -> Unit)? = null,
    ): ShakeAnimation {
        return createShakeAnimation(
            ShakeAnimation.Direction.VERTICAL_TOP,
            view, translationDistanceInDp, animatorConfig
        )
    }

    fun createShakeVerticalBottomAnimation(
        view: View,
        translationDistanceInDp: Float = DEFAULT_DISTANCE_IN_DP,
        animatorConfig: (ObjectAnimator.() -> Unit)? = null,
    ): ShakeAnimation {
        return createShakeAnimation(
            ShakeAnimation.Direction.VERTICAL_BOTTOM,
            view, translationDistanceInDp, animatorConfig
        )
    }

    fun createShakeHorizontalLeftAnimation(
        view: View,
        translationDistanceInDp: Float = DEFAULT_DISTANCE_IN_DP,
        animatorConfig: (ObjectAnimator.() -> Unit)? = null,
    ): ShakeAnimation {
        return createShakeAnimation(
            ShakeAnimation.Direction.HORIZONTAL_LEFT,
            view, translationDistanceInDp, animatorConfig
        )
    }

    fun createShakeHorizontalRightAnimation(
        view: View,
        translationDistanceInDp: Float = DEFAULT_DISTANCE_IN_DP,
        animatorConfig: (ObjectAnimator.() -> Unit)? = null,
    ): ShakeAnimation {
        return createShakeAnimation(
            ShakeAnimation.Direction.HORIZONTAL_RIGHT,
            view, translationDistanceInDp, animatorConfig
        )
    }

    private fun createShakeAnimation(
        direction: ShakeAnimation.Direction,
        view: View,
        translationDistanceInDp: Float = DEFAULT_DISTANCE_IN_DP,
        animatorConfig: (ObjectAnimator.() -> Unit)? = null,
    ): ShakeAnimation {
        return ShakeAnimation(view).apply {
            setTranslationDistanceInDp(translationDistanceInDp)
            setDirection(direction)
            setSplitStep(true)
            val configuration = animatorConfig ?: {
                duration = DEFAULT_DURATION
                interpolator = AccelerateDecelerateInterpolator()
            }
            createAnimator(configuration)
        }
    }

}