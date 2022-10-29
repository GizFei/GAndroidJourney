package coder.giz.publicapis.currents.model

/**
 * Created by GizFei on 2022/10/29
 */


/**
 * 语言
 */
enum class Language(val value: String) {
    EN("en"),
    ZH("zh"),
    ;

    companion object {
        fun fromValue(value: String?): Language {
            return if (value.isNullOrEmpty()) {
                EN
            } else {
                values().find { it.value == value } ?: EN
            }
        }
    }
}

enum class Status(private val value: String) {
    OK("ok"),
    ERROR("error"),
    ;

    companion object {
        fun fromValue(value: String?): Status {
            return if (value.isNullOrEmpty()) {
                ERROR
            } else {
                values().find { it.value == value } ?: ERROR
            }
        }
    }
}

data class NewsListResult(
    val status: String,
    val news: List<News>,
) {
    val enumStatus: Status get() = Status.fromValue(status)
}

data class News(
    val title: String,
    val image: String,
    val published: Boolean,
)