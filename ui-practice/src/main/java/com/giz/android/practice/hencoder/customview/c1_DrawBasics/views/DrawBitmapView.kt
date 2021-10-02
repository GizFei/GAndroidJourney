package com.giz.android.practice.hencoder.customview.c1_DrawBasics.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.get
import com.giz.android.practice.R

/**
 * Description of the file
 *
 * Created by GizFei on 2021/4/6
 */
class DrawBitmapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val TAG = "DrawBitmapView"

    private val mBgBitmap by lazy {
        BitmapFactory.decodeResource(context.resources, R.drawable.bg_star_sky).also {
            Log.w(TAG, "${it.width} ${it.height} ${it.byteCount} ${it.allocationByteCount}")
        }
    }

    private val mTextPaint = Paint().apply {
        alpha = 120
        textSize = 200f
        color = Color.BLACK
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mBgBitmap, Rect(0, 0, mBgBitmap.width / 2, mBgBitmap.height / 2),
            RectF(0f, 0f, 200f, 200f), null)

        canvas.drawBitmap(mBgBitmap, 0f, 300f, null)

        canvas.drawText("Hello World.", 0f, 200f, mTextPaint)

        Log.w(TAG, "onDraw: ${mBgBitmap.getScaledWidth(canvas)}")
    }

}