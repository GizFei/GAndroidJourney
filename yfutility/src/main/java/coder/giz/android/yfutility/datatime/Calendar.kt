/**
 * java.util.Calendar类相关的扩展函数、扩展属性等。
 *
 * @author GizFei created on 2021/01/20
 */
@file:JvmName("CalendarUtils")

package coder.giz.android.yfutility.datatime

import androidx.annotation.IntRange
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * 获取年份。
 * 取值：如2021。
 */
val Calendar.year: Int get() = this.get(Calendar.YEAR)

/**
 * 获取月份。
 * 取值：0-11。
 * [Calendar.JANUARY] - [Calendar.DECEMBER]
 */
val Calendar.month: Int get() = this.get(Calendar.MONTH)

/**
 * 获取实际生活中使用的月份值。
 * 取值：1-12。
 */
val Calendar.monthActual: Int get() = this.month + 1

/**
 * 获取日期。当月的某一天。
 * 取值：1-31。
 */
val Calendar.day: Int get() = this.get(Calendar.DAY_OF_MONTH)
val Calendar.date: Int get() = this.get(Calendar.DATE)

/**
 * 当天几点。12小时制。
 * 取值：0-11。
 */
val Calendar.hourIn12h: Int get() = this.get(Calendar.HOUR)

/**
 * 当天几点。24小时制。
 * 取值：0-23。
 */
val Calendar.hourIn24h: Int get() = this.get(Calendar.HOUR_OF_DAY)

/**
 * 分钟。
 * 取值：0-59。
 */
val Calendar.minute: Int get() = this.get(Calendar.MINUTE)

/**
 * 秒数。
 * 取值：0-59。
 */
val Calendar.second: Int get() = this.get(Calendar.SECOND)

/**
 * 毫秒数。
 * 取值：0-999。
 */
val Calendar.millisecond: Int get() = this.get(Calendar.MILLISECOND)

/**
 * 一周里的第几天。
 * 取值如下：
 * [Calendar.MONDAY]
 * [Calendar.TUESDAY]
 * [Calendar.WEDNESDAY]
 * [Calendar.THURSDAY]
 * [Calendar.FRIDAY]
 * [Calendar.SATURDAY]
 * [Calendar.SUNDAY]
 */
val Calendar.dayOfWeek: Int get() = this.get(Calendar.DAY_OF_WEEK)

/**
 * 当年的第几天。
 * 取值：1-366。
 */
val Calendar.dayOfYear: Int get() = this.get(Calendar.DAY_OF_YEAR)

/**
 * 当月的第几周。
 * 取值：1-5。
 */
val Calendar.weekOfMonth: Int get() = this.get(Calendar.WEEK_OF_MONTH)

/**
 * 当年的第几周。
 * 取值：1-53。
 */
val Calendar.weekOfYear: Int get() = this.get(Calendar.WEEK_OF_YEAR)

/**
 * 设置年份为某一年。
 * @param year 年份。
 */
infix fun Calendar.yearTo(year: Int): Calendar = this.apply { set(Calendar.YEAR, year) }

/**
 * 设置月份为某一月。
 * @param month 月份。
 */
infix fun Calendar.monthTo(
    @IntRange(from = Calendar.JANUARY.toLong(), to = Calendar.DECEMBER.toLong())
    month: Int
): Calendar = this.apply { set(Calendar.MONTH, month) }

/**
 * 设置为当月的某号。
 * @param day 某号。1-31
 */
infix fun Calendar.dayTo(
    @IntRange(from = 1L, to = 31L)
    day: Int
): Calendar = this.apply { set(Calendar.DAY_OF_MONTH, day) }

/**
 * 设置小时。
 * @param hour 几点。
 */
infix fun Calendar.hourTo(
    @IntRange(from = 0L, to = 23L)
    hour: Int
): Calendar = this.apply { set(Calendar.HOUR_OF_DAY, hour) }

/**
 * 设置分钟。
 * @param minute 分钟数
 */
infix fun Calendar.minuteTo(
    @IntRange(from = 0L, to = 59L)
    minute: Int
): Calendar = this.apply { set(Calendar.MINUTE, minute) }

/**
 * 设置秒数。
 * @param second 秒数
 */
infix fun Calendar.secondTo(
    @IntRange(from = 0L, to = 59L)
    second: Int
): Calendar = this.apply { set(Calendar.SECOND, second) }

/**
 * 设置毫秒数。
 * @param millis 毫秒数。
 */
infix fun Calendar.millisTo(
    @IntRange(from = 0L, to = 999L)
    millis: Int
): Calendar = this.apply { set(Calendar.MILLISECOND, millis) }

/**
 * 将日期精确到以天为单位
 */
fun Calendar.inDayUnit(): Calendar = this.apply {
    hourTo(0)
    minuteTo(0)
    secondTo(0)
    millisTo(0)
}

/**
 * 以标准（24小时制）格式化字符串表示该[Calendar]
 */
fun Calendar.timeText(): String
        = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault()).format(this.time)