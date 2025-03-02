package coder.giz.android.uidemo.nestedscroll

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.FloatRange
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.updateLayoutParams
import coder.giz.android.uidemo.R
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
//    private val mIconImageView: ImageView = mBinding.ivIcon
    private val mIconImageView: View = mBinding.progressBar
    private val mTextView: TextView = mBinding.tvHint
    private val mRootView: View = mBinding.root
    private val mCountTextView: TextView = mBinding.tvCount

    private var mTotalHeight = 0

    private var mMaxScrollOffsetY = 0
    private var mCurScrollOffsetY = 0

    private var mInit = false
    private var mTextViewMaxTranslationX = 0

    init {
        post {
            printViewPropertyLog()
        }
//        mTextView.doOnTextChanged { _, _, _, _ ->
//            mTextView.post {
//                mTextViewMaxTranslationX = (mIconImageView.marginLeft + mIconImageView.width + mTextView.marginLeft) -
//                        mTextView.left
//            }
//        }
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

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!mInit) {
            mInit = true
            mTextViewMaxTranslationX = (mIconImageView.marginLeft + mIconImageView.width + mTextView.marginLeft) -
                    mTextView.left
        }
    }

    /**
     * 可改变属性：translationX、x
     */
    fun updateOffset(offset: Int) {
        mCurScrollOffsetY = max(0, min(offset, mMaxScrollOffsetY))
        moveIconImageView(offset)
        moveTextView(offset)
    }

    @FloatRange(from = 0.0, to = 1.0)
    private fun calcOffsetPercent(offset: Int): Float {
        val p = offset.toFloat() / mMaxScrollOffsetY.toFloat()
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
        val maxTy = mMaxScrollOffsetY + mRootView.paddingTop - mIconImageView.top

        mIconImageView.translationX = maxTx * percent
        mIconImageView.translationY = maxTy * percent

        mCountTextView.translationY = maxTy * percent

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
        val maxTy = (mRootView.paddingTop + mMaxScrollOffsetY + mIconImageView.height / 4) - mTextView.top

        mTextView.translationX = maxTx * percent
        mTextView.translationY = maxTy * percent

        checkTextViewWidth()

        log { "moveTextView: maxTx: $maxTx, percent: $percent translationX: ${mTextView.translationX} " +
                "translationY: ${mTextView.translationY} left: ${mTextView.left} marginLeft: ${mTextView.marginLeft}" }
    }

    private fun checkTextViewWidth() {
        val textViewMaxWidth = (mRootView.width - mTextView.x - getTextViewRightSpace()).toInt()
        val textWidth = mTextView.paint.measureText(mTextView.text.toString()).toInt()

        log { "checkTextViewWidth textViewMaxWidth: $textViewMaxWidth mTextView.width: ${mTextView.width} " +
            "x: ${mTextView.x} getTextViewRightSpace: ${getTextViewRightSpace()} TextWidth: $textWidth"}

        if (textWidth > textViewMaxWidth) {
            mTextView.updateLayoutParams<MarginLayoutParams> {
                width = textViewMaxWidth
            }
        }
    }

    private fun getTextViewRightSpace(): Int {
        val countViewSpace = if (mCountTextView.isVisible) {
            mBinding.tvCount.width + mBinding.tvCount.marginRight
        } else {
            0
        }
        return mTextView.marginRight + countViewSpace
    }

    fun updateCountViewVisibility(visible: Boolean) {
        if (mCountTextView.isVisible == visible) {
            return
        }

        mCountTextView.isVisible = visible
        if (visible) {
            mCountTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_in_from_bottom))
        } else {
            mCountTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_out_to_bottom))
        }
        mCountTextView.post {
//            checkTextViewWidth()

            val textViewMaxWidth = (mRootView.width - mTextView.x - getTextViewRightSpace()).toInt()
            val textWidth = mTextView.paint.measureText(mTextView.text.toString()).toInt()

            if (textWidth > textViewMaxWidth) {
                ValueAnimator.ofInt(mTextView.width, textViewMaxWidth).apply {
                    addUpdateListener {
                        log { "updateCountViewVisibility width anim: ${it.animatedValue}" }
                        mTextView.updateLayoutParams<ViewGroup.LayoutParams> {
                            this.width = it.animatedValue as Int
                        }

                        val percent = calcOffsetPercent(mCurScrollOffsetY)
                        val maxTx = (mIconImageView.marginLeft + mIconImageView.width + mTextView.marginLeft) -
                                mTextView.left
                        mTextView.translationX = maxTx * percent

//                        updateOffset(mCurScrollOffsetY)
                    }
                    duration = 300
                }.start()
            }

//            mTextView.post {
//                val percent = calcOffsetPercent(mCurScrollOffsetY)
//                val maxTx = (mIconImageView.marginLeft + mIconImageView.width + mTextView.marginLeft) -
//                        mTextView.left
//                mTextView.animate().translationX(maxTx * percent)
//            }

        }
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
        mMaxScrollOffsetY = height -
                (mIconImageView.height +
                        mRootView.paddingTop +
                        mRootView.paddingBottom)
        return mMaxScrollOffsetY
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