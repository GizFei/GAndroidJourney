package com.giz.android.practice.hencoder.customview.c1_DrawBasics.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/29
 */
class DrawPathView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPathPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.YELLOW
    }

    private val mLinePathPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.BLUE
        strokeWidth = 36f
        strokeCap = Paint.Cap.ROUND
    }

    private val path = Path().apply {
        addArc(200f, 200f, 400f, 400f, -225f, 225f)
        arcTo(400f, 200f, 600f, 400f, -180f, 225f, false)
        lineTo(400f, 542f)
    }

    private val linePath = Path().apply {
        lineTo(200f, 200f)
        rLineTo(300f, 0f)
        lineTo(100f, 300f)
    }

    private val quadPath = Path().apply {
        quadTo(100f, 100f, 400f, 500f)
        rQuadTo(100f, 100f, 200f, 150f)
    }

    private val mArcPath = Path().apply {
        lineTo(100f, 100f)
        arcTo(100f, 100f, 500f, 500f, -90f, 180f, true)
    }

    private val mCirclePath = Path().apply {
        addCircle(200f, 200f, 100f, Path.Direction.CW)
        lineTo(400f, 500f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(path, mPathPaint)
        mLinePathPaint.color = Color.RED
        canvas.drawPath(linePath, mLinePathPaint)
        canvas.drawPath(quadPath, mLinePathPaint)

        mLinePathPaint.color = Color.GREEN
        canvas.drawPath(mArcPath, mPathPaint)
        canvas.drawPath(mArcPath, mLinePathPaint)

        mLinePathPaint.color = Color.CYAN
        canvas.drawPath(mCirclePath, mLinePathPaint)
    }

}