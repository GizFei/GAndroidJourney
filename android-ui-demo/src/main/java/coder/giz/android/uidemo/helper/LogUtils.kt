package coder.giz.android.uidemo.helper

import coder.giz.android.yfutility.util.YFLog

/**
 * Created by GizFei on 2022/7/29
 */
inline fun printRecyclerViewLog(msg: () -> String) {
    YFLog.w("RecyclerView", msg())
}