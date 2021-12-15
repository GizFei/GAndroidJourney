package coder.giz.android.uidemo.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import coder.giz.android.yfutility.util.dp

/**
 * Created by GizFei on 2021/12/7
 */
class ShadowVerticalView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        maskFilter = BlurMaskFilter(16.dp, BlurMaskFilter.Blur.OUTER)
        color = Color.GRAY
    }
    private val mShadowWidth = 20.dp
    private val mCornerRadius = 8.dp

    val path = Path().apply {
        this.moveTo(60f, 60f)
        rLineTo(100f, 0f)
        rLineTo(50f, 25f)
        rLineTo(0f, 100f)
        rLineTo(-50f, 25f)
        rLineTo(-100f, 0f)
        this.close()
    }
    val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 16.dp
        color = Color.BLACK
        strokeJoin = Paint.Join.BEVEL
        strokeCap = Paint.Cap.BUTT
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), 20.dp, mPaint)
        canvas.drawRect(0f, height - 20.dp, width.toFloat(), height.toFloat(), mPaint)

        canvas.drawPath(path, pathPaint)
    }

}