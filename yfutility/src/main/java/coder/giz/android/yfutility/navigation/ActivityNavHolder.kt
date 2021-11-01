package coder.giz.android.yfutility.navigation

import coder.giz.android.yfutility.databinding.ItemNavBinding
import coder.giz.android.yfutility.navigation.base.BaseNavHolder
import coder.giz.android.yfutility.navigation.base.BaseNavItem

/**
 * Created by GizFei on 2021/10/31
 */
class ActivityNavHolder(binding: ItemNavBinding) :
    BaseNavHolder<ActivityNavItem, ItemNavBinding>(binding) {

    override fun bindItem(item: ActivityNavItem) {
        with(mBinding) {
            this.item = item
            // Note 不调用以下语句可以刷新吗。——可以。
//            executePendingBindings()
        }
    }

}