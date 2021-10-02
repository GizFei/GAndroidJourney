package com.giz.android.practice.hencoder.customview.c1_DrawBasics.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import kotlin.math.max
import kotlin.math.min

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/30
 */
class DynamicCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mStrokeWidth = 24f
    private val mCirclePaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = mStrokeWidth
    }

    @FloatRange(from = 0.0, to = 1.0)
    private var mProgress: Float = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val actualWidth = min(measuredWidth, measuredHeight)
        setMeasuredDimension(actualWidth, actualWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(mStrokeWidth, mStrokeWidth, width - mStrokeWidth, height - mStrokeWidth,
            -90f, mProgress * 360f, false, mCirclePaint)
    }

    fun updateProgress(progress: Float) {
        mProgress = max(min(progress, 1f), 0f)
        invalidate()
    }

}