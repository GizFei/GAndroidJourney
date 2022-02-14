package coder.giz.coretopics.helper

import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem
import coder.giz.coretopics.app_data_files.sharing_files.SharingFilesActivity

/**
 * Created by GizFei on 2022/2/13
 */
internal object DataGenerator {
    val CoreTopicsNavItems = listOf(
        HeaderNavItem("App Data & Files"),
        ActivityNavItem("Sharing files", "分享文件",
            SharingFilesActivity::class),
    )

    val OperatorSystemList = listOf("Android", "iOS", "Windows", "MacOS", "Linux", "Ubuntu")
    val CompanyProductMap = mapOf(
        "Google" to "Android",
        "Apple" to "iOS",
        "Microsoft" to "Windows",
        "Amazon" to "AmazonWeb"
    )

}