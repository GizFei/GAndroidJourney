package coder.giz.android.uidemo.expandable

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.view.updateLayoutParams

/**
 * Created by GizFei on 2021/12/7
 */
class CollapseAnimation(
    private val view: View,
    private val startHeight: Int,
    private val endHeight: Int
) : Animation() {

    private val deltaHeight = endHeight -  startHeight

    init {
        init()
    }

    private fun init() {
        duration = DEFAULT_DURATION
        interpolator = AccelerateDecelerateInterpolator()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)
        if (interpolatedTime < 1f) {
            val curHeight = startHeight + deltaHeight * interpolatedTime
            view.updateLayoutParams { height = curHeight.toInt() }
        } else {
            view.updateLayoutParams { height = endHeight }
        }
    }

    companion object {
        private const val DEFAULT_DURATION = 300L
    }

}