package com.giz.android.practice.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/28
 */
abstract class DataBindingCommonFragment<VDB: ViewDataBinding> : Fragment() {

    private var _binding: VDB? = null
    protected val mBinding: VDB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner
        initView()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun initView() {}

}