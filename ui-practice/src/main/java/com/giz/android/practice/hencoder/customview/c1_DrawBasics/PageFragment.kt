package com.giz.android.practice.hencoder.customview.c1_DrawBasics

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import com.giz.android.practice.R
import com.giz.android.practice.common.DataBindingCommonFragment
import com.giz.android.practice.databinding.FragmentPageBinding

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/28
 */
open class PageFragment : DataBindingCommonFragment<FragmentPageBinding>() {

    companion object {
        private const val ARG_LAYOUT = "LayoutArg"

        fun newInstance(@LayoutRes layout: Int) = PageFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_LAYOUT, layout)
            }
        }
    }

    private var mLayout: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mLayout = arguments?.getInt(ARG_LAYOUT) ?: -1
    }

    override fun getLayoutId(): Int = R.layout.fragment_page

    override fun initView() {
        if (mLayout != -1) {
            mBinding.viewStub.viewStub?.run {
                layoutResource = mLayout
                inflate()
            }
        }
    }

}