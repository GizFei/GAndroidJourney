package coder.giz.android.yfui.utility.util

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * 封装第三方日志打印库
 *
 * Created by GizFei on 2021/7/4
 */
object YFLog {

    init {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    fun v(tag: String, msg: String) {
        Logger.t(tag).v(msg)
    }

    fun d(tag: String, msg: String) {
        Logger.t(tag).d(msg)
    }

    fun i(tag: String, msg: String) {
        Logger.t(tag).i(msg)
    }

    fun w(tag: String, msg: String) {
        Logger.t(tag).w(msg)
    }

    fun e(tag: String, msg: String) {
        Logger.t(tag).e(msg)
    }

    fun json(tag: String, msg: String) {
        Logger.t(tag).json(msg)
    }

}