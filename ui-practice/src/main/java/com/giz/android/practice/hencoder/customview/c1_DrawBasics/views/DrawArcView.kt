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
 * Created by GizFei on 2021/3/29
 */
class DrawArcView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mArcPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.YELLOW
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(0f, 0f, 200f, 200f, 0f, 60f, false, mArcPaint)
        canvas.drawArc(250f, 0f, 450f, 200f, 90f, 90f, true, mArcPaint)
        canvas.drawArc(500f, 0f, 700f, 300f, 0f, 270f, true, mArcPaint)

        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = 16f
        canvas.drawArc(0f, 100f, 100f, 200f, -90f, 120f, true, mArcPaint)
        canvas.drawArc(0f, 200f, 100f, 300f, 90f, 180f, false, mArcPaint)
    }

}