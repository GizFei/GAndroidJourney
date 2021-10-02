package coder.giz.android.yfui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

/**
 * 使用了DataBinding + ViewModel的Fragment基类。
 *
 * Created by GizFei on 2021/10/1
 */
abstract class MvvmBaseFragment<VDB: ViewDataBinding, VM: ViewModel> : BaseFragment() {

    private var _binding: VDB? = null
    protected val mBinding: VDB get() = _binding!!
    protected lateinit var mViewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel()
        initDataBinding()
        initView()
        subscribeToViewModel()
        loadDataOnce()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
     * 在 [onViewCreated] 中加载数据，所以只会加载一次。
     */
    open fun loadDataOnce() { }

}