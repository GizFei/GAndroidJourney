package coder.giz.android.uidemo.nestedscroll

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import coder.giz.android.yfutility.util.YFLog

/**
 * Created by GizFei on 2022/10/31
 */
class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        log { "layout left: $l" }
        super.layout(l, t, r, b)
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        log { "setFrame: left: $l" }
        return super.setFrame(l, t, r, b)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        log { "onLayout left: $left" }
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun offsetLeftAndRight(offset: Int) {
        log { "offsetLeftAndRight offset: $offset" }
        super.offsetLeftAndRight(offset)

    }

    private inline fun log(msg: () -> String) {
        YFLog.d("MyTextView", msg())
    }

}