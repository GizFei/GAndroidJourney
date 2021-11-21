package coder.giz.android.uidemo.popupwindow.helper

/**
 * Created by GizFei on 2021/11/21
 */
enum class EnumPopupWindowTransition(
    val description: String
) {
    SLIDE_TOP_TO_BOTTOM("Slide_TOP_to_BOTTOM"),
    SLIDE_START_TO_END("Slide_START_to_END"),
    MATERIAL_FADE("MaterialFade")
    ;

    override fun toString(): String = description
}