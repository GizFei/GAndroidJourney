/**
 * Context相关的扩展函数、扩展属性等
 * Created by GizFei on 2021/1/24
 */
@file:JvmName("ContextUtils")

package coder.giz.android.yfutility.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

/**
 * 最简单的页面跳转。
 *
 * @param destination 跳转的目的地Activity
 */
fun Context.navigate(destination: Class<out AppCompatActivity>) {
    this.startActivity(Intent(this, destination))
}

/**
 * 带数据的页面跳转。
 *
 * @param destination 跳转的目的地Activity
 * @param extras 为 [Intent] 添加数据的扩展函数
 */
fun Context.navigate(destination: Class<out AppCompatActivity>, extras: Intent.() -> Unit) {
    this.startActivity(Intent(this, destination).apply(extras))
}

/**
 * 弹出 [Toast]。
 */
fun Context.toast(text: String, isLong: Boolean = false) {
    val duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    Toast.makeText(this, text, duration).show()
}

/**
 * 弹出 [Toast]。
 */
fun Context.toast(@StringRes resId: Int, isLong: Boolean = false) {
    val duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    Toast.makeText(this, resId, duration).show()
}

