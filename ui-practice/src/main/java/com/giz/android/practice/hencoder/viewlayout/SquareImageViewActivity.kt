package com.giz.android.practice.hencoder.viewlayout

import android.graphics.Color
import android.widget.SeekBar
import com.giz.android.practice.R
import com.giz.android.practice.common.DataBindingBaseActivity
import com.giz.android.practice.databinding.ActivitySquareImageViewBinding

class SquareImageViewActivity : DataBindingBaseActivity<ActivitySquareImageViewBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_square_image_view

    override fun initView() {
        supportActionBar?.title = "SquareImageView"

        mBinding.sbWidth.setMaxProgress(800)
        mBinding.sbWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mBinding.flSquare.run {
                    val lp = layoutParams
                    lp.width = progress
                    layoutParams = lp
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        }, call = false)

        mBinding.sbHeight.setMaxProgress(800)
        mBinding.sbHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mBinding.flSquare.run {
                    val lp = layoutParams
                    lp.height = progress
                    layoutParams = lp
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        }, call = false)

        mBinding.colorPaintingView.apply {
            updateColorList(List(48) { Color.RED })
        }
    }
}