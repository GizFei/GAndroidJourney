package coder.giz.android.journey.bottomsheet.practice

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.updatePadding
import coder.giz.android.bottomsheet.BaseBottomSheetFragment
import coder.giz.android.bottomsheet.util.BottomSheetOptions
import coder.giz.android.bottomsheet.util.logBottomSheet
import coder.giz.android.journey.R
import coder.giz.android.journey.databinding.LayoutFullFeaturedBottomSheetWrapContentBinding

/**
 * Description of the file
 *
 * Created by GizFei on 2021/7/5
 */
class FullFeaturedBottomSheetPreviewFragment :
    BaseBottomSheetFragment<LayoutFullFeaturedBottomSheetWrapContentBinding>() {

    companion object {
        const val TAG = "FullFeaturedBottomSheetPreviewDialog"

        fun newInstance(options: BottomSheetOptions = BottomSheetOptions()) = FullFeaturedBottomSheetPreviewFragment().apply {
            arguments = options.toBundle()
        }
    }

    private var mOptions = BottomSheetOptions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extractOptions()
    }

    private fun extractOptions() {
        mOptions = BottomSheetOptions.resolveBundle(arguments)
        logBottomSheet { "extractOptions: $mOptions" }
    }

    override fun getLayoutId(): Int = R.layout.layout_full_featured_bottom_sheet_wrap_content

    @SuppressLint("SetTextI18n")
    override fun initView() {
        mBinding.btnLogIn.setOnClickListener { dismissAllowingStateLoss() }
        mBinding.btnLogIn.text = "dismiss"
    }

    override fun getOptions(): BottomSheetOptions = mOptions

    override fun customizeWrapperView(wrapperView: FrameLayout?) {
        wrapperView?.run {
            // 如果内容布局的背景不是白色，那么可以给包裹视图添加上边距，防止圆角被盖住
            updatePadding(top = 16.dp)
            // 保持圆角，修改包裹视图的背景颜色：
            backgroundTintList = ColorStateList.valueOf(Color.CYAN)
        }
    }

}