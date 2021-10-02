package com.giz.android.practice.hencoder.viewlayout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Description of the file
 *
 * Created by GizFei on 2021/4/13
 */
class ParentViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

//        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec)
    }

}