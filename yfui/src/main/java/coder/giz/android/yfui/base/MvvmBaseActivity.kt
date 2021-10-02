package coder.giz.android.yfui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

/**
 * 使用了DataBinding + ViewModel的Activity基类。
 *
 * Created by GizFei on 2021/10/1
 */
abstract class MvvmBaseActivity<VDB: ViewDataBinding, VM: ViewModel> : BaseActivity() {

    protected lateinit var mBinding: VDB
    protected lateinit var mViewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = getViewModel()
        initDataBinding()
        initView()
        subscribeToViewModel()
        loadDataOnce()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): VM

    @CallSuper
    open fun initDataBinding() {
        mBinding.lifecycleOwner = this
    }

    abstract fun initView()

    /**
     * 订阅 [ViewModel]。
     */
    abstract fun subscribeToViewModel()

    /**
     * 在 [onCreate] 中加载数据，所以只会加载一次。
     */
    open fun loadDataOnce() { }

}