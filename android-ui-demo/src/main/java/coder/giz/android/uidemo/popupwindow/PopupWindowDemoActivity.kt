package coder.giz.android.uidemo.popupwindow

import android.annotation.SuppressLint
import android.transition.Slide
import android.view.Gravity
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityPopupWindowDemoBinding
import coder.giz.android.uidemo.databinding.LayoutPopupWindowContentBinding
import coder.giz.android.uidemo.databinding.LayoutPopupWindowContentWithArrowBinding
import coder.giz.android.uidemo.helper.DataGenerator
import coder.giz.android.uidemo.helper.GravityItemSpinnerEntries
import coder.giz.android.uidemo.popupwindow.helper.EnumPopupWindowAnimationStyle
import coder.giz.android.uidemo.popupwindow.helper.EnumPopupWindowTransition
import coder.giz.android.yfui.base.MvvmBaseActivity
import coder.giz.android.yfui.recyclerview.adapter.SimpleListItemOneAdapter
import com.google.android.material.transition.platform.MaterialFade

class PopupWindowDemoActivity : MvvmBaseActivity<ActivityPopupWindowDemoBinding, PopupWindowDemoViewModel>() {

    private var mLocationAdjustablePopupWindow: PopupWindow? = null
    private var mStatusOtherApiPopupWindow: PopupWindow? = null

    override fun getLayoutId(): Int = R.layout.activity_popup_window_demo

    override fun getViewModel() = PopupWindowDemoViewModel()

    override fun initDataBinding() {
        super.initDataBinding()
        mBinding.viewModel = mViewModel
    }

    override fun initView() {
        supportActionBar?.title = "PopupWindow Demo"
        mBinding.btnShowPopupWindowLeft.setOnClickListener { showPopupWindowAsDropdownLeft() }
        mBinding.btnShowPopupWindowRight.setOnClickListener { showPopupWindowAsDropdownRight() }
        mBinding.btnShowPopupWindowWithArrow.setOnClickListener { showPopupWindowWithArrow() }
        mBinding.btnShowPopupWindowAtLocation.setOnClickListener { showPopupWindowAtLocation() }
        mBinding.btnShowPopupWindowLocationAdjustable.setOnClickListener { showPopupWindowLocationAdjustable() }
        mBinding.btnShowPopupWindowStatusOtherApi.setOnClickListener { showPopupWindowStatusOtherApi() }
        mBinding.btnDismissPopupWindowStatusOtherApi.setOnClickListener { mStatusOtherApiPopupWindow?.dismiss() }
        mBinding.btnShowPopupWindowAnimation.setOnClickListener { showPopupWindowUsingAnimation() }
        mBinding.btnShowPopupWindowTransition.setOnClickListener { showPopupWindowUsingTransition() }

        mBinding.sbLocationX.setMaxProgress(windowManager.defaultDisplay.width)
        mBinding.sbLocationY.setMaxProgress(windowManager.defaultDisplay.height)
        mBinding.sbLocationX.setOnProgressChangedListener({ _, progress, fromUser ->
            if (fromUser) {
                mViewModel.atLocationX = progress
                updatePopupWindowLocation()
            }
        }, false)
        mBinding.sbLocationY.setOnProgressChangedListener({ _, progress, fromUser ->
            if (fromUser) {
                mViewModel.atLocationY = progress
                updatePopupWindowLocation()
            }
        }, false)
    }

    override fun subscribeToViewModel() {

    }

