package com.giz.android.practice.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class Practice13GetTextBoundsView : View {
    var paint1 = Paint(Paint.ANTI_ALIAS_FLAG)
    var paint2 = Paint(Paint.ANTI_ALIAS_FLAG)
    var text1 = "A"
    var text2 = "a"
    var text3 = "J"
    var text4 = "j"
    var text5 = "Â"
    var text6 = "â"
    var top = 200f
    var bottom = 400f

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(50f, top, (width - 50).toFloat(), bottom, paint1)

        // 使用 Paint.getTextBounds() 计算出文字的显示区域
        // 然后计算出文字的绘制位置，从而让文字上下居中
        // 这种居中算法的优点是，可以让文字精准地居中，分毫不差


        canvas.drawText(text1, 100f, calcTextMiddleBaseline(text1), paint2)
        canvas.drawText(text2, 200f, calcTextMiddleBaseline(text2), paint2)
        canvas.drawText(text3, 300f, calcTextMiddleBaseline(text3), paint2)
        canvas.drawText(text4, 400f, calcTextMiddleBaseline(text4), paint2)
        canvas.drawText(text5, 500f, calcTextMiddleBaseline(text5), paint2)
        canvas.drawText(text6, 600f, calcTextMiddleBaseline(text6), paint2)
    }

    private fun calcTextMiddleBaseline(text: String): Float {
        val bounds = Rect()
        paint2.getTextBounds(text, 0, text.length, bounds)

//        val h = bottom - top
//        return (h - bounds.height()) / 2f - bounds.top + top

        val middle = (top + bottom) / 2f
        return -(bounds.top + bounds.bottom) / 2f + middle
    }

    init {
        paint1.style = Paint.Style.STROKE
        paint1.strokeWidth = 20f
        paint1.color = Color.parseColor("#E91E63")
        paint2.textSize = 160f
    }
}