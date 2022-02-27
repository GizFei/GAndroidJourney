package coder.giz.android.yfui.databinding.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/**
 * Created by GizFei on 2022/2/24
 */

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.isVisible = visible
}