package coder.giz.android.architecture

/**
 * Android官方文档学习之“App architecture”，App架构。
 *
 * 官网地址：[App architecture introduction](https://developer.android.google.cn/topic/architecture)
 */
object Readme {
    /**
     * [coder.giz.android.architecture.guide] 包
     */
    val Pkg_Guide: String = GuideToAppArchitecture.sectionPath

    /**
     * [coder.giz.android.architecture.components] 包
     */
    val Pkg_Components: String = ArchitectureComponents.sectionPath
}

class Section(val title: String, val subSections: List<Section> = emptyList())

interface SectionObject {
    val section: Section
    val parent: SectionObject? get() = null
    val sectionPath: String get() {
        val p = parent
        return if (p != null) {
            "/${p.section.title}/${section.title}"
        } else {
            "/${section.title}"
        }
    }
}

object GuideToAppArchitecture : SectionObject {
    override val section = Section(SectionTitles.Guide_To_App_Architecture)
}

object ArchitectureComponents : SectionObject {
    override val section = Section(
        SectionTitles.Architecture_Components,
        listOf(
            UiLayerLibraries.section,
            DataLayerLibraries.section
        )
    )

    object UiLayerLibraries : SectionObject {
        override val section = Section(
            SectionTitles.Ui_Layer_Libraries,
            listOf(
                ViewBinding.section
            )
        )
        override val parent = ArchitectureComponents


        object ViewBinding : SectionObject {
            override val section = Section(SectionTitles.View_Binding)
            override val parent = UiLayerLibraries
        }

    }

    object DataLayerLibraries : SectionObject {
        override val section = Section(SectionTitles.Data_Layer_Libraries)
        override val parent = ArchitectureComponents
    }
}


object SectionTitles {
    const val Guide_To_App_Architecture = "Guide to app architecture"

    const val Architecture_Components = "Architecture components"
    const val Ui_Layer_Libraries = "UI Layer Libraries"
    const val Data_Layer_Libraries = "Data Layer Libraries"
    const val View_Binding = "View Binding"
}

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
annotation class AppArchitectureSection(
    val section: String
)