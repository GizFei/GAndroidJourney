package coder.giz.android.journey.helper

import coder.giz.android.journey.bottomsheet.BottomSheetDemoActivity
import coder.giz.android.journey.main.MainNavItemData
import coder.giz.android.journey.yfui.button.YFUiButtonDemoActivity
import coder.giz.android.journey.yfui.button.YFUiButtonDraftActivity
import coder.giz.android.journey.yfui.dialog.BottomPopupDialogFragmentDemoActivity

/**
 * Created by GizFei on 2021/10/1
 */
object DataGenerators {

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
    )

}