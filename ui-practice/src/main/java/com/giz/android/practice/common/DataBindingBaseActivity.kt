package com.giz.android.practice.common

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.giz.android.practice.BR

/**
 * Description of the file
 *
 * Created by GizFei on 2021/3/28
 */
abstract class DataBindingBaseActivity<VDB: ViewDataBinding> : AppCompatActivity(), View.OnClickListener {

    protected lateinit var mBinding: VDB

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()

    open fun setupDataBinding() {
        mBinding.lifecycleOwner = this
        mBinding.setVariable(BR.onClickListener, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())

        setupDataBinding()
        initView()
    }

    override fun onClick(v: View) {

    }

}