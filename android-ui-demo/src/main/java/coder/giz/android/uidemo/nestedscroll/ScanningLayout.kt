package coder.giz.android.uidemo.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import coder.giz.android.uidemo.databinding.LayoutScanningBinding
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

    private val mBinding = LayoutScanningBinding.inflate(LayoutInflater.from(context), this, true)
    private val mIconImageView = mBinding.ivIcon
    private val mTextView = mBinding.tvHint

    init {
        printViewPropertyLog()
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

    /**
     * 可改变属性：translationX、x
     */
    fun move(offset: Float) {
        moveIconImageView(offset)
        moveTextView(offset)
    }

    private fun moveIconImageView(offset: Float) {
        val minX = mIconImageView.marginLeft
        val maxX = width - mIconImageView.width - mIconImageView.marginRight
        var newX = (mIconImageView.x + offset).toInt()
        newX = max(minX, min(newX, maxX))
        mIconImageView.x = newX.toFloat()

        mIconImageView.y -= offset / 3
    }

    private fun moveTextView(offset: Float) {
        val minX = mIconImageView.marginLeft + mIconImageView.width + mTextView.marginLeft
        val calcMinTop = mIconImageView.marginTop + mIconImageView.height / 2f - mTextView.height / 2f
        val minY = max(mTextView.marginTop, calcMinTop.toInt())

        val newX = (mTextView.x + offset).toInt()
        mTextView.x = max(minX, newX).toFloat()


    }

    private inline fun log(msg: () -> String) {
        YFLog.d("ScanningLayout", msg())
    }

}