package coder.giz.android.yfutility.navigation.base

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by GizFei on 2021/10/30
 */
abstract class BaseNavHolder<T: BaseNavItem, VDB: ViewDataBinding>(
    protected val mBinding: VDB
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(item: BaseNavItem) {
        bindItem(item as T)
    }

    protected abstract fun bindItem(item: T)

}