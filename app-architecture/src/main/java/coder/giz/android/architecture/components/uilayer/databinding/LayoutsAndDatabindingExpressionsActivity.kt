package coder.giz.android.architecture.components.uilayer.databinding

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coder.giz.android.architecture.R
import coder.giz.android.architecture.components.uilayer.databinding.model.newPersonLittleBoy
import coder.giz.android.architecture.components.uilayer.databinding.model.newPersonLittleGirl
import coder.giz.android.architecture.databinding.ActivityLayoutsAndDatabindingExpressionsBinding
import coder.giz.android.architecture.helper.DataGenerators
import coder.giz.android.yfui.base.MvvmBaseActivity

/**
 * [布局和绑定表达式](https://developer.android.google.cn/topic/libraries/data-binding/expressions)
 */
class LayoutsAndDatabindingExpressionsActivity :
    MvvmBaseActivity<ActivityLayoutsAndDatabindingExpressionsBinding, LayoutsAndDatabindingExpressionsViewModel>(),
    View.OnClickListener {

    private var mPersonIdx = 0

    override fun getLayoutId(): Int = R.layout.activity_layouts_and_databinding_expressions

    override fun getViewModel() = LayoutsAndDatabindingExpressionsViewModel()

    override fun initDataBinding() {
        super.initDataBinding()
        with(mBinding) {
            onClickListener = this@LayoutsAndDatabindingExpressionsActivity
            person = newPersonLittleBoy()
            aList = DataGenerators.OperatorSystemList
            aMap = DataGenerators.CompanyProductMap
            viewModel = mViewModel
        }
    }

    override fun initView() {
        supportActionBar?.title = "Layouts And Databinding Expressions"
    }

    override fun subscribeToViewModel() {
        mViewModel.toastText.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_nc_nullable_str -> {
                // Note 不加 mBinding.executePendingBindings()，可以更新吗？——可以✅。
                mBinding.nullableStr = null
            }
            R.id.btn_nc_nonnull_str -> {
                mBinding.nullableStr = "NonNull String Text"
            }
            R.id.btn_toggle_person_info -> {
                togglePersonInfo()
            }
            R.id.btn_count_inc -> {
                mViewModel.incCount()
            }
        }
    }

    private fun togglePersonInfo() {
        if (mPersonIdx == 0) {
            mPersonIdx = 1
            mBinding.person = newPersonLittleGirl()
        } else {
            mPersonIdx = 0
            mBinding.person = newPersonLittleBoy()
        }
    }

}

class LayoutsAndDatabindingExpressionsViewModel : ViewModel() {
    val count = ObservableInt(0)
    val countLiveData = MutableLiveData(0)
    val toastText = MutableLiveData<String>()

    fun incCount() {
        count.set(count.get() + 1)
        countLiveData.value = (countLiveData.value ?: 0) + 1
    }

    fun listToString(list: List<String>) {
        list.joinToString()
    }

    fun showToast(isChecked: Boolean) {
        toastText.value = if (isChecked) "Checked!" else "Unchecked!"
    }
}