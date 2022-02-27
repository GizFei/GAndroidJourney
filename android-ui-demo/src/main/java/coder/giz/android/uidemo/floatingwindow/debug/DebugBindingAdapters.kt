package coder.giz.android.uidemo.floatingwindow.debug

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coder.giz.android.uidemo.R

/**
 * Created by GizFei on 2022/2/25
 */

private fun loadVisibilityAnim(
    context: Context,
    @AnimRes animRes: Int,
    animDuration: Long = 300,
    doOnStart: ((Animation?) -> Unit)? = null,
    doOnEnd: ((Animation?) -> Unit)? = null,
): Animation {
    return AnimationUtils.loadAnimation(context, animRes).apply {
        duration = animDuration
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                doOnStart?.invoke(animation)
            }

            override fun onAnimationEnd(animation: Animation?) {
                doOnEnd?.invoke(animation)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }
}

@BindingAdapter("translateVisibility")
fun translateViewVisibility(view: View, visible: Boolean) {
    if (view.animation?.hasEnded() != false
        && visible == view.isVisible) {
        return
    }

    if (!visible) {
        view.animation?.cancel()
    }

    if (visible) {
        val animation = loadVisibilityAnim(view.context, R.anim.slide_in_from_top, doOnStart = {
            view.isVisible = true
        })
        view.startAnimation(animation)
    } else {
        val animation = loadVisibilityAnim(view.context, R.anim.slide_out_to_bottom, doOnEnd = {
            view.isVisible = false
        })
        view.startAnimation(animation)
    }
}

@BindingAdapter("zoomVisibility")
fun zoomViewVisibility(view: View, visible: Boolean) {
    if (view.animation?.hasEnded() != false
        && visible == view.isVisible) {
        return
    }

    if (!visible) {
        view.animation?.cancel()
    }

    if (visible) {
        val animation = loadVisibilityAnim(view.context, R.anim.zoom_in, doOnStart = {
            view.isVisible = true
        })
        view.startAnimation(animation)

    } else {
        val animation = loadVisibilityAnim(view.context, R.anim.zoom_out, doOnEnd = {
            view.isVisible = false
        })
        view.startAnimation(animation)
    }
}