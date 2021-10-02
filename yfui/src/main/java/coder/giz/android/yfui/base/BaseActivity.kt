package coder.giz.android.yfui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

/**
 * Activity通用基类。
 *
 * Created by GizFei on 2021/10/1
 */
open class BaseActivity : AppCompatActivity() {

    protected fun KClass<out AppCompatActivity>.navigate() {
        startActivity(Intent(this@BaseActivity, this.java))
    }

}