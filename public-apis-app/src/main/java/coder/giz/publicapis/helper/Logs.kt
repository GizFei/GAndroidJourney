package coder.giz.publicapis.helper

import coder.giz.android.yfutility.util.YFLog

/**
 * Created by GizFei on 2022/10/29
 */

const val DEFAULT_TAG = "PublicApisAppLog"

inline fun logD(msg: () -> String) {
    YFLog.d(DEFAULT_TAG, msg())
}

inline fun logD(tag: String, msg: () -> String) {
    YFLog.d(tag, msg())
}

inline fun logW(msg: () -> String) {
    YFLog.w(DEFAULT_TAG, msg())
}

inline fun logW(tag: String, msg: () -> String) {
    YFLog.w(tag, msg())
}

inline fun logE(msg: () -> String) {
    YFLog.e(DEFAULT_TAG, msg())
}

inline fun logE(tag: String, msg: () -> String) {
    YFLog.e(tag, msg())
}