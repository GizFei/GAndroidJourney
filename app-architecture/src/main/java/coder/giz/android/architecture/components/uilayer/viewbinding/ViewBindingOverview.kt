package coder.giz.android.architecture.components.uilayer.viewbinding

import coder.giz.android.architecture.AppArchitectureSection

/**
 * Created By GizFei on 2021/10/7
 */
@AppArchitectureSection("ArchitectureComponents.UiLayerLibraries.ViewBinding")
class ViewBindingOverview {
    companion object {
        const val ModuleBuildGradle = """
            android {
                ...
                buildFeatures {
                    viewBinding true
                }
            }
        """
    }
}