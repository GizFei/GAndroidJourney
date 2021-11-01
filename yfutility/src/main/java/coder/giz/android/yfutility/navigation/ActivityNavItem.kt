package coder.giz.android.yfutility.navigation

import androidx.appcompat.app.AppCompatActivity
import coder.giz.android.yfutility.navigation.base.BaseNavItem
import kotlin.reflect.KClass

/**
 * Created by GizFei on 2021/10/30
 */
class ActivityNavItem(
    title: String,
    description: String,
    val activity: KClass<out AppCompatActivity>,
) : BaseNavItem(title, description, ITEM_TYPE_ACTIVITY)