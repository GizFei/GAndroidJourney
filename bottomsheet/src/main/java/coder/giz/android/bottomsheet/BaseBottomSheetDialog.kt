package coder.giz.android.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import coder.giz.android.bottomsheet.util.BottomSheetOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * 只能充当`Dialog`使用
 * ```
 * val bottomSheetDialog = BottomSheetDialog.newInstance()
 * bottomSheetDialog.show(supportFragmentManager, "TAG")
 * ```
 *
 * Created by GizFei on 2021/7/4
 */
abstract class BaseBottomSheetDialog<VDB: ViewDataBinding> :
    BottomSheetDialogFragment(),
    BottomSheet {

    protected lateinit var mBinding: VDB
    protected lateinit var mBehavior: BottomSheetBehavior<FrameLayout>
    protected var mWrapperView: FrameLayout? = null

    private var mOptions: BottomSheetOptions? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOptions = BottomSheetOptions.resolveBundle(arguments)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), getLayoutId(), null, false)
        initView()

        return BottomSheetDialog(requireContext(), theme).apply {
            setContentView(mBinding.root)
            mBehavior = behavior
            mWrapperView = findBottomSheetWrapperView(this)

            customizeByOptions(this, getOptions())
            customizeDialog(this)
            customizeWrapperView(mWrapperView)
            customBehavior(behavior)
        }
    }

    private fun customizeByOptions(dialog: BottomSheetDialog, options: BottomSheetOptions?) {
        options ?: return

        isCancelable = options.cancelable
        dialog.behavior.run {
            isHideable = options.hideable
            skipCollapsed = options.skipCollapsed
        }

        if (options.topSpacedPixels != 0) {
            makeTopSpaced(dialog, options.topSpacedPixels)
        } else if (options.fullscreen) {
            matchFullscreen(dialog)
        }
    }

    private fun makeTopSpaced(dialog: BottomSheetDialog, spacedPx: Int) {
        val screenHeight = resources.displayMetrics.heightPixels
        val topSpacedHeight = screenHeight - spacedPx

        findBottomSheetWrapperView(dialog)?.layoutParams?.height = topSpacedHeight
        dialog.behavior.run {
            peekHeight = topSpacedHeight
            isHideable = false
        }
    }

    private fun matchFullscreen(dialog: BottomSheetDialog) {
        findBottomSheetWrapperView(dialog)?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.behavior.run {
            state = BottomSheetBehavior.STATE_EXPANDED
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

    protected fun findBottomSheetWrapperView(dialog: BottomSheetDialog): FrameLayout? {
        return dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
    }

    override fun getBehavior(): BottomSheetBehavior<FrameLayout>? = mBehavior

    override fun getWrapperView(): FrameLayout? = mWrapperView

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()

    protected val Int.dp: Int get() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()

    open fun customizeDialog(dialog: BottomSheetDialog) {}

    open fun customizeWrapperView(wrapperView: FrameLayout?) {}

    open fun customBehavior(behavior: BottomSheetBehavior<FrameLayout>) {}

    open fun getOptions(): BottomSheetOptions? = mOptions

    companion object {
        fun <T : BaseBottomSheetDialog<*>> T.withOptions(options: BottomSheetOptions): T {
            this.arguments = (arguments ?: Bundle()).apply {
                putAll(options.toBundle())
            }
            return this
        }
    }

}