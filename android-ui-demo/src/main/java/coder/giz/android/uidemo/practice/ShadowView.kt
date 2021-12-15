package coder.giz.android.uidemo.practice

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import coder.giz.android.yfutility.util.dp

/**
 * Created by GizFei on 2021/12/7
 */
class ShadowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        maskFilter = BlurMaskFilter(16.dp, BlurMaskFilter.Blur.OUTER)
        color = Color.RED
    }
    private val mShadowWidth = 20.dp
    private val mCornerRadius = 8.dp

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(mShadowWidth, mShadowWidth, width - mShadowWidth, height - mShadowWidth,
            mCornerRadius, mCornerRadius, mPaint
        )
    }

}