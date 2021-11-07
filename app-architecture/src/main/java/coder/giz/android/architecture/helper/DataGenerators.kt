package coder.giz.android.architecture.helper

import coder.giz.android.architecture.components.uilayer.databinding.LayoutsAndDatabindingExpressionsActivity
import coder.giz.android.architecture.components.uilayer.databinding.ObservableDataObjectsActivity
import coder.giz.android.architecture.components.uilayer.viewbinding.ViewBindingActivity
import coder.giz.android.yfutility.navigation.ActivityNavItem
import coder.giz.android.yfutility.navigation.HeaderNavItem

/**
 * Created by GizFei on 2021/10/31
 */

internal object DataGenerators {
    val AppArchitectureNavItems = listOf(
        HeaderNavItem("Arch Components > UI layer > View binding"),
        ActivityNavItem("ViewBindingActivity", "使用View binding扩充布局的Activity",
            ViewBindingActivity::class),
        HeaderNavItem("Arch Components > UI layer > Data binding"),
        ActivityNavItem("Layouts and binding expressions", "布局和绑定表达式示例页面",
            LayoutsAndDatabindingExpressionsActivity::class),
        ActivityNavItem("Observable data objects", "使用可观察对象",
            ObservableDataObjectsActivity::class),
    )

    val OperatorSystemList = listOf("Android", "iOS", "Windows", "MacOS", "Linux", "Ubuntu")
    val CompanyProductMap = mapOf(
        "Google" to "Android",
        "Apple" to "iOS",
        "Microsoft" to "Windows",
        "Amazon" to "AmazonWeb"
    )

    val NameOne = Name("Melanie", "Quinn")
    val NameTwo = Name("Nicolette", "Lester")

    data class Name(val firstName: String, val lastName: String)
}