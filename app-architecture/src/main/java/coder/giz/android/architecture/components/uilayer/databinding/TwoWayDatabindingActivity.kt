package coder.giz.android.architecture.components.uilayer.databinding

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import coder.giz.android.architecture.R
import coder.giz.android.architecture.databinding.ActivityTwoWayDatabindingBinding
import coder.giz.android.architecture.helper.logDatabinding
import coder.giz.android.yfui.base.MvvmBaseActivity
import coder.giz.android.yfutility.components.toast

class TwoWayDatabindingActivity :
    MvvmBaseActivity<ActivityTwoWayDatabindingBinding, TwoWayDatabindingViewModel>(),
    View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.activity_two_way_databinding

    override fun getViewModel() = TwoWayDatabindingViewModel()

    override fun initDataBinding() {
        super.initDataBinding()
        mBinding.onClickListener = this
        mBinding.viewModel = mViewModel
    }

    override fun initView() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_decrease -> mViewModel.decreaseProgress()
            R.id.fab_increase -> mViewModel.increaseProgress()
            R.id.btn_show_label_seekbar_progress -> {
                toast("进度值：${mViewModel.labelSeekBarProgress.get()}")
            }
        }
    }

    override fun subscribeToViewModel() {
        mViewModel.labelSeekBarProgress.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                logDatabinding { "mViewModel.labelSeekBarProgress onPropertyChanged value: ${mViewModel.labelSeekBarProgress.get()}" }
            }
        })
    }
}

class TwoWayDatabindingViewModel : ViewModel() {

    val labelSeekBarProgress = ObservableInt(0)

    fun decreaseProgress() {
        val newProgress = labelSeekBarProgress.get() - 1
        if (newProgress >= 0) {
            labelSeekBarProgress.set(newProgress)
        }
    }

    fun increaseProgress() {
        val newProgress = labelSeekBarProgress.get() + 1
        labelSeekBarProgress.set(newProgress)
    }

}