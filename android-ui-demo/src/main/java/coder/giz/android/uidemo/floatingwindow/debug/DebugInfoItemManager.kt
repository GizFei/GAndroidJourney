package coder.giz.android.uidemo.floatingwindow.debug

import android.content.Context

/**
 * Created by GizFei on 2022/2/24
 */
class DebugInfoItemManager(private val mContext: Context) {

    companion object {
        const val ITEM_CURRENT_PAGE = "DebugInfo.CurrentPageItem"
        const val ITEM_DEVICE_OFFLINE_REASON_PAGE = "DebugInfo.DeviceOfflineReasonItem"

        fun getSortedItemTypeList(): List<ItemType> {
            return ItemType.values().toList()
        }
    }

    enum class ItemType(val value: String, val title: String) {
        CURRENT_PAGE(ITEM_CURRENT_PAGE, "当前页面"),
        DEVICE_OFFLINE_REASON(ITEM_DEVICE_OFFLINE_REASON_PAGE, "设备离线原因"),
        ;
    }

    private val mDebugInfoItemMap = mutableMapOf<ItemType, DebugInfoItem>()
    private var mInit = false

    /**
     * 初始化调试信息项映射表。包含所有类型。
     */
    fun initDebugInfoItemMap() {
        if (mInit) {
            return
        }
        mInit = true
        mDebugInfoItemMap.clear()
        mDebugInfoItemMap[ItemType.CURRENT_PAGE] = CurrentPageDebugInfoItem(mContext)
        mDebugInfoItemMap[ItemType.DEVICE_OFFLINE_REASON] = DeviceOfflineReasonDebugInfoItem(mContext)
    }

    fun getDebugInfoItems(): List<DebugInfoItem> {
        ensureItemsInit()
        return getSortedDebugInfoItems(mDebugInfoItemMap)
    }

    private fun ensureItemsInit() {
        if (!mInit) {
            initDebugInfoItemMap()
        }
    }

    private fun getSortedDebugInfoItems(itemMap: Map<ItemType, DebugInfoItem>): List<DebugInfoItem> {
        val sortedTypes = getSortedItemTypeList()
        val sortedItems = mutableListOf<DebugInfoItem>()
        for (type in sortedTypes) {
            itemMap[type]?.let(sortedItems::add)
        }
        return sortedItems
    }

    fun getCustomizedDebugInfoItems(setting: DebugSettingSP): List<DebugInfoItem> {
        ensureItemsInit()
        val settingMap = setting.getSettingMap()
        val customizedItemsMap = mutableMapOf<ItemType, DebugInfoItem>()
        val sortedTypes = getSortedItemTypeList()

        for (type in sortedTypes) {
            val enabled = settingMap[type] == true
            if (enabled) {
                mDebugInfoItemMap[type]?.let { customizedItemsMap[type] = it }
            }
        }

        return getSortedDebugInfoItems(customizedItemsMap)
    }

    fun updateCurrentPageItem(activityName: String? = null, fragmentName: String? = null) {
        mDebugInfoItemMap[ItemType.CURRENT_PAGE]?.updateView(
            activityName, fragmentName
        )
    }

    fun updateDeviceOfflineReasonPageItem(reason: String) {
        mDebugInfoItemMap[ItemType.DEVICE_OFFLINE_REASON]?.updateView(reason)
    }

}