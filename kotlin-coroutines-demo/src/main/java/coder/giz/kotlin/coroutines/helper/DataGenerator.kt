package coder.giz.kotlin.coroutines.helper

import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem
import coder.giz.kotlin.coroutines.practice.series1.Series1Demo1Activity
import coder.giz.kotlin.coroutines.practice.series1.Series1Demo2Activity

/**
 * Created by GizFei on 2021/11/14
 */
internal object DataGenerator {
    val KotlinCoroutinesDemoNavItems = listOf(
        HeaderNavItem("Series1"),
        ActivityNavItem(
            "系列文章（一）",
            "史上最详Android版kotlin协程入门进阶实战(一) - Kotlin协程的基础用法",
            Series1Demo1Activity::class
        ),
        ActivityNavItem(
            "系列文章（四）",
            "史上最详Android版kotlin协程入门进阶实战(四) -> 使用kotlin协程开发Android的应用",
            Series1Demo2Activity::class
        ),
    )

    val OperatorSystemList = listOf("Android", "iOS", "Windows", "MacOS", "Linux", "Ubuntu")
    val CompanyProductMap = mapOf(
        "Google" to "Android",
        "Apple" to "iOS",
        "Microsoft" to "Windows",
        "Amazon" to "AmazonWeb"
    )

}