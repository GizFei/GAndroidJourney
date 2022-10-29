package coder.giz.publicapis.helper

import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem
import coder.giz.publicapis.currents.view.CurrentsNewsActivity

/**
 * Created by GizFei on 2022/10/29
 */
internal object DataGenerator {
    val PublicApisAppNavItems = listOf(
        HeaderNavItem("Currents"),
        ActivityNavItem(
            "Currents",
            "最近新闻Api",
            CurrentsNewsActivity::class
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