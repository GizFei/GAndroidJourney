/**
 * Intent相关的扩展函数、扩展属性等
 * Created by GizFei on 2021/1/24
 */
package coder.giz.android.yfutility.components

import android.content.Intent
import android.os.Bundle

/**
 * 为 [Intent] 添加 [Bundle] 数据。
 *
 * @param data 存入Bundle中的数据
 */
fun Intent.bundle(data: Bundle.() -> Unit) {
    this.putExtras(Bundle().apply(data))
}