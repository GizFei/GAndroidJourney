package coder.giz.android.network.ui

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import coder.giz.android.network.R
import coder.giz.android.network.databinding.ActivityBehaviorSubjectPracticeBinding
import coder.giz.android.network.practice.BehaviorSubjectPractice
import coder.giz.android.yfui.base.DataBindingBaseActivity

/**
 * Created by GizFei on 2021/12/12
 */
class BehaviorSubjectPracticeActivity :
    DataBindingBaseActivity<ActivityBehaviorSubjectPracticeBinding>(),
    View.OnClickListener {

    private val mViewModel: BehaviorSubjectPracticeViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_behavior_subject_practice

    override fun initView() {
        mBinding.onClickListener = this
        mBinding.viewModel = mViewModel

        subscribeToViewModel()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_emit_next -> mViewModel.setEmitType(BehaviorSubjectPractice.SubjectEmitType.NEXT)
            R.id.btn_emit_token_expired -> mViewModel.setEmitType(BehaviorSubjectPractice.SubjectEmitType.ERROR_TOKEN_EXPIRED)
        }
    }

    private fun subscribeToViewModel() {

    }

}

class BehaviorSubjectPracticeViewModel : ViewModel() {
    private val mBehaviorSubjectPractice = BehaviorSubjectPractice()

    val behaviorSubjectMessage = mBehaviorSubjectPractice.messageLiveData

    fun postRequest() {
        mBehaviorSubjectPractice.postRequest()
    }

    fun postRequestStepByStep() {
        mBehaviorSubjectPractice.postRequestStepByStep()
    }

    fun postRequestEmitter() {
        mBehaviorSubjectPractice.postRequestEmitter()
    }

    fun setEmitType(emitType: BehaviorSubjectPractice.SubjectEmitType) {
        mBehaviorSubjectPractice.nextSubjectEmitType = emitType
    }
}