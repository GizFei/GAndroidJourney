package coder.giz.android.bottomsheet

import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * `BottomSheet`抽象接口
 *
 * Created by GizFei on 2021/7/5
 */
interface BottomSheet {

    fun getBehavior(): BottomSheetBehavior<FrameLayout>?

    fun getWrapperView(): FrameLayout?

}