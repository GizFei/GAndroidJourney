/**
 * Debug悬浮窗中使用到的数据结构。
 *
 * Created by GizFei on 2022/2/24
 */
package coder.giz.android.uidemo.floatingwindow.debug

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// region DebugInfo条目
abstract class DebugInfoItem(val title: String) {
    abstract val infoContentView: View

    abstract fun updateView(vararg data: Any?)

    fun removeContentViewFromParent() {
        val view = infoContentView
        val parent = view.parent
        if (parent is ViewGroup) {
            parent.removeView(view)
        }
    }

    protected inline fun <reified T> Array<out Any?>.getData(index: Int): T? {
        return this.getOrNull(index) as? T
    }

    protected fun Array<out Any?>.getString(index: Int): String? {
        return this.getOrNull(index) as? String
    }
}

/**
 * “当前页面”信息。即当前显示页面的Activity类名或Fragment类名。
 */
class CurrentPageDebugInfoItem(
    context: Context,
    title: String = "当前页面"
) : DebugInfoItem(title) {

    private val mTextView = TextView(context).apply {
        textSize = 16f
        setTextColor(Color.BLACK)
    }

    override val infoContentView: View get() = mTextView

    private var mCurActivity: String? = null
    private var mCurFragment: String? = null

    /**
     * data格式：
     * [0]. Activity类名
     * [1]. Fragment类名
     */
    override fun updateView(vararg data: Any?) {
        data.getString(0)?.let { mCurActivity = it }
        data.getString(1)?.let { mCurFragment = it }
        var text = ""
        mCurActivity?.let { text += "A: $it\n" }
        mCurFragment?.let { text += "F: $it\n" }

        mTextView.text = text.trim()
    }

}

/**
 * “设备离线原因”信息。
 */
class DeviceOfflineReasonDebugInfoItem(
    context: Context,
    title: String = "设备离线原因"
) : DebugInfoItem(title) {

    private val mTextView = TextView(context).apply {
        textSize = 16f
        setTextColor(Color.BLACK)
    }

    override val infoContentView: View get() = mTextView

    override fun updateView(vararg data: Any?) {
        mTextView.text = data.getString(0) ?: "Unknown Reason"
    }

}
// endregion

// region DebugSetting条目
class DebugSettingItem(
    val itemType: DebugInfoItemManager.ItemType,
    val title: String,
    var enabled: Boolean,
)
// endregion