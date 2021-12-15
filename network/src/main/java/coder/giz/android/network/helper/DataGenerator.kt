package coder.giz.android.network.helper

import coder.giz.android.network.ui.BehaviorSubjectPracticeActivity
import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem

/**
 * Created by GizFei on 2021/12/12
 */
internal object DataGenerator {
    val NetworkNavItems = listOf(
        HeaderNavItem("Subject"),
        ActivityNavItem(
            "BehaviorSubject",
            "BehaviorSubject练习",
            BehaviorSubjectPracticeActivity::class
        ),
    )
}
