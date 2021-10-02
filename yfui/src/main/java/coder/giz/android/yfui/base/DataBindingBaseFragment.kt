package coder.giz.android.yfui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 使用了DataBinding的Fragment基类。
 *
 * Created by GizFei on 2021/10/1
 */
abstract class DataBindingBaseFragment<VDB: ViewDataBinding> : BaseFragment() {

    private var _binding: VDB? = null
    protected val mBinding: VDB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDataBinding()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    @CallSuper
    open fun initDataBinding() {
        mBinding.lifecycleOwner = this
    }

    abstract fun initView()

}