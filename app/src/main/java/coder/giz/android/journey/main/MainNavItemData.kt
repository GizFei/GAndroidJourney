package coder.giz.android.journey.main

import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

/**
 * Created by GizFei on 2021/10/1
 */
data class MainNavItemData(
    val title: String,
    val description: String,
    val activity: KClass<out AppCompatActivity>
)