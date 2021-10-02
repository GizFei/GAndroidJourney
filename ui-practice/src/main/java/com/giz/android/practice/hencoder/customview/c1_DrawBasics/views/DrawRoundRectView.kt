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
class DrawRoundRectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mRectPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.parseColor("#3C000000"))
        canvas.drawRoundRect(50f, 50f, 100f, 100f, 24f, 24f, mRectPaint)
        canvas.drawRoundRect(50f, 200f, 200f, 300f, 48f, 48f, mRectPaint)
        canvas.drawRoundRect(50f, 350f, 200f, 450f, 24f, 48f, mRectPaint)
    }

}