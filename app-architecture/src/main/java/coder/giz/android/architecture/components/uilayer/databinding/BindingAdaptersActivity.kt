package coder.giz.android.architecture.components.uilayer.databinding

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import coder.giz.android.architecture.R
import coder.giz.android.architecture.databinding.ActivityBindingAdaptersBinding
import coder.giz.android.yfui.base.MvvmBaseActivity

/**
 * Created by GizFei on 2021/11/21
 */
class BindingAdaptersActivity : MvvmBaseActivity<ActivityBindingAdaptersBinding, BindingAdaptersViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_binding_adapters

    override fun getViewModel() = BindingAdaptersViewModel()

    override fun initDataBinding() {
        super.initDataBinding()
        mBinding.viewModel = mViewModel
    }

    override fun initView() {

    }

    override fun subscribeToViewModel() {

    }
}

class BindingAdaptersViewModel : ViewModel() {

    val isDarkMode = ObservableBoolean(false)

    fun switchDarkMode() {
        isDarkMode.set(isDarkMode.get().not())
    }

}