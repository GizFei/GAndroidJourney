/**
 * 视图扩展方法
 *
 * Created by GizFei on 2022/10/2
 */
package coder.giz.android.yfui.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible

fun TextView.setTextAndVisible(text: String?) {
    this.text = text
    this.isVisible = !text.isNullOrEmpty()
}

fun ImageView.setImageResourceAndVisible(@DrawableRes resId: Int?) {
    if (resId != null) {
        setImageResource(resId)
        isVisible = true
    } else {
        isVisible = false
    }
}