package coder.giz.kotlin.coroutines.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import coder.giz.kotlin.coroutines.R

/**
 * Created by GizFei on 2022/10/22
 */
class LogcatScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private val mTextView: TextView = TextView(context).apply {
        setTextAppearance(R.style.YFUi_Widget_TextView_Body1)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        text = "Logcat:"
    }

    init {
        addView(mTextView)
    }

    fun logcat(tag: String, msg: String) {
        Log.w(tag, msg)
        val log = "LOG/[$tag] $msg"
        val allLog = "${mTextView.text}\n$log"
        mTextView.text = allLog
        mTextView.post {
            this.fullScroll(View.FOCUS_DOWN)
        }
    }

    fun clearLogcat() {
        mTextView.text = "Logcat:"
    }

}
