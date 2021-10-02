package coder.giz.android.bottomsheet.util

/**
 * BottomSheet特性
 *
 * Created by GizFei on 2021/6/27
 */
sealed class BottomSheetFeature(private val done: Boolean = false) {
    /* 顶部圆角 */
    object TopRoundCorner: BottomSheetFeature(done = true)

    /* 无遮罩 */
    object NoDim: BottomSheetFeature(done = true)

    /* 高度全屏 */
    object Fullscreen : BottomSheetFeature(done = true)

    /* 顶部留有一定空隙 */
    object TopSpaced: BottomSheetFeature(done = true)

    /**
     *  不可点击遮罩或返回键取消
     */
    object NonCancelable: BottomSheetFeature(done = true)

    /**
     * 不可隐藏。
     * 即 `BottomSheet` 只能在 `STATE_COLLAPSED`、`STATE_EXPANDED` 之间滑动，不能滑到底部来隐藏（即关闭）。
     * 只能通过点击遮罩或代码调用 `dismiss()` 来关闭。
     *
     * `isHideable = true` 时，可以滑动 `BottomSheet` 到底部关闭。
     * 关闭 `BottomSheet`，即会调用onDestroyView -> onDestroy -> onDetach流程。
     */
    object NonHideable: BottomSheetFeature(done = true)

    /* 禁用滑动 */
    object DisableDrag: BottomSheetFeature(done = true)
}