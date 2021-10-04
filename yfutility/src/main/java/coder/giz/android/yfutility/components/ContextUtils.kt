/**
 * Context相关的扩展函数、扩展属性等
 * Created by GizFei on 2021/1/24
 */
@file:JvmName("ContextUtils")

package coder.giz.android.yfutility.components

import android.content.Context
import android.content.Intent
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

