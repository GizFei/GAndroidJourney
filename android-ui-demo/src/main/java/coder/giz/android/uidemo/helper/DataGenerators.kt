package coder.giz.android.uidemo.helper

import coder.giz.android.uidemo.popupwindow.PopupWindowDemoActivity
import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem

/**
 * Created by GizFei on 2021/11/14
 */
internal object DataGenerators {
    val AndroidUiDemoNavItems = listOf(
        HeaderNavItem("Widget"),
        ActivityNavItem("PopupWindow", "PopupWindow使用",
            PopupWindowDemoActivity::class),
    )

    val OperatorSystemList = listOf("Android", "iOS", "Windows", "MacOS", "Linux", "Ubuntu")
    val CompanyProductMap = mapOf(
        "Google" to "Android",
        "Apple" to "iOS",
        "Microsoft" to "Windows",
        "Amazon" to "AmazonWeb"
    )

}