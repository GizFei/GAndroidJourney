package coder.giz.android.uidemo.floatingwindow.debug

import android.content.Context
import androidx.core.content.edit

/**
 * Debug设置面板设置项信息存储
 *
 * Created by GizFei on 2022/2/25
 */
class DebugSettingSP(context: Context) {

    companion object {
        private const val DEBUG_SETTING_SP_FILENAME = "DebugSettingSP"
    }

    private val mSP = context.getSharedPreferences(DEBUG_SETTING_SP_FILENAME, Context.MODE_PRIVATE)

    fun isSettingEnabled(itemType: DebugInfoItemManager.ItemType): Boolean {
        return mSP.getBoolean(itemType.value, true)
    }

    fun setSettingEnabled(itemType: DebugInfoItemManager.ItemType, enabled: Boolean) {
        mSP.edit { putBoolean(itemType.value, enabled) }
    }

    fun getSettingMap(): Map<DebugInfoItemManager.ItemType, Boolean> {
        return DebugInfoItemManager.ItemType.values().associateWith {
            isSettingEnabled(it)
        }
    }

}