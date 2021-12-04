package coder.giz.android.uidemo.popupwindow.helper

/**
 * Created by GizFei on 2021/11/21
 */
enum class EnumPopupWindowAnimationStyle(
    val description: String
) {
    COMPLEX("Complex(Rotation & Alpha & Scale)"),
    SLIDE("Slide(Alpha & Translate)"),
    ;

    override fun toString(): String = description
}