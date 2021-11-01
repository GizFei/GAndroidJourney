package coder.giz.android.architecture.components.uilayer.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import coder.giz.android.architecture.R
import coder.giz.android.architecture.databinding.ActivityDatabindingWhiteboardBinding

/**
 * 数据绑定白板练习页面。
 *
 * Created by GizFei on 2021/10/25
 */
class DataBindingWhiteboardActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDatabindingWhiteboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_whiteboard)
    }
}