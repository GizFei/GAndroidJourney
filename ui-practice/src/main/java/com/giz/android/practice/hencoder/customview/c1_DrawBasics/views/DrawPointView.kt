package com.giz.android.practice.hencoder.customview.c1_DrawBasics.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/28
 */
class DrawPointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPointPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPointPaint.strokeWidth = 8f
        mPointPaint.strokeCap = Paint.Cap.ROUND // 设置点的形状。圆形
        canvas.drawPoint(50f, 50f, mPointPaint)

        mPointPaint.strokeWidth = 24f
        mPointPaint.strokeCap = Paint.Cap.BUTT // 和SQUARE效果一样，是方形
        canvas.drawPoints(floatArrayOf(0f, 0f, 50f, 100f, 50f, 200f), mPointPaint)
    }

}