    /**
     * ???????????? Popup Window???
     *
     * @param showClose ????????????????????????
     */
    private fun createPopupWindow(showClose: Boolean = false): PopupWindow {
        val popupViewBinding: LayoutPopupWindowContentBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_popup_window_content,
            null,
            false
        )
        val popupWindow = PopupWindow(
            popupViewBinding.root,                  // ????????????
            ViewGroup.LayoutParams.WRAP_CONTENT,    // ??????????????????contentView????????????
            ViewGroup.LayoutParams.WRAP_CONTENT,    // ??????????????????contentView????????????
        )
        popupViewBinding.run {
            rvItems.adapter = SimpleListItemOneAdapter().apply {
                updateData(DataGenerator.OperatorSystemList)
            }
            if (showClose) {
                ivClose.isVisible = true
                ivClose.setOnClickListener { popupWindow.dismiss() }
            }
        }
        return popupWindow
    }

    /**
     * ????????????????????????????????? Popup Window???
     *
     * @param showClose ????????????????????????
     */
    private fun createPopupWindowWithArrow(showClose: Boolean = false): PopupWindow {
        val popupViewBinding: LayoutPopupWindowContentWithArrowBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_popup_window_content_with_arrow,
            null,
            false
        )
        val popupWindow = PopupWindow(
            popupViewBinding.root,                  // ????????????
            ViewGroup.LayoutParams.WRAP_CONTENT,    // ??????????????????contentView????????????
            ViewGroup.LayoutParams.WRAP_CONTENT,    // ??????????????????contentView????????????
        )
        popupViewBinding.run {
            rvItems.adapter = SimpleListItemOneAdapter().apply {
                updateData(DataGenerator.OperatorSystemList)
            }
            if (showClose) {
                ivClose.isVisible = true
                ivClose.setOnClickListener { popupWindow.dismiss() }
            }
        }
        return popupWindow
    }

    private fun showPopupWindowAsDropdownLeft() {
        val popupWindow = createPopupWindow()
        popupWindow.run {
            isOutsideTouchable = true       // ?????????????????????
            isFocusable = true              // ???????????????
            elevation = 32f                 // ???????????????PopupWindow??????????????????
            // setBackgroundDrawable(getDrawable(R.drawable.bg_round_corner_outline))  // ??????
        }
        popupWindow.showAsDropDown(mBinding.btnShowPopupWindowLeft)
    }

    @SuppressLint("RtlHardcoded")
    private fun showPopupWindowAsDropdownRight() {
        val popupWindow = createPopupWindow()
        popupWindow.run {
            isOutsideTouchable = true
            isFocusable = true
            elevation = 32f
        }
        // PopupWindow?????????????????????????????????
        popupWindow.showAsDropDown(mBinding.btnShowPopupWindowRight, -200, 0, Gravity.RIGHT)
    }

    private fun showPopupWindowAtLocation() {
        val gravity = GravityItemSpinnerEntries.getOrNull(mViewModel.atLocationGravity.get())?.gravity
            ?: Gravity.NO_GRAVITY
        val popupWindow = createPopupWindow()
        popupWindow.run {
            isOutsideTouchable = true
            isFocusable = true
            elevation = 32f
            enterTransition = Slide(Gravity.TOP)
            exitTransition = Slide(Gravity.TOP)
            showAtLocation(mBinding.btnShowPopupWindowAtLocation, gravity, mViewModel.atLocationX,
                mViewModel.atLocationY)
        }
    }

    private fun showPopupWindowLocationAdjustable() {
        val gravity = GravityItemSpinnerEntries.getOrNull(mViewModel.atLocationGravity.get())?.gravity
            ?: Gravity.NO_GRAVITY
        mLocationAdjustablePopupWindow = mLocationAdjustablePopupWindow ?: createPopupWindow(true)
        mLocationAdjustablePopupWindow?.run {
            elevation = 32f
            enterTransition = Slide(Gravity.TOP)
            exitTransition = Slide(Gravity.TOP)
            showAtLocation(mBinding.btnShowPopupWindowAtLocation, gravity, mViewModel.atLocationX,
                mViewModel.atLocationY)
            setOnDismissListener { mLocationAdjustablePopupWindow = null }
        }
    }

    private fun updatePopupWindowLocation() {
        mLocationAdjustablePopupWindow?.run {
            update(mViewModel.atLocationX, mViewModel.atLocationY, -1, -1)
        }
    }

    private fun showPopupWindowStatusOtherApi() {
        mStatusOtherApiPopupWindow = mStatusOtherApiPopupWindow ?: createPopupWindow(true)
        mStatusOtherApiPopupWindow?.run {
            isFocusable = mViewModel.isFocusable.get()
            isOutsideTouchable = mViewModel.isOutsideTouchable.get()

            // ?????? false????????????????????????????????????????????????????????????????????????
            isTouchable = mViewModel.isTouchable.get()
            // ??????true????????????PopupWindow?????????????????????????????????????????????????????????????????????????????????????????????
            overlapAnchor = mViewModel.isOverlapAnchor.get()
            // ??????????????????PopupWindow??????????????????
            isClippingEnabled = mViewModel.isClippingEnabled.get()

            elevation = 32f
            showAsDropDown(mBinding.btnShowPopupWindowStatusOtherApi, 0, -1000)
            setOnDismissListener { mStatusOtherApiPopupWindow = null }
        }
    }

    private fun showPopupWindowUsingAnimation() {
        val gravity = GravityItemSpinnerEntries[mViewModel.atLocationGravity.get()].gravity
        val popupWindow = createPopupWindow(false)
        popupWindow.run {
            isFocusable = true
            elevation = 32f
            animationStyle = when (PopupWindowDemoViewModel.AnimationStyles[mViewModel.animationStyleIndex.get()]) {
                EnumPopupWindowAnimationStyle.COMPLEX -> R.style.PopupWindowAnimation_Complex
                EnumPopupWindowAnimationStyle.SLIDE -> R.style.PopupWindowAnimation_Slide
            }
            showAtLocation(mBinding.btnShowPopupWindowAnimation, gravity, mViewModel.atLocationX, mViewModel.atLocationY)
        }
    }

    private fun showPopupWindowUsingTransition() {
        val gravity = GravityItemSpinnerEntries[mViewModel.atLocationGravity.get()].gravity
        val popupWindow = createPopupWindow(false)
        popupWindow.run {
            isFocusable = true
            elevation = 32f
            when (PopupWindowDemoViewModel.Transitions[mViewModel.transitionIndex.get()]) {
                EnumPopupWindowTransition.SLIDE_TOP_TO_BOTTOM -> {
                    enterTransition = Slide(Gravity.TOP)
                    exitTransition = Slide(Gravity.BOTTOM)
                }
                EnumPopupWindowTransition.SLIDE_START_TO_END -> {
                    enterTransition = Slide(Gravity.START)
                    exitTransition = Slide(Gravity.END)
                }
                EnumPopupWindowTransition.MATERIAL_FADE -> {
                    enterTransition = MaterialFade()
                    exitTransition = MaterialFade()
                }
            }
            showAtLocation(mBinding.btnShowPopupWindowTransition, gravity, 0, 0)
        }
    }

    private fun showPopupWindowWithArrow() {
        val popupWindow = createPopupWindowWithArrow(showClose = true)
        popupWindow.run {
            isFocusable = true
            enterTransition = MaterialFade()
            exitTransition = MaterialFade()
            overlapAnchor = true
            showAsDropDown(mBinding.btnShowPopupWindowWithArrow, 0, 64)
        }
    }

}