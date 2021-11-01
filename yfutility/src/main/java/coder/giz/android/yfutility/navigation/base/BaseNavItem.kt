package coder.giz.android.yfutility.navigation.base

import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

/**
 * Created by GizFei on 2021/10/30
 */
open class BaseNavItem(
    val title: String,
    val description: String,
    val type: Int
) {
    companion object {
        const val ITEM_TYPE_HEADER = 0
        const val ITEM_TYPE_ACTIVITY = 1
    }
}

