package coder.giz.android.uidemo.expandablelistview

/**
 * Created by GizFei on 2021/12/5
 */
object ExpandableListDataGenerator {

    fun getData(): Map<String, List<String>> {
        val crickets = listOf(
            "India",
            "Pakistan",
            "Australia",
            "England",
            "South Africa",
        )
        val footballs = listOf(
            "Brazil",
            "Spain",
            "Germany",
            "Netherlands",
            "Italy",
        )
        val basketballs = listOf(
            "United States",
            "Spain",
            "Argentina",
            "France",
            "Russia",
        )
        return linkedMapOf(
            "CRICKET TEAMS" to crickets,
            "FOOTBALL TEAMS" to footballs,
            "BASKETBALL TEAMS" to basketballs,
        )
    }

}