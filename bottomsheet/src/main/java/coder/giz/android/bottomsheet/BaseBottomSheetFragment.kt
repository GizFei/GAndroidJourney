package coder.giz.android.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import coder.giz.android.bottomsheet.util.BottomSheetOptions
import coder.giz.android.bottomsheet.util.logBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * 既可以充当`Dialog`、也可以充当`Fragment`的`BottomSheet`。
 *
 * ## 重点笔记：
 * ### 1、以显示`Dialog`的方式使用
 * ```
 * val bottomSheetFragment = BottomSheetFragment.newInstance()
 * bottomSheetFragment.show(supportFragmentManager, "TAG")
 * ```
 * 此时，[BottomSheetDialogFragment.mContainerId] `== 0`，
 * 在 [onCreate]中赋值[BottomSheetDialogFragment.mShowsDialog] `= true`，
 * 所以走以下生命周期：
 * [onCreateDialog] -> [onCreateView] -> [onViewCreated] -> [onActivityCreated]
 *
 * ### 2、以添加`Fragment`的方式使用
 * ```
 * supportFragmentManager
 *      .beginTransaction()
 *      .add(R.id.fragment_container, getFullFeaturedPreviewFragment())
 *      .commitAllowingStateLoss()
 * ```
 * 此时，[BottomSheetDialogFragment.mContainerId] `== R.id.fragment_container`，
 * 在[onCreate]中赋值[BottomSheetDialogFragment.mShowsDialog] `= false`
 * 所以走以下生命周期：
 * [onCreateView] -> [onViewCreated] -> [onActivityCreated]
 *
 * Created by GizFei on 2021/7/5
 */
abstract class BaseBottomSheetFragment<VDB: ViewDataBinding> :
    BottomSheetDialogFragment(),
    BottomSheet {

    private var _binding: VDB? = null
    protected val mBinding: VDB get() = _binding!!

    /**
     * - 作`Dialog`使用，不为空
     * - 作`Fragment`使用，为空
     */
    protected var mBehavior: BottomSheetBehavior<FrameLayout>? = null
    protected var mWrapperView: FrameLayout? = null

    private var mOptions: BottomSheetOptions? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOptions = BottomSheetOptions.resolveBundle(arguments)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        logBottomSheet { "onCreateDialog" }
        return BottomSheetDialog(requireContext(), theme).apply {
            mBehavior = behavior
        }
    }

    override fun getTheme(): Int {
        val defaultTheme = R.style.YFBottomSheet
        val options = getOptions() ?: return defaultTheme

        return when {
            options.noDim && options.topRoundCorner -> R.style.YFBottomSheet_NoDim_TopRoundCorner
            options.noDim -> R.style.YFBottomSheet_NoDim
            options.topRoundCorner -> R.style.YFBottomSheet_TopRoundCorner
            else -> defaultTheme
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logBottomSheet { "onCreateView" }
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logBottomSheet { "onViewCreated" }
        mBinding.lifecycleOwner = viewLifecycleOwner
        initView()
    }

    /**
     * 紧跟在 [onViewCreated] 后面调用。
     *
     * 如果 [mShowsDialog] `== true`，此时已将 [onCreateView] 中创建的视图通过 [Dialog.setContentView] 方法传入`Dialog`。
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // 在父类的 onActivityCreate 方法中有 Dialog.setContentView 调用
        super.onActivityCreated(savedInstanceState)

        logBottomSheet { "onActivityCreated: mShowsDialog: $showsDialog" }
        ensureWrapperView()
        ensureBehavior()

        customizeByOptions(getOptions())
        mBehavior?.let(this::customBehavior)
        customizeWrapperView(mWrapperView)
    }

    private fun ensureWrapperView() {
        if (mWrapperView != null) {
            return
        }

        mWrapperView = if (showsDialog) {
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        } else {
            // 非Dialog时，不是com.google.android.material.R.id.design_bottom_sheet对应的FrameLayout
            // 而是Fragment容器视图（通常是FrameLayout）
            view?.parent as? FrameLayout
        }
    }

    private fun ensureBehavior() {
        if (mBehavior == null && showsDialog) {
            mBehavior = (dialog as? BottomSheetDialog)?.behavior
        }
    }

    private fun customizeByOptions(options: BottomSheetOptions?) {
        options ?: return

        isCancelable = options.cancelable
        mBehavior?.run {
            isHideable = options.hideable
            skipCollapsed = options.skipCollapsed
        }

        if (options.topSpacedPixels != 0) {
            makeTopSpaced(options.topSpacedPixels)
        } else if (options.fullscreen) {
            matchFullscreen()
        }
    }

    private fun makeTopSpaced(spacedPx: Int) {
        val screenHeight = resources.displayMetrics.heightPixels
        val topSpacedHeight = screenHeight - spacedPx

        if (showsDialog) {
            makeTopSpacedAsDialog(topSpacedHeight)
        } else {
            makeTopSpacedAsFragment(topSpacedHeight)
        }
    }

    private fun makeTopSpacedAsDialog(topSpacedHeight: Int) {
        mWrapperView?.layoutParams?.height = topSpacedHeight
        mBehavior?.run {
            peekHeight = topSpacedHeight
            isHideable = false
        }
    }

    private fun makeTopSpacedAsFragment(topSpacedHeight: Int) {
        view?.layoutParams?.height = topSpacedHeight
    }

    private fun matchFullscreen() {
        if (showsDialog) {
            matchFullscreenAsDialog()
        } else {
            matchFullscreenAsFragment()
        }
    }

    private fun matchFullscreenAsDialog() {
        mWrapperView?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        mBehavior?.run {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun matchFullscreenAsFragment() {
        view?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getBehavior(): BottomSheetBehavior<FrameLayout>? = mBehavior

    override fun getWrapperView(): FrameLayout? = mWrapperView

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()

    protected val Int.dp: Int get() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()

    open fun customizeWrapperView(wrapperView: FrameLayout?) {}

    open fun customBehavior(behavior: BottomSheetBehavior<FrameLayout>) {}

    open fun getOptions(): BottomSheetOptions? = mOptions

    companion object {
        fun <T : BaseBottomSheetFragment<*>> T.withOptions(options: BottomSheetOptions): T {
            this.arguments = (arguments ?: Bundle()).apply {
                putAll(options.toBundle())
            }
            return this
        }
    }

}