package com.giz.android.practice

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.giz.android.practice.common.DataBindingBaseActivity
import com.giz.android.practice.databinding.ActivityUiPracticeMainBinding
import com.giz.android.practice.hencoder.customview.c1_DrawBasics.DrawBasicsActivity
import com.giz.android.practice.hencoder.customview.c3_drawText.DrawTextActivity
import com.giz.android.practice.hencoder.hencoderpracticedraw2.HenCoderPracticeDraw2MainActivity
import com.giz.android.practice.hencoder.hencoderpracticedraw3.HenCoderPracticeDraw3MainActivity
import com.giz.android.practice.hencoder.hencoderpracticedraw4.HenCoderPracticeDraw4MainActivity
import com.giz.android.practice.hencoder.viewlayout.SquareImageViewActivity
import com.giz.android.practice.window.WindowTestActivity
import kotlin.reflect.KClass

class UiPracticeMainActivity : DataBindingBaseActivity<ActivityUiPracticeMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_ui_practice_main

    override fun initView() {
        Log.w("Window", "onCreate: ${window::class.simpleName}[${window}]")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_draw_basics -> DrawBasicsActivity::class.navigation()
            R.id.btn_draw_text -> DrawTextActivity::class.navigation()
            R.id.btn_square_image_view -> SquareImageViewActivity::class.navigation()
            R.id.btn_paint_practice -> HenCoderPracticeDraw2MainActivity::class.navigation()
            R.id.btn_draw_text_practice -> HenCoderPracticeDraw3MainActivity::class.navigation()
            R.id.btn_canvas_practice -> HenCoderPracticeDraw4MainActivity::class.navigation()
            R.id.btn_window_test -> WindowTestActivity::class.navigation()
        }
    }

    private fun KClass<out AppCompatActivity>.navigation() {
        startActivity(Intent(this@UiPracticeMainActivity, this.java))
    }

}