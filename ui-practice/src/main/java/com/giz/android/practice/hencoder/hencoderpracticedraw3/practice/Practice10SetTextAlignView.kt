package com.giz.android.practice.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice10SetTextAlignView : View {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var text = "Hello HenCoder"

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 使用 Paint.setTextAlign() 来调整文字对齐方式

        // 第一处：使用 Paint.Align.LEFT
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText(text, (width / 2).toFloat(), 100f, paint)
//        canvas.drawText(text, (width).toFloat(), 100f, paint)

        // 第二处：使用 Paint.Align.CENTER
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, (width / 2).toFloat(), 200f, paint)
//        canvas.drawText(text, (width).toFloat(), 200f, paint)

        // 第三处：使用 Paint.Align.RIGHT
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText(text, (width / 2).toFloat(), 300f, paint)
//        canvas.drawText(text, (width).toFloat(), 300f, paint)

        // ！！！ 根据drawText中的x坐标为基准线对齐
        // Paint.Align.LEFT：文字全部在基准线右侧
        // Paint.Align.CENTER：文字均匀分布在基准线两侧
        // Paint.Align.RIGHT：文字全部在基准线左侧
    }

    init {
        paint.textSize = 60f

        // 使用 Paint.setTextAlign() 来调整文字对齐方式
    }
}