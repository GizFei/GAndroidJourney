package com.giz.android.practice.hencoder.customview.c1_DrawBasics

import android.widget.SeekBar
import com.giz.android.practice.R
import com.giz.android.practice.common.DataBindingCommonFragment
import com.giz.android.practice.databinding.PageDrawbasicsDrawDynamicPathBinding

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/31
 */
class DynamicPathPageFragment : DataBindingCommonFragment<PageDrawbasicsDrawDynamicPathBinding>() {

    override fun getLayoutId(): Int = R.layout.page_drawbasics_draw_dynamic_path

    override fun initView() {
        mBinding.customSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mBinding.dynamicCircleView.updateProgress(progress.toFloat() / mBinding.customSeekBar.getMaxProgress())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

}