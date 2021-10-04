/**
 * Date相关的扩展函数、扩展属性等。
 *
 * Created by GizFei on 2021/1/24
 */
package coder.giz.android.yfutility.datatime

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * 获取时间为[Date]的[Calendar]
 */
val Date.calendar: Calendar get() = Calendar.getInstance().also { it.time = this }

/**
 * 获取时间为[Date]的[Calendar]
 * @param timeZone 时区
 */
fun Date.toCalendar(timeZone: TimeZone = TimeZone.getDefault()): Calendar
        = Calendar.getInstance(timeZone).apply { time = this@toCalendar }

/**
 * 将[Date]格式化成字符串
 * @param pattern 模式
 * @param timeZone 时区。影响解析时间的结果
 * @param locale 地区。影响字符串语言
 */
fun Date.toFormatString(
    pattern: String?,
    timeZone: TimeZone = TimeZone.getDefault(),
    locale: Locale = Locale.getDefault()
): String {
    pattern ?: return ""

    return SimpleDateFormat(pattern, locale).apply { this.timeZone = timeZone }.format(this)
}