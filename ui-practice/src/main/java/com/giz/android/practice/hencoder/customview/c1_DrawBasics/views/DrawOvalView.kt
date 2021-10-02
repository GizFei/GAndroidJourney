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
class DrawOvalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mOvalPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawOval(0f, 0f, 200f, 100f, mOvalPaint)

        mOvalPaint.style = Paint.Style.STROKE
        mOvalPaint.strokeWidth = 4f
        canvas.drawOval(0f, 150f, 100f, 300f, mOvalPaint)

        mOvalPaint.strokeWidth = 24f
        canvas.drawOval(0f, 350f, 100f, 500f, mOvalPaint)
    }

}