package com.example.android.kotlincoroutines.util

/**
 * Created by GizFei on 2022/10/24
 */

fun String.appendThreadInfo() = "$this | Thread[Name: ${Thread.currentThread().name}]"