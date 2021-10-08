package coder.giz.android.journey.bottomsheet_demo

import android.util.TypedValue
import coder.giz.android.bottomsheet.util.BottomSheetOptions
import coder.giz.android.journey.R
import coder.giz.android.journey.bottomsheet_demo.practice.FullFeaturedBottomSheetDialog
import coder.giz.android.journey.bottomsheet_demo.practice.FullFeaturedBottomSheetPreviewDialog
import coder.giz.android.journey.bottomsheet_demo.practice.FullFeaturedBottomSheetPreviewFragment
import coder.giz.android.journey.databinding.ActivityBottomSheetDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity

/**
 * BottomSheet 样例演示页面。包含；
 * · 作为 Dialog 的 BottomSheet
 * · 作为 Fragment 的 BottomSheet
 */
class BottomSheetDemoActivity : DataBindingBaseActivity<ActivityBottomSheetDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_bottom_sheet_demo

    override fun initView() {
        mBinding.btnFullFeaturedDialog.setOnClickListener {
            showFullFeaturedBottomSheetDialog()
        }
        mBinding.btnFullscreen.setOnClickListener {
            showFullscreenFeature()
        }
        mBinding.btnTopSpaced64.setOnClickListener {
            showTopSpacedFeature(64.dp)
        }
        mBinding.btnTopSpaced200.setOnClickListener {
            showTopSpacedFeature(200.dp)
        }
        mBinding.btnNoDim.setOnClickListener {
            showNoDimFeature()
        }

        mBinding.btnFullFeaturedFragment.setOnClickListener {
            showFullFeaturedBottomSheetFragment()
        }
        mBinding.btnFullscreenFragment.setOnClickListener {
            showFullscreenFeatureFragment()
        }
        mBinding.btnTopSpaced64Fragment.setOnClickListener {
            showTopSpacedFeatureFragment(64.dp)
        }
        mBinding.btnTopSpaced200Fragment.setOnClickListener {
            showTopSpacedFeatureFragment(200.dp)
        }
        mBinding.btnNoDimFragment.setOnClickListener {
            showNoDimFeatureFragment()
        }

        mBinding.btnPreviewFragment.setOnClickListener {
            gotoPreviewFragmentActivity()
        }
    }

    private fun showFullFeaturedBottomSheetDialog() {
        if (supportFragmentManager.findFragmentByTag(FullFeaturedBottomSheetDialog.TAG) != null) {
            return
        }

        val fullFeaturedBottomSheetDialog = FullFeaturedBottomSheetDialog.newInstance(
            BottomSheetOptions()
                .noDim(false)
                .topRoundCorner()
        )
        fullFeaturedBottomSheetDialog.show(supportFragmentManager, FullFeaturedBottomSheetDialog.TAG)
    }

    private fun BottomSheetOptions.mergeCommonOptions() = apply {
        hideable(mBinding.switchHideable.isChecked)
        cancelable(mBinding.switchCancelable.isChecked)
    }

    private fun showFullscreenFeature() {
        showFullFeaturedBottomSheetPreviewDialog(
            BottomSheetOptions()
                .fullscreen(true)
                .mergeCommonOptions()
        )
    }

    private fun showTopSpacedFeature(px: Int) {
        showFullFeaturedBottomSheetPreviewDialog(
            BottomSheetOptions()
                .topSpacedPixels(px)
                .mergeCommonOptions()
        )
    }

    private fun showNoDimFeature() {
        showFullFeaturedBottomSheetPreviewDialog(
            BottomSheetOptions()
                .noDim(true)
                .mergeCommonOptions()
        )
    }

    private fun showFullFeaturedBottomSheetPreviewDialog(options: BottomSheetOptions) {
        FullFeaturedBottomSheetPreviewDialog.newInstance(options)
            .show(supportFragmentManager, null)
    }

    private fun showFullFeaturedBottomSheetFragment() {
        val fullFeaturedFragment = FullFeaturedBottomSheetPreviewFragment.newInstance()
        fullFeaturedFragment.show(supportFragmentManager, null)
    }

    private fun showFullscreenFeatureFragment() {
        showFullFeaturedBottomSheetPreviewFragment(
            BottomSheetOptions()
                .fullscreen()
                .mergeCommonOptions()
        )
    }

    private fun showTopSpacedFeatureFragment(px: Int) {
        showFullFeaturedBottomSheetPreviewFragment(
            BottomSheetOptions()
                .topSpacedPixels(px)
                .mergeCommonOptions()
        )
    }

    private fun showNoDimFeatureFragment() {
        showFullFeaturedBottomSheetPreviewFragment(
            BottomSheetOptions()
                .noDim()
                .mergeCommonOptions()
        )
    }

    private fun showFullFeaturedBottomSheetPreviewFragment(options: BottomSheetOptions) {
        FullFeaturedBottomSheetPreviewFragment.newInstance(options)
            .show(supportFragmentManager, null)
    }

    private fun gotoPreviewFragmentActivity() {
        BottomSheetFragmentDemoActivity::class.navigate()
    }

    protected val Int.dp: Int get() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()

}