package coder.giz.android.network.helper

/**
 * Created by GizFei on 2021/12/12
 */
data class NetDataWrapper<T>(
    val data: T? = null,
    val throwable: Throwable? = null
)