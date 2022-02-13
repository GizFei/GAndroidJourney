package coder.giz.android.uidemo.slider.elasticscale

import android.view.View
import coder.giz.android.uidemo.helper.limitFloatRange
import kotlin.math.abs

/**
 * Created by GizFei on 2022/1/15
 */
class HorizontalElasticScaleEffectHelper(
    view: View,
    factors: Factors,
    elasticScaleEffect: ElasticScaleEffect = ElasticScaleEffect(),
) : ElasticScaleEffectHelper(view, factors, elasticScaleEffect) {

    override fun applyEffect(rawFactor: Float) {
        if (rawFactor >= mFactors.upperLimitFactor) {
            val exceedFactor = rawFactor - mFactors.upperLimitFactor
            val factor = limitFloatRange(exceedFactor / mFactors.maxEffectFactor)
            mElasticScaleEffect.rightwards(mView, factor)
        } else if (rawFactor <= mFactors.lowerLimitFactor) {
            val exceedFactor = abs(rawFactor - mFactors.lowerLimitFactor)
            val factor = limitFloatRange(exceedFactor / mFactors.maxEffectFactor)
            mElasticScaleEffect.leftwards(mView, factor)
        }
    }

    override fun recoverEffect(rawFactor: Float) {
        if (rawFactor >= mFactors.upperLimitFactor) {
            mElasticScaleEffect.animateRightwards(mView, 0f)
        } else if (rawFactor <= mFactors.lowerLimitFactor) {
            mElasticScaleEffect.animateLeftwards(mView, 0f)
        }
    }

}