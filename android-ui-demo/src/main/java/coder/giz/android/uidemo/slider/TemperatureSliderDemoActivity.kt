package coder.giz.android.uidemo.slider

import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityTemperatureSliderDemoBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity

/**
 * Created by GizFei on 2022/1/14
 */
class TemperatureSliderDemoActivity :
    DataBindingBaseActivity<ActivityTemperatureSliderDemoBinding>(),
    TemperatureSlider.OnSliderChangeListener {

    override fun getLayoutId(): Int = R.layout.activity_temperature_slider_demo

    override fun initView() {
        mBinding.tempSlider.run {
            updateProgressRange(-5, 35)
            setOnSliderChangeListener(this@TemperatureSliderDemoActivity)
        }
        mBinding.btnScale.setOnClickListener {
            mBinding.tempSlider.pivotY = mBinding.tempSlider.height.toFloat()
            mBinding.tempSlider.scaleY = 1.2f
            mBinding.tempSlider.translationY = 60f
        }
    }

    override fun onProgressChange(progress: Int, value: Int) {
        mBinding.tvProgress.text = "$value"
    }

    override fun onStopSlidingTouch(progress: Int, value: Int) {

    }
}