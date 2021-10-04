/**
 * Dialog相关的扩展属性、扩展方法等。
 *
 * Created by GizFei on 2021/1/31
 */
@file:JvmName("DialogUtils")
package coder.giz.android.yfutility.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment

/**
 * 去掉 [Dialog] 的内边距
 */
fun Dialog.removePadding(): Dialog {
    window?.decorView?.setPadding(0)
    return this
}

/**
 * 使 [Dialog] 占满屏幕宽高。四边离屏幕边缘有一点距离。
 */
fun Dialog.fillScreen(): Dialog {
    window?.run {
        // 去掉内边距
        decorView.setPadding(0)
        // 宽高铺满
        attributes = attributes.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }
    return this
}

/**
 * [Dialog] 占满屏幕宽度。左右两边离屏幕边缘有一点距离。
 */
fun Dialog.fillScreenWidth(): Dialog {
    window?.run {
        decorView.setPadding(0)
        attributes = attributes.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
        }
    }
    return this
}

/**
 * [Dialog] 占满屏幕高度。上下两边离屏幕边缘有一点距离。
 */
fun Dialog.fillScreenHeight(): Dialog {
    window?.run {
        decorView.setPadding(0)
        attributes = attributes.apply {
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }
    return this
}

/**
 * 使 [Dialog] 全屏显示。背景默认透明。与屏幕边缘没有缝隙。
 * @param bgColor 背景颜色
 */
fun Dialog.fullscreen(bgColor: Int = Color.TRANSPARENT): Dialog {
    window?.run {
        setBackgroundDrawable(ColorDrawable(bgColor))
        // 去掉内边距
        decorView.setPadding(0)
        // 全屏显示
        attributes = attributes.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }
    return this
}

/**
 * 使 [Dialog] 宽度全屏显示。背景默认透明。与屏幕边缘没有缝隙。
 * @param bgColor 背景颜色
 */
fun Dialog.matchScreenWith(bgColor: Int = Color.TRANSPARENT): Dialog {
    window?.run {
        setBackgroundDrawable(ColorDrawable(bgColor))
        // 去掉内边距
        decorView.setPadding(0)
        // 全屏显示
        attributes = attributes.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
        }
    }
    return this
}

/**
 * 使 [Dialog] 高度全屏显示。背景默认透明。与屏幕边缘没有缝隙。
 * @param bgColor 背景颜色
 */
fun Dialog.matchScreenHeight(bgColor: Int = Color.TRANSPARENT): Dialog {
    window?.run {
        setBackgroundDrawable(ColorDrawable(bgColor))
        // 去掉内边距
        decorView.setPadding(0)
        // 全屏显示
        attributes = attributes.apply {
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }
    return this
}

/**
 * 使 [DialogFragment] 中的Dialog全屏显示，且背景变透明。
 * 常用于底部弹出的 [DialogFragment]
 */
fun DialogFragment.makeFullscreen() {
    dialog?.window?.run {
        // 背景透明
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // 去掉内边距
        decorView.setPadding(0)
        // 全屏显示
        attributes = attributes.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }
}