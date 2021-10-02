package com.giz.android.practice.hencoder.customview.c3_drawText

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.giz.android.practice.R
import com.giz.android.practice.common.DataBindingBaseActivity
import com.giz.android.practice.databinding.ActivityDrawTextBinding

class DrawTextActivity : DataBindingBaseActivity<ActivityDrawTextBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_draw_text

    override fun initView() {
        Log.w("Window", "onCreate: ${window::class.simpleName}[${window}]")
    }
}