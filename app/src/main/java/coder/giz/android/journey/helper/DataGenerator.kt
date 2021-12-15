package coder.giz.android.journey.helper

import coder.giz.android.architecture.AppArchitectureMainActivity
import coder.giz.android.journey.bottomsheet_demo.BottomSheetDemoActivity
import coder.giz.android.journey.main.MainNavItemData
import coder.giz.android.journey.yfui_demo.button.YFUiButtonDemoActivity
import coder.giz.android.journey.yfui_demo.button.YFUiButtonDraftActivity
import coder.giz.android.journey.yfui_demo.dialog.BottomPopupDialogFragmentDemoActivity
import coder.giz.android.network.NetworkMainActivity

/**
 * Created by GizFei on 2021/10/1
 */
object DataGenerator {

    val MainNavItemDataList = listOf(
        MainNavItemData(
            "BottomSheet",
            "BottomSheet测试",
            BottomSheetDemoActivity::class
        ),
        MainNavItemData(
            "BottomPopupDialogFragment",
            "底部弹出的DialogFragment测试",
            BottomPopupDialogFragmentDemoActivity::class
        ),
        MainNavItemData(
            "YFUi Button Draft",
            "YFUi Button控件草稿页面",
            YFUiButtonDraftActivity::class
        ),
        MainNavItemData(
            "YFUi Button",
            "YFUi Button控件测试",
            YFUiButtonDemoActivity::class
        ),
        MainNavItemData(
            "App Architecture",
            "App Architecture学习",
            AppArchitectureMainActivity::class
        ),
        MainNavItemData(
            "Network",
            "Network练习",
            NetworkMainActivity::class
        )
    )

}