package coder.giz.android.yfutility.navigation

import android.content.Context
import android.view.ViewGroup
import coder.giz.android.yfutility.components.navigate
import coder.giz.android.yfutility.databinding.ItemNavBinding
import coder.giz.android.yfutility.navigation.base.BaseNavAdapter
import coder.giz.android.yfutility.navigation.base.BaseNavHolder
import coder.giz.android.yfutility.navigation.base.BaseNavItem
import coder.giz.android.yfutility.navigation.base.BaseNavItem.Companion.ITEM_TYPE_ACTIVITY

/**
 * Created by GizFei on 2021/10/30
 */
class ActivityNavAdapter(context: Context) : BaseNavAdapter(context) {

    init {
        mItemClickListener = { item, pos ->
            if (item is ActivityNavItem) {
                mContext.navigate(item.activity.java)
            }
        }
    }

    override fun createNavItemHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseNavHolder<out BaseNavItem, *>? {
        return if (viewType == ITEM_TYPE_ACTIVITY) {
            ActivityNavHolder(ItemNavBinding.inflate(mLayoutInflater, parent, false))
        } else {
            null
        }
    }

}