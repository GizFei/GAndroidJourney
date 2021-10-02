package com.giz.android.practice.window

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.giz.android.practice.R

/**
 * 测试Android Window相关内容的活动
 */
class WindowTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_window_test)
        // 直接给窗口设置背景
        window.setBackgroundDrawableResource(R.drawable.wallpaper)
        // 内容全屏
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}