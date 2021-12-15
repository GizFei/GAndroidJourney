package coder.giz.android.network.exception

/**
 * Created by GizFei on 2021/12/12
 */
class NetworkException(
    val errorCode: Int,
    val msg: String
) : Exception(msg)