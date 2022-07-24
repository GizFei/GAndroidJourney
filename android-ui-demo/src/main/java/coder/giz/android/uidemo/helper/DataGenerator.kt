package coder.giz.android.uidemo.helper

import coder.giz.android.uidemo.animation.attention.ShakeAnimationDemoActivity
import coder.giz.android.uidemo.expandablelistview.ExpandableListViewDemoActivity
import coder.giz.android.uidemo.floatingwindow.FloatingWindowDemoActivity
import coder.giz.android.uidemo.playground.UiPlaygroundActivity
import coder.giz.android.uidemo.popupwindow.PopupWindowDemoActivity
import coder.giz.android.uidemo.recyclerview.DraggableRecyclerViewActivity
import coder.giz.android.uidemo.slider.TemperatureSliderDemoActivity
import coder.giz.android.uidemo.tablayout.ColumnTabLayoutDemoActivity
import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem

/**
 * Created by GizFei on 2021/11/14
 */
internal object DataGenerator {
    val AndroidUiDemoNavItems = listOf(
        HeaderNavItem("Widget"),
        ActivityNavItem("PopupWindow", "PopupWindow使用",
            PopupWindowDemoActivity::class),
        ActivityNavItem("ExpandableListView", "ExpandableListView使用",
            ExpandableListViewDemoActivity::class),
        HeaderNavItem("Playground"),
        ActivityNavItem("UiPlayground", "Ui练习页面",
            UiPlaygroundActivity::class),
        ActivityNavItem("ColumnTabLayout", "ColumnTabLayout练习页面",
            ColumnTabLayoutDemoActivity::class),
        ActivityNavItem("TemperatureSlider", "TemperatureSlider练习页面",
            TemperatureSliderDemoActivity::class),
        HeaderNavItem("FloatingWindow"),
        ActivityNavItem("DebugFloatingWindow", "调试悬浮窗",
            FloatingWindowDemoActivity::class),
    )

    val OperatorSystemList = listOf("Android", "iOS", "Windows", "MacOS", "Linux", "Ubuntu")
    val CompanyProductMap = mapOf(
        "Google" to "Android",
        "Apple" to "iOS",
        "Microsoft" to "Windows",
        "Amazon" to "AmazonWeb"
    )

}