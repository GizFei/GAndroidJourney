package com.giz.android.practice.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class Practice02StaticLayoutView : View {
    var textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    var text = "Hello\nHenCoder"

    private val staticLayout by lazy {
        StaticLayout.Builder.obtain(
            text,
            0,
            text.length,     // 不包括
            textPaint,
            600
        ).build()
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 使用 StaticLayout 代替 Canvas.drawText() 来绘制文字，
        // 以绘制出带有换行的文字
//        canvas.drawText(text, 50f, 100f, textPaint)

        canvas.save()

        canvas.translate(50f, 40f)
        canvas.drawLine(0f, 0f, 200f, 0f, textPaint)
        canvas.drawLine(0f, 0f, 0f, 300f, textPaint)
        staticLayout.draw(canvas)

        canvas.restore()
    }

    init {
        textPaint.textSize = 60f
    }
}