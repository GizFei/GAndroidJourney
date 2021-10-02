package coder.giz.android.bottomsheet.util

import android.util.Log

/**
 * Created by GizFei on 2021/7/7
 */

inline fun logBottomSheet(msg: () -> String) {
    Log.e("BottomSheet", msg())
}