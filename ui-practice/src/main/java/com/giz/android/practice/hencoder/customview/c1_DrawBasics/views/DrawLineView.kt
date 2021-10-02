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
class DrawLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mLinePaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mLinePaint.strokeWidth = 4f
        canvas.drawLine(0f, 0f, 100f, 0f, mLinePaint)

        mLinePaint.strokeWidth = 24f
        canvas.drawLines(floatArrayOf(50f, 50f, 0f, 200f, 100f, 50f, 50f, 200f), 0, 8, mLinePaint)
    }

}