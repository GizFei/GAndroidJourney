package coder.giz.android.yfui.yfutility.util

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 * 日志、打印工具函数
 *
 * Created by GizFei on 2021/10/1
 */

inline fun Activity.logLifecycle(lifecycle: () -> String) {
    YFLog.e("Lifecycle", "Activity[${this::class.simpleName}] Lifecycle is: ${lifecycle()}")
}

inline fun Fragment.logLifecycle(lifecycle: () -> String) {
    YFLog.e("Lifecycle", "Fragment[${this::class.simpleName}] Lifecycle is: ${lifecycle()}")
}