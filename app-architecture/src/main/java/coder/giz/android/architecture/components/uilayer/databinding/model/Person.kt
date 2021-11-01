package coder.giz.android.architecture.components.uilayer.databinding.model

/**
 * Created by GizFei on 2021/10/30
 */
data class Person(
    var name: String,
    var sex: Sex,
    var age: Int,
) {
    val isAdult: Boolean get() = age >= 18
}

enum class Sex {
    FEMALE,
    MALE
}

fun newPersonLittleBoy() = Person("LittleBoy", Sex.MALE, 12)
fun newPersonLittleGirl() = Person("LittleGirl", Sex.FEMALE, 10)