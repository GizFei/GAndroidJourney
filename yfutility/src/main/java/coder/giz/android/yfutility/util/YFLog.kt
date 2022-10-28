package coder.giz.android.yfutility.util

import coder.giz.android.yfutility.data.toPrettyJson
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

    fun v(msg: String) {
        Logger.v(msg)
    }

    fun d(tag: String, msg: String) {
        Logger.t(tag).d(msg)
    }

    fun d(msg: String) {
        Logger.d(msg)
    }

    fun i(tag: String, msg: String) {
        Logger.t(tag).i(msg)
    }

    fun i(msg: String) {
        Logger.i(msg)
    }

    fun w(tag: String, msg: String) {
        Logger.t(tag).w(msg)
    }

    fun w(msg: String) {
        Logger.w(msg)
    }

    fun e(tag: String, msg: String) {
        Logger.t(tag).e(msg)
    }

    fun e(msg: String) {
        Logger.e(msg)
    }

    fun json(tag: String, msg: String) {
        Logger.t(tag).json(msg)
    }

    fun json(msg: String) {
        Logger.json(msg)
    }

    fun jsonAny(tag: String, any: Any?) {
        Logger.t(tag).json(any.toPrettyJson())
    }

    fun jsonAny(any: Any?) {
        Logger.json(any.toPrettyJson())
    }

}