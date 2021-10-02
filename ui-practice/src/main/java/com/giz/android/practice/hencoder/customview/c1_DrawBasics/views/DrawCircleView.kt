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
class DrawCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mAxisLinePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.BLACK
    }

    private val mPointTextPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = 48f
    }

    private val mFillCirclePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val mStrokeCirclePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 坐标系射线
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, mAxisLinePaint)
        canvas.drawLine(0f,  0f, 0f, height.toFloat(), mAxisLinePaint)
        canvas.drawText("(0, 0)", 0f, 20f, mPointTextPaint)

        // 绘制圆
        mFillCirclePaint.color = Color.BLACK
        canvas.drawCircle(200f, 200f, 100f, mFillCirclePaint)
        mFillCirclePaint.color = Color.BLUE
        canvas.drawCircle(450f, 200f, 100f, mFillCirclePaint)

        mStrokeCirclePaint.color = Color.RED
        mStrokeCirclePaint.strokeWidth = 4f
        canvas.drawCircle(200f, 450f, 100f, mStrokeCirclePaint)
        mStrokeCirclePaint.strokeWidth = 24f
        canvas.drawCircle(450f, 450f, 100f, mStrokeCirclePaint)

        mFillCirclePaint.color = Color.parseColor("#AAAA88EE")
        canvas.drawCircle(450f, 450f, 100f, mFillCirclePaint)
    }

}