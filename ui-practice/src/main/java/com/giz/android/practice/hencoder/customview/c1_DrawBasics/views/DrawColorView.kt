package com.giz.android.practice.hencoder.customview.c1_DrawBasics.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/28
 */
class DrawColorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mColorPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.parseColor("#3C000000")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.parseColor("#88880000"))
        canvas.drawRect(0f, 0f, width / 2f, height.toFloat(), mColorPaint)
    }

}