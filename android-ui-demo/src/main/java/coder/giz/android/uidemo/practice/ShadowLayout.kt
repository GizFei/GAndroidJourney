package coder.giz.android.uidemo.practice

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout
import coder.giz.android.yfutility.util.dp

/**
 * Created by GizFei on 2021/12/7
 */
class ShadowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        maskFilter = BlurMaskFilter(16.dp, BlurMaskFilter.Blur.OUTER)
//        color = Color.RED
        color = 0x7DFF0000
    }
    private val mSolidPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }
    private val mShadowWidth = 20.dp
    private val mCornerRadius = 18.dp

    init {
        setWillNotDraw(false)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        val extra = mShadowWidth.toInt()
        super.setPadding(left + extra, top + extra, right + extra, bottom + extra)

    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(mShadowWidth, mShadowWidth, width - mShadowWidth, height - mShadowWidth,
            mCornerRadius, mCornerRadius, mShadowPaint
        )
        canvas.drawRoundRect(mShadowWidth, mShadowWidth / 2, width - mShadowWidth, height - mShadowWidth,
            mCornerRadius, mCornerRadius, mSolidPaint
        )

        super.draw(canvas)
    }

    fun updateShadowSize(size: Float) {
        mShadowPaint.maskFilter = BlurMaskFilter(size, BlurMaskFilter.Blur.OUTER)
        invalidate()
    }

}