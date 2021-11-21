package coder.giz.android.uidemo.popupwindow

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import coder.giz.android.uidemo.popupwindow.helper.EnumPopupWindowAnimationStyle
import coder.giz.android.uidemo.popupwindow.helper.EnumPopupWindowTransition

/**
 * Created by GizFei on 2021/11/14
 */
class PopupWindowDemoViewModel : ViewModel() {

    val atLocationGravity = ObservableInt(0)
    var atLocationX: Int = 0
    var atLocationY: Int = 0

    val isFocusable = ObservableBoolean(false)
    val isOutsideTouchable = ObservableBoolean(false)
    val isTouchable = ObservableBoolean(true)
    val isOverlapAnchor = ObservableBoolean(false)
    val isClippingEnabled = ObservableBoolean(true)

    val animationStyleIndex = ObservableInt(0)
    val transitionIndex = ObservableInt(0)

    companion object {
        @JvmField
        val AnimationStyles = EnumPopupWindowAnimationStyle.values().toList()
        @JvmField
        val Transitions = EnumPopupWindowTransition.values().toList()
    }

}