package coder.giz.android.network.helper

import coder.giz.android.yfutility.util.YFLog
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by GizFei on 2021/12/12
 */
object RxJavaHook {
    private const val TAG = "RxJavaHook"

    fun init() {
        RxJavaPlugins.setOnObservableSubscribe { source, observer ->
            YFLog.e(TAG, "onObservableSubscribe source: $source  observer: $observer")
            observer
        }
    }
}