package coder.giz.android.architecture.components.uilayer.databinding

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.ViewModel
import coder.giz.android.architecture.BR
import coder.giz.android.architecture.R
import coder.giz.android.architecture.databinding.ActivityObservableDataObjectsBinding
import coder.giz.android.architecture.helper.DataGenerator
import coder.giz.android.yfui.base.MvvmBaseActivity
import kotlin.random.Random

/**
 * Created by GizFei on 2021/11/1
 */
class ObservableDataObjectsActivity :
    MvvmBaseActivity<ActivityObservableDataObjectsBinding, ObservableDataObjectsViewModel>(),
    View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.activity_observable_data_objects

    override fun initDataBinding() {
        super.initDataBinding()
        with(mBinding) {
            viewModel = mViewModel
            onClickListener = this@ObservableDataObjectsActivity
        }
    }

    override fun initView() {
        supportActionBar?.title = "Observable data objects"

    }

    override fun getViewModel() = ObservableDataObjectsViewModel()

    override fun subscribeToViewModel() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_change_list -> changeList()
            R.id.btn_change_map -> changeMap()
            R.id.btn_change_observable_user -> changeObservableUser()
            R.id.btn_change_whole_observable_user -> changeWholeObservableUser()
            R.id.btn_change_non_observable_user -> changeNonObservableUser()
        }
    }

    private fun changeList() {
        when (Random.nextInt(3)) {
            0 -> mViewModel.operatorSystemList.add("NewItem ${Random.nextFloat()}")
            1 -> mViewModel.operatorSystemList.removeLast()
            2 -> mViewModel.operatorSystemList[1] = "ChangeItem[1] ${Random.nextFloat()}"
        }
    }

    private fun changeMap() {
        when (Random.nextInt(3)) {
            0 -> mViewModel.companyProductMap["NewKey ${Random.nextFloat()}"] = "NewValue ${Random.nextFloat()}"
            1 -> mViewModel.companyProductMap.removeAt(0)
            2 -> mViewModel.companyProductMap["Microsoft"] = "Windows ${if (Random.nextBoolean()) 10 else 11}"
        }
    }

    private fun changeObservableUser() {
        when (Random.nextInt(3)) {
            0 -> mViewModel.observableUser.firstName = "${DataGenerator.NameOne.firstName} ${Random.nextFloat()}"
            1 -> mViewModel.observableUser.lastName = "${DataGenerator.NameOne.lastName} ${Random.nextFloat()}"
            2 -> mViewModel.observableUser.age = Random.nextInt(8, 18)
        }
    }

    private fun changeWholeObservableUser() {
        // NOTE 改变整个对象后，原来视图绑定的对象就不会生效了。所以可观察对象应该是 val 的。
        mViewModel.observableUser = ObservableUser().apply {
            firstName = "RandomFirstName ${Random.nextFloat()}"
            lastName = "RandomLastName ${Random.nextFloat()}"
            age = Random.nextInt(5, 25)
        }
        mBinding.executePendingBindings()
    }

    private fun changeNonObservableUser() {
        when (Random.nextInt(3)) {
            0 -> {
                mViewModel.nonObservableUser.firstName = "${DataGenerator.NameTwo.firstName} ${Random.nextFloat()}"
            }
            1 -> {
                mViewModel.nonObservableUser.lastName = "${DataGenerator.NameTwo.lastName} ${Random.nextFloat()}"
            }
            2 -> {
                mViewModel.nonObservableUser.age = Random.nextInt(10, 24)
            }
        }
    }

}

class ObservableDataObjectsViewModel : ViewModel() {
    val operatorSystemList = ObservableArrayList<String>().apply {
        addAll(DataGenerator.OperatorSystemList)
    }
    val companyProductMap = ObservableArrayMap<String, String>().apply {
        putAll(DataGenerator.CompanyProductMap)
    }
    // 为了测试，使用var。实际使用时应定义成val。
    var observableUser = ObservableUser()
    val nonObservableUser = NonObservableUser()
}

class ObservableUser : BaseObservable() {
    // 生成fieldId: BR.firstName
    @get:Bindable
    var firstName = DataGenerator.NameOne.firstName
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    // 生成fieldId: BR.lastName
    @get:Bindable
    var lastName = DataGenerator.NameOne.lastName
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }

    // 生成fieldId: BR.age
    @get:Bindable
    var age = 15
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }
}

class NonObservableUser {
    var firstName = DataGenerator.NameTwo.firstName
    var lastName = DataGenerator.NameTwo.lastName
    var age = 16
}