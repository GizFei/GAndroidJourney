package com.giz.android.practice.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.core.view.updateLayoutParams
import com.giz.android.practice.R
import kotlin.math.max
import kotlin.math.min

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/30
 */
class CustomSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val dp16 = 16.dp

    private val mTvProgress: TextView
    private val mSeekBar: SeekBar

    private var mListener: SeekBar.OnSeekBarChangeListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_seekbar, this, true).apply {
            mTvProgress = findViewById(R.id.tv_progress)
            mSeekBar = findViewById(R.id.sb_custom)
        }

        mSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateProgressTextView(progress)
                mListener?.onProgressChanged(seekBar, progress, fromUser)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mListener?.onStartTrackingTouch(seekBar)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mListener?.onStopTrackingTouch(seekBar)
            }
        })
        mSeekBar.post {
            updateProgressTextView(mSeekBar.progress)
        }
    }

    private fun updateProgressTextView(progress: Int) {
        mTvProgress.text = "$progress"

        // 先测量更改文本后TextView的宽度
        mTvProgress.measure(0, 0)

        val progressWidth = (mSeekBar.width - mSeekBar.paddingLeft - mSeekBar.paddingRight) *
            (mSeekBar.progress / mSeekBar.max.toFloat()) + mSeekBar.paddingLeft
        val rawLeft = progressWidth - (mTvProgress.measuredWidth / 2f)
        val marginLeft = min(max(0, rawLeft.toInt()), mSeekBar.width - mTvProgress.measuredWidth)

        mTvProgress.updateLayoutParams<LinearLayout.LayoutParams> {
            marginStart = marginLeft
        }
    }

    fun setOnSeekBarChangeListener(listener: SeekBar.OnSeekBarChangeListener, call: Boolean = true) {
        mListener = listener.also {
            if (call) {
                it.onProgressChanged(mSeekBar, mSeekBar.progress, false)
            }
        }
    }

    fun setProgress(progress: Int) { mSeekBar.progress = progress }

    fun getProgress(): Int = mSeekBar.progress

    fun setMaxProgress(max: Int) { mSeekBar.max = max }

    fun getMaxProgress(): Int = mSeekBar.max

    private val Int.dp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), context.resources.displayMetrics)

}