package com.giz.android.practice.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.giz.android.practice.R
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * 分段着色视图
 *
 * Created by GizFei on 2021/4/19
 */
class ColorPaintingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 默认宽高
    private val mDefaultWidth = 300.dp

    // 绘制区域宽高
    private var mAreaWidth = 0f
    private var mAreaHeight = 0f

    // 方块宽高、列数
    private var mBlockWidth = 0f
    private var mBlockHeight = 0f
    private var mCols = 10
    private var mRows = 5

    // 水平、竖直间隙宽度
    private var mGapX = 0f
    private var mGapY = 0f

    // 偏移后的“零点”坐标。即绘制区域左上角在当前视图中的坐标
    private var mZeroX = 0f
    private var mZeroY = 0f

    // 颜色色块包装类列表、颜色列表
    private val mColorBlockList = mutableListOf<ColorBlock>()
    private val mColorList = mutableListOf<Int>()

    private val mDefBlockColor: Int = 0xFFDDDDDD.toInt()

    // 色块画笔
    private val mBlockPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 虚线
    private val mDashLinePath = Path()
    private val mDashLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2.dp
        color = mDefBlockColor
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
    }

    init {
        var initColorList = false
        context.obtainStyledAttributes(attrs, R.styleable.ColorPaintingView).apply {
            mCols = max(1, getInt(R.styleable.ColorPaintingView_cpv_cols, 10))
            mRows = max(1, getInt(R.styleable.ColorPaintingView_cpv_rows, 5))
            mBlockHeight = getDimension(R.styleable.ColorPaintingView_cpv_blockHeight, 48.dp)
            mGapX = getDimension(R.styleable.ColorPaintingView_cpv_gapX, 2.dp)
            mGapY = getDimension(R.styleable.ColorPaintingView_cpv_gapY, 2.dp)

            initColorList = hasValue(R.styleable.ColorPaintingView_cpv_rows)

            recycle()
        }

        // 在XML文件中定义了cpv_rows属性，则初始化灰色颜色列表。
        if (initColorList) {
            mColorList.addAll(List(mRows * mCols) { mDefBlockColor })
            updateColorBlockList()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = getMeasuredSize(mDefaultWidth.toInt(), widthMeasureSpec, false)

        // 根据色块的高度 + 行间距来计算高度
        val heightSize: Int = (mGapY * (mRows - 1) + mBlockHeight * mRows + paddingTop + paddingBottom).toInt()

        setMeasuredDimension(widthSize, heightSize)
    }

    /**
     * 测量尺寸
     *
     * @param useMin 当规格为AT_MOST，取较小值，还是取较大值
     */
    private fun getMeasuredSize(size: Int, measureSpec: Int, useMin: Boolean): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            MeasureSpec.AT_MOST -> if (useMin) min(specSize, size) else max(specSize, size)
            MeasureSpec.EXACTLY -> specSize
            else -> size
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        // 1、计算绘制宽高
        mAreaWidth = width.toFloat() - paddingLeft - paddingRight
        mAreaHeight = height.toFloat() - paddingTop - paddingBottom

        // 2、计算绘制区域“零点”坐标、block宽
        mZeroX = paddingLeft.toFloat()
        mZeroY = paddingTop.toFloat()
        mBlockWidth = (mAreaWidth - mGapX * (mCols - 1)) / mCols
    }

    override fun onDraw(canvas: Canvas) {
        Log.e("TAG", "onDraw: ")
        // 根据每个色块的坐标绘制
        for (block in mColorBlockList) {
            mBlockPaint.color = block.color
            canvas.drawRect(calcBlockRect(block.row, block.col), mBlockPaint)
        }

        // 绘制行间的虚线
        if (mRows > 1) {
            for (i in 0 until mRows - 1) {
                val sx = mAreaWidth + mZeroX
                val sy = mBlockHeight * (i + 1) + mGapY * i + mZeroY
                mDashLinePath.apply {
                    reset()
                    moveTo(sx, sy)
                    rLineTo(0f, mGapY / 2f)
                    rLineTo(-mAreaWidth, 0f)
                    rLineTo(0f, mGapY / 2f)
                }
                canvas.drawPath(mDashLinePath, mDashLinePaint)
            }
        }
    }

    private var mLastX = 0f
    private var mLastY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x
                mLastY = event.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }

        val touchX = event.x - mZeroX
        val touchY = event.y - mZeroY

        val c = floor(touchX / (mBlockWidth + mGapX)).toInt()
        val r = floor(touchY / (mBlockHeight + mGapY)).toInt()
        val blockRect = calcBlockRect(r, c)
        if (blockRect.contains(touchX, touchY)) {
            val idx = calcIndex(r, c)
            if (idx in mColorList.indices && mColorList[idx] != Color.BLUE) {
                updateColor(idx, Color.BLUE)
                invalidate()
            }
        }

        return true
    }

    /**
     * 更新颜色列表。重新计算行数。
     */
    fun updateColorList(colors: List<Int>) {
        if (colors.isEmpty()) return

        mColorList.clear()
        mColorList.addAll(colors)
        mRows =  ceil(mColorList.size / mCols.toFloat()).toInt()    // 根据颜色列表个数与列数，计算行数
        updateColorBlockList()

        requestLayout()
    }

    private fun calcBlockRect(row: Int, col: Int): RectF {
        val left = (mBlockWidth + mGapX) * col + mZeroX
        val top = (mBlockHeight + mGapY) * row + mZeroY

        return RectF(left, top, left + mBlockWidth, top + mBlockHeight)
    }

    /**
     * 将 (r, c) 坐标转换成列表索引
     */
    private fun calcIndex(r: Int, c: Int): Int = r * mCols + c

    private fun updateColor(idx: Int, color: Int) {
        if (idx in mColorList.indices) {
            mColorList[idx] = color
        }
        if (idx in mColorBlockList.indices) {
            mColorBlockList[idx].color = color
        }
    }

    /**
     * 更新色块列表
     */
    private fun updateColorBlockList() {
        mColorBlockList.clear()
        mColorList.forEachIndexed { idx, color ->
            val r = idx / mCols
            val c = idx.rem(mCols)
            mColorBlockList.add(ColorBlock(r, c, color))
        }
    }

    private val Int.dp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), context.resources.displayMetrics)

    /**
     * 颜色色块包装类
     */
    private data class ColorBlock(var row: Int, var col: Int, var color: Int)
}