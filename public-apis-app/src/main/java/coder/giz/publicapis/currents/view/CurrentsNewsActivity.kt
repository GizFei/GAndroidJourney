package coder.giz.publicapis.currents.view

import android.view.View
import androidx.activity.viewModels
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.publicapis.R
import coder.giz.publicapis.currents.viewmodel.CurrentsNewsViewModel
import coder.giz.publicapis.databinding.ActivityCurrentsNewsBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Created by GizFei on 2022/10/29
 */
class CurrentsNewsActivity : DataBindingBaseActivity<ActivityCurrentsNewsBinding>(), View.OnClickListener {

    private val mViewModel: CurrentsNewsViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_currents_news

    override fun initView() {
        mBinding.run {
            onClickListener = this@CurrentsNewsActivity
            isLoading = mViewModel.loadingLiveData
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_fetch -> {
                fetchNews()
            }
        }
    }

    override fun subscribeToViewModel() {
        mViewModel.snackbarMsgLiveData.observe(this) { msg ->
            msg?.let {
                Snackbar.make(this, mBinding.root, it, 2000).show()
                mViewModel.onSnackbarShown()
            }
        }
    }

    private fun fetchNews() {
        mViewModel.getLatestNews()
    }

}