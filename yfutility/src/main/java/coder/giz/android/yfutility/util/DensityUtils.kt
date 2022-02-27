package coder.giz.android.yfutility.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

/**
 * Created by GizFei on 2021/10/2
 */

fun Context.dp2px(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)

fun Context.dp2px(dp: Int): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics)

fun Context.sp2px(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, this.resources.displayMetrics)

fun Context.sp2px(dp: Int): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp.toFloat(), this.resources.displayMetrics)

val Int.dp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
    Resources.getSystem().displayMetrics)

val Int.sp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(),
    Resources.getSystem().displayMetrics)

/**
 * 获取屏幕度量参数
 */
private fun getWindowMetrics(context: Context) : DisplayMetrics? {
    val wm : WindowManager? = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    if(wm != null){
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        return metrics
    }
    return null
}
/**
 * 获得屏幕的宽
 * 默认宽度：1080
 * @param context 上下文
 * @return 宽度：Int
 * @see getScreenHeight
 */
fun getScreenWidth(context: Context) : Int = getWindowMetrics(context)?.widthPixels ?: 1080
/**
 * 获得屏幕的高
 * 默认高度：1920
 * @param context 上下文
 * @return 高度：Int
 * @see getScreenWidth
 */
fun getScreenHeight(context: Context) : Int = getWindowMetrics(context)?.heightPixels ?: 1920