package coder.giz.android.yfui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 使用了DataBinding的Activity基类。
 *
 * Created by GizFei on 2021/10/1
 */
abstract class DataBindingBaseActivity<VDB: ViewDataBinding> : BaseActivity() {

    protected lateinit var mBinding: VDB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initDataBinding()
        initView()
        subscribeToViewModel()
        loadDataOnce()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    @CallSuper
    open fun initDataBinding() {
        mBinding.lifecycleOwner = this
    }

    abstract fun initView()

    open fun subscribeToViewModel() {}

    open fun loadDataOnce() {}

}