package coder.giz.android.architecture.helper

import coder.giz.android.yfutility.util.YFLog

/**
 * Created by GizFei on 2021/12/4
 */

inline fun logDatabinding(msg: () -> String) {
    YFLog.w("DataBinding", msg())
}