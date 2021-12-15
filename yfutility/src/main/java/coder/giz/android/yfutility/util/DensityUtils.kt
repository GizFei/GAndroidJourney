package coder.giz.android.yfutility.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by GizFei on 2021/10/2
 */

fun Context.dp2px(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)

fun Context.dp2px(dp: Int): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics)

val Int.dp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
    Resources.getSystem().displayMetrics)