package coder.giz.android.yfui.imageview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import coder.giz.android.yfui.R
import kotlin.math.min

/**
 * ### 圆角图片控件
 * #### 1、在"attrs.settings"文件中声明样式
 * ```
 * <declare-styleable name="RoundedImageView">
 *      <attr name="riv_type" format="enum">
 *          <enum name="circle" value="1"></enum>
 *          <enum name="corner" value="2"></enum>
 *      </attr>
 *      <attr name="riv_radius" format="dimension"></attr>
 * </declare-styleable>
 * ```
 * - riv_type：圆角样式：circle-圆形；corner-圆角
 * - riv_radius：圆角模式下的圆角半径
 *
 * Created by GizFei on 2021/7/10
 */
class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        // 圆形，圆角，默认
        private const val TYPE_CIRCLE = 1
        private const val TYPE_CORNER = 2
        private const val TYPE_DEFAULT = 0
    }

    private var mType = TYPE_DEFAULT // 类型
    private var mRadius = 0f // 圆角半径
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView,
            defStyleAttr, 0)
        mType = ta.getInt(R.styleable.RoundedImageView_riv_type, TYPE_DEFAULT)
        mRadius = ta.getDimension(R.styleable.RoundedImageView_riv_radius, 8.dp)
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 当模式为圆形模式的时候，我们强制让宽高一致
        if (mType == TYPE_CIRCLE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            val result = min(measuredWidth, measuredHeight)
            setMeasuredDimension(result, result)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private var isShaderSet = false
    override fun onDraw(canvas: Canvas) {
        val drawable = drawable
        val matrix: Matrix? = imageMatrix
        if (drawable == null) {
            return
        }
        if (drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) {
            return
        }
        if (matrix == null && paddingTop == 0 && paddingLeft == 0) {
            drawable.draw(canvas)
        } else {
            val saveCount: Int = canvas.saveCount
            canvas.save()
            if (cropToPadding) {
                val scrollX = scrollX
                val scrollY = scrollY
                canvas.clipRect(
                    scrollX + paddingLeft, scrollY + paddingTop,
                    scrollX + right - left - paddingRight,
                    scrollY + bottom - top - paddingBottom
                )
            }
            canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
            when (mType) {
                TYPE_CIRCLE -> {
                    ensureShader(drawable)
                    canvas.drawCircle(width / 2f, height / 2f, width / 2f, mPaint)
                }
                TYPE_CORNER -> {
                    ensureShader(drawable)
                    canvas.drawRoundRect(
                        paddingLeft.toFloat(),
                        paddingTop.toFloat(),
                        (width - paddingRight).toFloat(),
                        (height - paddingBottom).toFloat(),
                        mRadius, mRadius, mPaint
                    )
                }
                else -> {
                    if (matrix != null) {
                        canvas.concat(matrix)
                    }
                    drawable.draw(canvas)
                }
            }
            canvas.restoreToCount(saveCount)
        }
    }

    private fun ensureShader(drawable: Drawable) {
        if(!isShaderSet){
            val bitmap = drawable2Bitmap(drawable)
            mPaint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            isShaderSet = true
        }
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        isShaderSet = false
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        isShaderSet = false
    }

    /**
     * drawable转换成bitmap
     */
    private fun drawable2Bitmap(drawable: Drawable?): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        if (drawable == null) {
            return bitmap
        }
        val canvas = Canvas(bitmap)
        //根据传递的scale type获取matrix对象，设置给bitmap
        val matrix: Matrix? = imageMatrix
        if (matrix != null) {
            canvas.concat(matrix)
        }
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 设置圆角半径
     * @param radius 半径值，单位dp
     */
    fun setCornerRadius(radius: Float) {
        mRadius = radius.toInt().dp
        invalidate()
    }

    /**
     * 设置样式
     * TYPE_CORNER, TYPE_CIRCLE, TYPE_DEFAULT
     * @param type 类型
     */
    fun setType(type: Int) {
        mType = type
        invalidate()
    }

    private val Int.dp get(): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics)

}