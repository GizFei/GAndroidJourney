package coder.giz.android.uidemo.slider.elasticscale

import android.view.MotionEvent
import android.view.View

/**
 * Created by GizFei on 2022/1/15
 */
abstract class ElasticScaleEffectHelper(
    protected val mView: View,
    protected val mFactors: Factors,
    protected val mElasticScaleEffect: ElasticScaleEffect = ElasticScaleEffect(),
) {

    /**
     * 原始因数。
     */
    private var mRawFactor: Float = 0f

    fun handleMotionEvent(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                updateRawFactor()
            }
            MotionEvent.ACTION_MOVE -> {
                updateRawFactor()
                applyEffect(mRawFactor)
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                updateRawFactor()
                recoverEffect(mRawFactor)
            }
        }
    }

    private fun updateRawFactor() {
        mRawFactor = mFactors.getCurrentRawFactor()
    }

    abstract fun applyEffect(rawFactor: Float)

    abstract fun recoverEffect(rawFactor: Float)

    interface Factors {
        val upperLimitFactor: Float get() = 1f
        val lowerLimitFactor: Float get() = 0f

        /**
         * 最大形变因数。形变范围就限制在：0 - mMaxEffectFactor。
         */
        val maxEffectFactor: Float get() = 0.5f

        /**
         * 获取当前形变因数。
         */
        fun getCurrentRawFactor(): Float
    }

}