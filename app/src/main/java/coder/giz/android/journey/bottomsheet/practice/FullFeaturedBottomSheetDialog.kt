package coder.giz.android.journey.bottomsheet.practice

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import coder.giz.android.bottomsheet.util.BottomSheetFeature
import coder.giz.android.bottomsheet.util.BottomSheetFeatureImpl
import coder.giz.android.bottomsheet.util.BottomSheetOptions
import coder.giz.android.bottomsheet.util.logBottomSheet
import coder.giz.android.journey.R
import coder.giz.android.journey.databinding.LayoutFullFeaturedBottomSheetBinding
import coder.giz.android.yfutility.util.logLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * 包含全部特性的BottomSheetDialog
 *
 * Created by GizFei on 2021/7/2
 */
class FullFeaturedBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "FullFeaturedBottomSheetDialog"

        fun newInstance(options: BottomSheetOptions = BottomSheetOptions()) = FullFeaturedBottomSheetDialog().apply {
            arguments = options.toBundle()
        }
    }

    private var mOptions = BottomSheetOptions()
    private lateinit var mBinding: LayoutFullFeaturedBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extractOptions()
    }

    private fun extractOptions() {
        mOptions = BottomSheetOptions.resolveBundle(arguments)
        logBottomSheet { "extractOptions: $mOptions" }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = LayoutFullFeaturedBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
        initView()

        return BottomSheetDialog(requireContext(), theme).apply {
            setContentView(mBinding.root)

            addCallback(this)
//            makeFullscreen(this)
//            setHideableFalse(this)
//            nonCancelable(this)
            disableDrag(this)
//            makeTopSpaced(this)
        }
    }

    private fun initView() {
        mBinding.btnLogIn.setOnClickListener { dismissAllowingStateLoss() }
    }

    private fun addCallback(dialog: BottomSheetDialog) {
        dialog.behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                logBottomSheet { "BottomSheetBehavior onStateChanged: ${convertState(newState)}" }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun convertState(state: Int): String {
        return when (state) {
            1 -> "STATE_DRAGGING"
            2 -> "STATE_SETTLING"
            3 -> "STATE_EXPANDED"
            4 -> "STATE_COLLAPSED"
            5 -> "STATE_HIDDEN"
            6 -> "STATE_HALF_EXPANDED"
            else -> "Unknown State"
        }
    }

    /**
     * 使 `BottomSheet` 占满屏幕高度。
     */
    @BottomSheetFeatureImpl(BottomSheetFeature.Fullscreen::class)
    private fun matchFullscreen(dialog: BottomSheetDialog) {
        findBottomSheetWrapperView(dialog)?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.behavior.skipCollapsed = true
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    /**
     * 禁止取消：
     * 无法通过“点击遮罩”或“返回键”关闭 `BottomSheet`，需要通过代码调用 `dismiss()`
     */
    @BottomSheetFeatureImpl(BottomSheetFeature.NonCancelable::class)
    private fun nonCancelable(dialog: BottomSheetDialog) {
        // 使用的是DialogFragment的cancelable
        isCancelable = false
    }

    /**
     * 不可隐藏。
     * 即 `BottomSheet` 只能在 `STATE_COLLAPSED`、`STATE_EXPANDED` 之间滑动，不能滑到底部来隐藏（即关闭）。
     * 只能通过点击遮罩或代码调用 `dismiss()` 来关闭。
     *
     * `isHideable = true` 时，可以滑动 `BottomSheet` 到底部关闭。
     * 关闭 `BottomSheet`，即会调用onDestroyView -> onDestroy -> onDetach流程。
     */
    @BottomSheetFeatureImpl(BottomSheetFeature.NonHideable::class)
    private fun setHideableFalse(dialog: BottomSheetDialog) {
        dialog.behavior.isHideable =  false
    }

    override fun onDestroy() {
        super.onDestroy()
        logLifecycle { "onDestroy" }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logLifecycle { "onDestroyView" }
    }

    override fun onDetach() {
        super.onDetach()
        logLifecycle { "onDetach" }
    }

    /**
     * 用过设置 `isHideable = false`，禁止用户滑动至底部关闭 `BottomSheet`，相当于禁止滑动。
     * 但是允许在 `STATE_EXPANDED` 和 `STATE_COLLAPSED` 两种状态之间滑动
     */
    @BottomSheetFeatureImpl(BottomSheetFeature.DisableDrag::class)
    private fun disableDrag(dialog: BottomSheetDialog) {
        dialog.behavior.isHideable = false
    }

    /**
     * 占满全屏，且顶部留出一定空间。
     * 关键点：
     * 1、wrapperView高度 == peekHeight
     * 2、禁止隐藏：`isHideable = false`
     */
    @BottomSheetFeatureImpl(BottomSheetFeature.TopSpaced::class)
    private fun makeTopSpaced(dialog: BottomSheetDialog) {
        // 获取屏幕高度
        val screenHeight = resources.displayMetrics.heightPixels
        val topSpaced = 64.dp
        val topSpacedHeight = screenHeight - topSpaced
        findBottomSheetWrapperView(dialog)?.layoutParams?.height = topSpacedHeight
        dialog.behavior.peekHeight = topSpacedHeight
        dialog.behavior.isHideable = false
    }

    /**
     * 该方法中的参数都是 [BottomSheetBehavior] 默认值
     */
    private fun customizeBehaviorFitToContents(behavior: BottomSheetBehavior<FrameLayout>) {
        behavior.apply {
            this.isFitToContents = true // 适应内容布局的大小
            this.peekHeight = 800       // BottomSheet处于STATE_EXPANDED时的露出高度
            this.skipCollapsed = false  // 是否跳过STATE_COLLAPSED这个状态。仅当hideable=true时有效。
            this.isHideable = true      // 是否可以隐藏（滑到最底部，然后消失）
            // this.state = BottomSheetBehavior.STATE_XXX 指定BottomSheet的状态
        }
    }

    @Deprecated("非常难用")
    private fun customizeBehaviorNotFitToContents(behavior: BottomSheetBehavior<FrameLayout>) {
        behavior.apply {
            this.isFitToContents = false
            this.setExpandedOffset(400)
        }
    }

    private fun findBottomSheetWrapperView(dialog: BottomSheetDialog): View? {
        return dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.also {
            logBottomSheet { "findBottomSheetWrapperView: ${it::class.simpleName}" }
        }
    }

    /**
     * 获取 `BottomSheet` 的主题
     * 实现“去掉背景遮罩”、“顶部圆角”特性
     */
    override fun getTheme(): Int {
        return when {
            mOptions.noDim && mOptions.topRoundCorner -> {
                @BottomSheetFeatureImpl(BottomSheetFeature.NoDim::class, BottomSheetFeature.TopRoundCorner::class)
                R.style.YFBottomSheet_NoDim_TopRoundCorner
            }
            mOptions.noDim -> {
                @BottomSheetFeatureImpl(BottomSheetFeature.NoDim::class)
                R.style.YFBottomSheet_NoDim
            }
            mOptions.topRoundCorner -> {
                @BottomSheetFeatureImpl(BottomSheetFeature.TopRoundCorner::class)
                R.style.YFBottomSheet_TopRoundCorner
            }
            else -> {
                R.style.YFBottomSheet
            }
        }
    }

    private val Int.dp: Int get() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()

}