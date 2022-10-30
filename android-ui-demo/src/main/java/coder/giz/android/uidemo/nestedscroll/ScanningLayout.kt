package coder.giz.android.uidemo.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.FloatRange
import androidx.core.view.marginLeft
import coder.giz.android.uidemo.databinding.LayoutScanning2Binding
import coder.giz.android.yfutility.util.YFLog
import coder.giz.android.yfutility.util.getScreenWidth
import kotlin.math.max
import kotlin.math.min

/**
 * Created by GizFei on 2022/8/28
 */
class ScanningLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mBinding = LayoutScanning2Binding.inflate(LayoutInflater.from(context), this, true)
    private val mIconImageView: ImageView = mBinding.ivIcon
    private val mTextView: TextView = mBinding.tvHint
    private val mRootView: View = mBinding.root

    private var mTotalHeight = 0

    private var mScrollOffsetY = 0

    init {
        post {
            printViewPropertyLog()
        }
    }

    private fun printViewPropertyLog() {
        log {
            """
                屏幕宽：${getScreenWidth(context)}
                IconImageView - width: ${mIconImageView.width} left: ${mIconImageView.left} translationX: ${mIconImageView.translationX}
                TextView - width: ${mTextView.width} left: ${mTextView.left} translationX: ${mTextView.translationX}
            """.trimIndent()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mTotalHeight == 0) {
            mTotalHeight = height
        }
        log {
            "onSizeChanged height: $height. totalHeight: $mTotalHeight"
        }
    }

    /**
     * 可改变属性：translationX、x
     */
    fun move(offset: Int) {
        moveIconImageView(offset)
        moveTextView(offset)
    }

    @FloatRange(from = 0.0, to = 1.0)
    private fun calcOffsetPercent(offset: Int): Float {
        val p = offset.toFloat() / mScrollOffsetY.toFloat()
        return max(0f, min(p, 1f))
    }

    private fun moveIconImageView(offset: Int) {
//        val minX = mIconImageView.marginLeft
//        val maxX = width - mIconImageView.width - mIconImageView.marginRight
//        var newX = (mIconImageView.x + offset).toInt()
//        newX = max(minX, min(newX, maxX))
//        mIconImageView.x = newX.toFloat()

        val percent = calcOffsetPercent(offset)
        val maxTx = mIconImageView.marginLeft - mIconImageView.left
        val maxTy = mScrollOffsetY + mRootView.paddingTop - mIconImageView.top

        mIconImageView.translationX = maxTx * percent
        mIconImageView.translationY = maxTy * percent

        log { "moveIconImageView: translationY: ${mIconImageView.translationY}" }
    }

    private fun moveTextView(offset: Int) {
//        val minX = mIconImageView.marginLeft + mIconImageView.width + mTextView.marginLeft
//        val calcMinTop = mIconImageView.marginTop + mIconImageView.height / 2f - mTextView.height / 2f
//        val minY = max(mTextView.marginTop, calcMinTop.toInt())
//
//        val newX = (mTextView.x + offset).toInt()
//        mTextView.x = max(minX, newX).toFloat()

        val percent = calcOffsetPercent(offset)
        val maxTx = (mIconImageView.marginLeft + mIconImageView.width + mTextView.marginLeft) -
                mTextView.left
        val maxTy = (mRootView.paddingTop + mScrollOffsetY + mIconImageView.height / 4) - mTextView.top

        mTextView.translationX = maxTx * percent
        mTextView.translationY = maxTy * percent

        log { "moveTextView: translationY: ${mTextView.translationY}" }
    }

//    fun changeHeightBy(dy: Int) {
//        mRootView.updateLayoutParams<ViewGroup.LayoutParams> {
//            val expectedHeight = mTotalHeight + dy
//            val newHeight = max(0, min(expectedHeight, mTotalHeight))
//            height = newHeight
//        }
//    }

    fun calcScrollOffsetY(): Int {
//        mScrollOffsetY = height / 2
        mScrollOffsetY = height -
                (mIconImageView.height +
                        mRootView.paddingTop +
                        mRootView.paddingBottom)
        return mScrollOffsetY
    }

    private var mIsLongText = false
    fun changeTextLength() {
        mIsLongText = !mIsLongText
        mTextView.text = if (mIsLongText) {
            "Scanning nearby devices Scanning nearby devices"
        } else {
            "Scanning nearby devices"
        }
    }

    private inline fun log(msg: () -> String) {
        YFLog.d("ScanningLayout", msg())
    }

}