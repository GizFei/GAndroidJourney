package com.giz.android.practice.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class Practice01LinearGradientView : View {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // 用 Paint.setShader(shader) 设置一个 LinearGradient
        // LinearGradient 的参数：坐标：(100, 100) 到 (500, 500) ；颜色：#E91E63 到 #2196F3

        val linearGradient = LinearGradient(100f, 100f, 500f, 500f,
            intArrayOf(0xFFE91E63.toInt(), 0xFF2196F3.toInt()), null, Shader.TileMode.CLAMP)
        shader = linearGradient
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(300f, 300f, 200f, paint)
    }
}