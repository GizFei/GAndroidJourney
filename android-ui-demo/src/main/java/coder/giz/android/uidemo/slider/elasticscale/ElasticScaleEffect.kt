package coder.giz.android.uidemo.slider.elasticscale

import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.FloatRange
import coder.giz.android.yfutility.util.YFLog
import com.google.android.material.math.MathUtils.lerp

/**
 * 弹性缩放形变。
 * 1、垂直方向
 *    - 高度：放大
 *    - 宽度：缩小
 *    - 位移：向上或向下偏移微小距离
 * 2、水平方向
 *    - 高度：缩小
 *    - 宽度：放大
 *    - 位移：向左或向右偏移微波距离
 *
 * Created by GizFei on 2022/1/14
 */
open class ElasticScaleEffect {

    /**
     * 向上拖拽滑出范围。
     */
    fun upwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
    ) {
        val params = getParams(Direction.UPWARDS)
        view.run {
            // 将轴心移到视图底部中心
            pivotY = height.toFloat()
            scaleX = lerpScaleX(params, factor)
            scaleY = lerpScaleY(params, factor)
            translationY = lerpTranslation(params, factor)
        }
/*        log {
            """
                dragUp:
                   factor = $factor
                   scaleX = ${lerpScaleX(Direction.UPWARDS, factor)}
                   scaleY = ${lerpScaleY(Direction.UPWARDS, factor)}
                   translationY = ${lerpTranslation(Direction.UPWARDS, factor)}
                   height = ${view.height}
            """.trimIndent()
        }*/
    }

    /**
     * 向上拖拽滑出范围。使用动画。
     */
    fun animateUpwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
        duration: Long = DEFAULT_DURATION,
        animatorConfigure: (ViewPropertyAnimator.() -> Unit)? = null,
    ) {
        val params = getParams(Direction.UPWARDS)
        // 将轴心移到视图底部中心
        view.run {
            pivotY = height.toFloat()
            animate()
                .scaleX(lerpScaleX(params, factor))
                .scaleY(lerpScaleY(params, factor))
                .translationY(lerpTranslation(params, factor))
                .setDuration(duration)
                .also { animatorConfigure?.invoke(it) }
                .start()
        }
    }

    /**
     * 向下拖拽滑出范围。
     */
    fun downwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
    ) {
        val params = getParams(Direction.DOWNWARDS)
        view.run {
            // 将轴心移到视图顶部中心
            pivotY = 0f
            scaleX = lerpScaleX(params, factor)
            scaleY = lerpScaleY(params, factor)
            translationY = lerpTranslation(params, factor)
        }
    }

    /**
     * 向下拖拽滑出范围。使用动画。
     */
    fun animateDownwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
        duration: Long = DEFAULT_DURATION,
        animatorConfigure: (ViewPropertyAnimator.() -> Unit)? = null,
    ) {
        val params = getParams(Direction.DOWNWARDS)
        // 将轴心移到视图顶部中心
        view.apply {
            pivotY = 0f
            animate()
                .scaleX(lerpScaleX(params, factor))
                .scaleY(lerpScaleY(params, factor))
                .translationY(lerpTranslation(params, factor))
                .setDuration(duration)
                .also { animatorConfigure?.invoke(it) }
                .start()
        }
    }

    fun leftwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
    ) {
        val params = getParams(Direction.LEFTWARDS)
        view.run {
            pivotX = width.toFloat()
            scaleX = lerpScaleX(params, factor)
            scaleY = lerpScaleY(params, factor)
            translationX = lerpTranslation(params, factor)
        }
    }

    fun animateLeftwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
        duration: Long = DEFAULT_DURATION,
        animatorConfigure: (ViewPropertyAnimator.() -> Unit)? = null,
    ) {
        val params = getParams(Direction.LEFTWARDS)
        view.run {
            pivotX = width.toFloat()
            animate()
                .scaleX(lerpScaleX(params, factor))
                .scaleY(lerpScaleY(params, factor))
                .translationX(lerpTranslation(params, factor))
                .setDuration(duration)
                .also { animatorConfigure?.invoke(it) }
                .start()
        }
    }

    fun rightwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
    ) {
        val params = getParams(Direction.RIGHTWARDS)
        view.run {
            pivotX = 0f
            scaleX = lerpScaleX(params, factor)
            scaleY = lerpScaleY(params, factor)
            translationX = lerpTranslation(params, factor)
        }
    }

    fun animateRightwards(
        view: View,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
        duration: Long = DEFAULT_DURATION,
        animatorConfigure: (ViewPropertyAnimator.() -> Unit)? = null,
    ) {
        val params = getParams(Direction.RIGHTWARDS)
        view.run {
            pivotX = 0f
            animate()
                .scaleX(lerpScaleX(params, factor))
                .scaleY(lerpScaleY(params, factor))
                .translationX(lerpTranslation(params, factor))
                .setDuration(duration)
                .also { animatorConfigure?.invoke(it) }
                .start()
        }
    }

    open fun getParams(direction: Direction): EffectParams = when (direction) {
        Direction.UPWARDS -> EffectParams.Upwards
        Direction.DOWNWARDS -> EffectParams.Downwards
        Direction.LEFTWARDS -> EffectParams.Leftwards
        Direction.RIGHTWARDS -> EffectParams.Rightwards
    }

    private fun lerpScaleX(
        params: EffectParams,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
    ): Float {
        return lerp(params.originScaleX, params.targetScaleX, factor)
    }

    private fun lerpScaleY(
        params: EffectParams,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
    ): Float {
        return lerp(params.originScaleY, params.targetScaleY, factor)
    }

    private fun lerpTranslation(
        params: EffectParams,
        @FloatRange(from = 0.0, to = 1.0)
        factor: Float,
    ): Float {
        return lerp(params.originTranslation, params.targetTranslation, factor)
    }

    private inline fun log(msg: () -> String) {
        YFLog.w("ElasticScaleEffect", msg())
    }

    enum class Direction {
        UPWARDS, DOWNWARDS, LEFTWARDS, RIGHTWARDS
    }

    abstract class EffectParams {
        abstract val originScaleX: Float
        abstract val targetScaleX: Float
        abstract val originScaleY: Float
        abstract val targetScaleY: Float
        abstract val originTranslation: Float
        abstract val targetTranslation: Float

        open class Upwards : EffectParams() {
            override val originScaleX: Float = 1f
            override val targetScaleX: Float = 0.9f
            override val originScaleY: Float = 1f
            override val targetScaleY: Float = 1.1f
            override val originTranslation: Float = 0f
            override val targetTranslation: Float = -DEFAULT_TRANSLATION

            companion object : Upwards()
        }

        open class Downwards : EffectParams() {
            override val originScaleX: Float = 1f
            override val targetScaleX: Float = 0.9f
            override val originScaleY: Float = 1f
            override val targetScaleY: Float = 1.1f
            override val originTranslation: Float = 0f
            override val targetTranslation: Float = DEFAULT_TRANSLATION

            companion object : Downwards()
        }

        open class Leftwards : EffectParams() {
            override val originScaleX: Float = 1f
            override val targetScaleX: Float = 1.1f
            override val originScaleY: Float = 1f
            override val targetScaleY: Float = 0.9f
            override val originTranslation: Float = 0f
            override val targetTranslation: Float = -DEFAULT_TRANSLATION

            companion object : Leftwards()
        }

        open class Rightwards : EffectParams() {
            override val originScaleX: Float = 0f
            override val targetScaleX: Float = 1.1f
            override val originScaleY: Float = 1f
            override val targetScaleY: Float = 0.9f
            override val originTranslation: Float = 0f
            override val targetTranslation: Float = DEFAULT_TRANSLATION

            companion object : Rightwards()
        }

        companion object {
            private const val DEFAULT_TRANSLATION = 64f
        }
    }

    companion object {
        private const val DEFAULT_DURATION = 300L
    }

}