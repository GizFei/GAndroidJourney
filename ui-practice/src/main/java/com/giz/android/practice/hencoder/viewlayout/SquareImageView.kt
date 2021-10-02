package com.giz.android.practice.hencoder.viewlayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * Description of the file
 *
 * Created by GizFei on 2021/4/12
 */
class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val actualWidth = min(measuredWidth, measuredHeight)
        setMeasuredDimension(actualWidth, actualWidth)
    }

}