package coder.giz.android.uidemo.viewpager

import android.graphics.Color
import android.os.Bundle
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.FragmentPagerBinding
import coder.giz.android.yfui.base.DataBindingBaseFragment
import kotlin.random.Random

/**
 * Created by GizFei on 2022/9/4
 */
class PagerFragment : DataBindingBaseFragment<FragmentPagerBinding>() {

    companion object {
        private const val ARG_POSITION = "Arg.Position"
        private const val ARG_DATA = "Arg.Data"
        private const val DEFAULT_POS = -1

        fun newInstance(pos: Int, data: String): PagerFragment {
            val arguments = Bundle().apply {
                putInt(ARG_POSITION, pos)
                putString(ARG_DATA, data)
            }
            return PagerFragment().apply {
                this.arguments = arguments
            }
        }
    }

    private var mPos = DEFAULT_POS
    private var mData: String = ""

    var callback: Callback? = null

    override fun getLayoutId(): Int = R.layout.fragment_pager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extractArguments()
    }

    private fun extractArguments() {
        mPos = arguments?.getInt(ARG_POSITION, DEFAULT_POS) ?: DEFAULT_POS
        mData = arguments?.getString(ARG_DATA) ?: ""
    }

    override fun initView() {
        val randomColor = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        mBinding.cardView.setCardBackgroundColor(randomColor)
        mBinding.ivClose.setOnClickListener {
            callback?.onClose(mData)
        }
        mBinding.textView.text = mData
    }

    interface Callback {
        fun onClose(data: String)
    }

}