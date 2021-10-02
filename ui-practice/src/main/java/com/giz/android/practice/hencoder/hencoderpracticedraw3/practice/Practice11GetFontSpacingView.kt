package com.giz.android.practice.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice11GetFontSpacingView : View {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var text = "Hello HenCoder"

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 使用 Paint.getFontSpacing() 来获取推荐的行距
        val spacing = paint.fontSpacing
        canvas.drawText("$text FontSpacing: ${"%.2f".format(spacing)}", 50f, 100f, paint)
        canvas.drawText(text, 50f, 100 + spacing, paint)
        canvas.drawText(text, 50f, 100 + spacing * 2, paint)
    }

    init {
        paint.textSize = 60f
    }
}