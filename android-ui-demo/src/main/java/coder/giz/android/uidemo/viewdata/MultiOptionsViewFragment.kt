package coder.giz.android.uidemo.viewdata

import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.FragmentMultiOptionsViewBinding
import coder.giz.android.yfui.base.DataBindingBaseFragment
import coder.giz.android.yfui.helper.setImageResourceAndVisible
import coder.giz.android.yfui.helper.setTextAndVisible

/**
 * Created by GizFei on 2022/9/9
 */
class MultiOptionsViewFragment : DataBindingBaseFragment<FragmentMultiOptionsViewBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_multi_options_view

    override fun initView() {
        val viewData: MultiOptionsViewData = MultiOptionsViewData(
            getString(R.string.app_name),
            R.drawable.avatar,
            getString(R.string.app_name),
            R.drawable.avatar
        )

        mBinding.run {
            tvOption1.setTextAndVisible(viewData.optionOneText)
            ivOption1.setImageResourceAndVisible(viewData.optionOneImageId)
            tvOption2.setTextAndVisible(viewData.optionTwoText)
            ivOption2.setImageResourceAndVisible(viewData.optionTwoImageId)
            tvOption3.setTextAndVisible(viewData.optionThreeText)
            ivOption3.setImageResourceAndVisible(viewData.optionThreeImageId)
        }
    }
}