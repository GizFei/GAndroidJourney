package coder.giz.android.yfutility.navigation

import coder.giz.android.yfutility.databinding.ItemNavHeaderBinding
import coder.giz.android.yfutility.navigation.base.BaseNavHolder
import coder.giz.android.yfutility.navigation.base.BaseNavItem

/**
 * Created by GizFei on 2021/10/30
 */
class HeaderNavHolder(binding: ItemNavHeaderBinding) : BaseNavHolder<HeaderNavItem, ItemNavHeaderBinding>(binding) {

    override fun bindItem(item: HeaderNavItem) {
        with(mBinding) {
            title = item.title
            // NOTE 不写以下这句可以刷新吗？
//            executePendingBindings()
        }
    }

}