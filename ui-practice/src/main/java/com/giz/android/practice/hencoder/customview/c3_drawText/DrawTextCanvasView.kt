package com.giz.android.practice.hencoder.customview.c3_drawText

import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.*
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * Description of the file
 *
 * Created by GizFei on 2021/4/6
 */
class DrawTextCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
    private val text2 = "Hello World"

    private val mStaticLayout by lazy { getStaticLayout() }

    private val mTextPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textSize = 64f
        typeface = Typeface.MONOSPACE
        style = Paint.Style.STROKE
    }
    private val mTextBounds = Rect()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mStaticLayout.draw(canvas)
        canvas.drawText(text1, 0f, 400f, mTextPaint)

        val xOffset = 0
        val yOffset = 600
        mTextPaint.getTextBounds(text2, 0, text2.length, mTextBounds)
        Log.w("TAG", "onDraw: $mTextBounds")
        mTextBounds.run {
            left += xOffset
            right += xOffset
            top += yOffset
            bottom += yOffset
        }
        canvas.drawRect(mTextBounds, mTextPaint)
        canvas.drawText(text2, xOffset.toFloat(), yOffset.toFloat(), mTextPaint)
    }

    private fun getStaticLayout(): StaticLayout {
        val paint = TextPaint().apply {
            isAntiAlias = true
            textSize = 48f
            typeface = Typeface.SANS_SERIF
            color = Color.BLUE
        }

        return StaticLayout.Builder.obtain(text1, 0, text1.length, paint, width)
            .setLineSpacing(2f, 1f)
            .build()
    }

}