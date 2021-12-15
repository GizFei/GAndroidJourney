package coder.giz.android.uidemo.playground

import android.animation.ValueAnimator
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityUiPlaygroundBinding
import coder.giz.android.uidemo.expandable.ExpandAnimation
import coder.giz.android.yfui.base.DataBindingBaseActivity

class UiPlaygroundActivity : DataBindingBaseActivity<ActivityUiPlaygroundBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_ui_playground

    override fun initView() {
        mBinding.btnExpand.setOnClickListener {
            val animation = ExpandAnimation(mBinding.cardView, 0, mBinding.cardView.height).apply {
                duration = 1600
            }
            mBinding.cardView.startAnimation(animation)
        }
        mBinding.btnCollapse.setOnClickListener {
            val animation = ExpandAnimation(mBinding.cardView, mBinding.cardView.height, 0).apply {
                duration = 1600
                fillAfter = true
            }
            mBinding.cardView.startAnimation(animation)
        }
        mBinding.tvAnimateShadow.setOnClickListener {
            ValueAnimator.ofFloat(4f, 32f).apply {
                addUpdateListener {
                    mBinding.shadowLayout.updateShadowSize(it.animatedValue as Float)
                }
                duration = 800
                start()
            }
        }
    }
}