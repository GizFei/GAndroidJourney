package coder.giz.android.yfui.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import coder.giz.android.yfui.seekbar.LabelSeekBar

/**
 * Created by GizFei on 2021/12/4
 */

@BindingAdapter("progress")
fun setProgress(labelSeekBar: LabelSeekBar, progress: Int) {
    // ！！！避免无限循环
    if (progress != labelSeekBar.getProgress()) {
        labelSeekBar.setProgress(progress)
    }
}

@InverseBindingAdapter(attribute = "progress")
fun getProgress(labelSeekBar: LabelSeekBar): Int {
    return labelSeekBar.getProgress()
}

@BindingAdapter("progressAttrChanged")
fun setProgressAttrListener(labelSeekBar: LabelSeekBar, attrChanged: InverseBindingListener) {
    labelSeekBar.setOnProgressChangedListener({ _, _, fromUser ->
        if (fromUser) {
            attrChanged.onChange()
        }
    })
}