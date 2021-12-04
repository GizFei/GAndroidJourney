package coder.giz.android.architecture.components.uilayer.databinding.helper

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import coder.giz.android.architecture.helper.logDatabinding
import coder.giz.android.yfui.seekbar.LabelSeekBar

/**
 * 双向绑定相关的绑定适配器。
 *
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
    logDatabinding { "LabelSeekBar getProgress ${labelSeekBar.getProgress()}" }
    return labelSeekBar.getProgress()
}

@BindingAdapter("progressAttrChanged")
fun setProgressListener(labelSeekBar: LabelSeekBar, attrChanged: InverseBindingListener) {
    labelSeekBar.setOnProgressChangedListener({ _, _, fromUser ->
        if (fromUser) {
            attrChanged.onChange()
        }
    })
}