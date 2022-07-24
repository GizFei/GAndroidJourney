/**
 * Gson工具方法。
 *
 * Created by GizFei on 2022/3/14
 */
package coder.giz.android.yfutility.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

private val GSON = GsonBuilder()
    .disableHtmlEscaping()
    .create()

private val PRETTY_GSON = GsonBuilder()
    .disableHtmlEscaping()
    .setPrettyPrinting()
    .create()

val COMMON_GSON: Gson = GSON

fun Any?.toJson(): String = GSON.toJson(this)

fun Any?.toJson(clz: Class<*>): String = GSON.toJson(this, clz)

fun <T> String.toObject(clz: Class<T>): T = GSON.fromJson(this, clz) ?: clz.newInstance()

fun <T> String.toObjectOrNull(clz: Class<T>): T? = try {
    GSON.fromJson(this, clz)
} catch (e: Exception) {
    null
}

fun <T> String.toObject(typeOfT: Type): T = GSON.fromJson(this, typeOfT)

fun <T> String.toObjectOrNull(typeOfT: Type): T? = try {
    GSON.fromJson(this, typeOfT)
} catch (e: Exception) {
    null
}

inline fun <reified T> String.toObject(): T = COMMON_GSON.fromJson(this, T::class.java)

inline fun <reified T> String.toObjectOrNull(): T? = try {
    COMMON_GSON.fromJson(this, T::class.java)
} catch (e: Exception) {
    null
}

fun Any?.toPrettyJson(): String = PRETTY_GSON.toJson(this)

fun Any?.toPrettyJson(clz: Class<*>): String = PRETTY_GSON.toJson(this, clz)