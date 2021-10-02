package com.giz.android.practice.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.giz.android.practice.R

class Practice05ComposeShaderView : View {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawCircle(200f, 200f, 200f, paint)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null) // 硬件加速下 ComposeShader 不能使用两个同类型的 Shader

        // 用 Paint.setShader(shader) 设置一个 ComposeShader
        // Shader 1: BitmapShader 图片：R.drawable.batman
        // Shader 2: BitmapShader 图片：R.drawable.batman_logo
        val shader1 = BitmapShader(BitmapFactory.decodeResource(context.resources, R.drawable.batman), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val shader2 = BitmapShader(BitmapFactory.decodeResource(context.resources, R.drawable.batman_logo), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = ComposeShader(shader1, shader2, PorterDuff.Mode.DST_OUT)
    }
}