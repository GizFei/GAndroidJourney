/**
 * 数学计算工具方法。
 *
 * Created by GizFei on 2022/1/14
 */
package coder.giz.android.uidemo.helper

import kotlin.math.max
import kotlin.math.min

/**
 * 将浮点数 [value] 的值限制在 [min] 和 [max] 之间。
 */
fun limitFloatRange(value: Float, min: Float = 0f, max: Float = 1f): Float {
    return max(min, min(value, max))
}

/**
 * 线性插值。
 */
private fun lerp(min: Float, max: Float, factor: Float): Float {
    return min + (max - min) * factor
}