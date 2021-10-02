package com.giz.android.practice.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.giz.android.practice.R

class Practice08XfermodeView : View {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bitmap1: Bitmap? = null
    var bitmap2: Bitmap? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 使用 paint.setXfermode() 设置不同的结合绘制效果

        // 别忘了用 canvas.saveLayer() 开启 off-screen buffer
        canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        canvas.drawBitmap(bitmap1!!, 0f, 0f, paint)
        // 第一个：PorterDuff.Mode.SRC
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(bitmap2!!, 0f, 0f, paint)

//        canvas.drawBitmap(bitmap1!!, (bitmap1!!.width + 100).toFloat(), 0f, paint)
        // 第二个：PorterDuff.Mode.DST_IN
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
//        canvas.drawBitmap(bitmap2!!, (bitmap1!!.width + 100).toFloat(), 0f, paint)

//        canvas.drawBitmap(bitmap1!!, 0f, (bitmap1!!.height + 20).toFloat(), paint)
        // 第三个：PorterDuff.Mode.DST_OUT
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//        canvas.drawBitmap(bitmap2!!, 0f, (bitmap1!/!.height + 20).toFloat(), paint)

        // 用完之后使用 canvas.restore() 恢复 off-screen buffer
        canvas.restore()
    }

    init {
        bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.batman)
        bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.batman_logo)
    }
}