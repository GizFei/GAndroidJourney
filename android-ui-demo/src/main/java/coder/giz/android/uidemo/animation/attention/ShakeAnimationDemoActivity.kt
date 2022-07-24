package coder.giz.android.uidemo.animation.attention

import android.view.animation.AccelerateDecelerateInterpolator
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityShakeAnimationDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity

/**
 * Created by GizFei on 2022/7/24
 */
class ShakeAnimationDemoActivity : DataBindingBaseActivity<ActivityShakeAnimationDemoBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_shake_animation_demo

    override fun initView() {
        val shakeVerticalAnimation = ShakeAnimation(mBinding.viewShakeVertical).apply {
            setTranslationDistanceInDp(32f)
            setDirection(ShakeAnimation.Direction.VERTICAL_BOTTOM)
            setSplitStep(true)
            createAnimator {
                duration = 1000L
                interpolator = AccelerateDecelerateInterpolator()
            }
        }
        mBinding.btnStartShakeVertical.setOnClickListener {
            shakeVerticalAnimation.startAnim()
        }
        mBinding.btnStopShakeVertical.setOnClickListener {
            shakeVerticalAnimation.stopAnim()
        }

        ShakeAnimationHelper.createShakeVerticalTopAnimation(mBinding.viewShakeVerticalTop)
            .startAnim()
        ShakeAnimationHelper.createShakeVerticalBottomAnimation(mBinding.viewShakeVerticalBottom)
            .startAnim()
        ShakeAnimationHelper.createShakeHorizontalLeftAnimation(mBinding.viewShakeHorizontalLeft)
            .startAnim()
        ShakeAnimationHelper.createShakeHorizontalRightAnimation(mBinding.viewShakeHorizontalRight)
            .startAnim()
    }
}