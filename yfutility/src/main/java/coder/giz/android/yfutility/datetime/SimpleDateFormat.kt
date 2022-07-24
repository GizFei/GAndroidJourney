/**
 * java.text.SimpleDateFormat类的相关扩展函数、扩展属性等。
 *
 * Created by GizFei on 2021/1/21
 */
@file:JvmName("SimpleDateFormatUtils")

package coder.giz.android.yfutility.datetime

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class DateFormatPattern(val pattern: String) {
    /**
     * 4位数年份的末尾2位。
     * 2021 -> 21
     * 1995 -> 95
     */
    YEAR_SHORT("yy"),

    /**
     * 4位数完整年份。
     * 如：2021、1995。
     */
    YEAR_LONG("yyyy"),

    /**
     * 标准年份。
     */
    YEAR(YEAR_LONG),

    /**
     * 数字月份。1-12，不补0。
     */
    MONTH_SHORT("M"),

    /**
     * 数字月份。01-12，补0
     */
    MONTH_LONG("MM"),

    /**
     * 标准月份。
     */
    MONTH(MONTH_LONG),

    /**
     * 单词缩写月份。如：一月，Jan，十二月
     */
    MONTH_ABBR("MMM"),

    /**
     * 单词全称月份。如：一月，January，十二月
     */
    MONTH_FULL("MMMM"),

    /**
     * 当月几号。1-31，不补0。
     */
    DAY_SHORT("d"),

    /**
     * 当月几号。01-31，补0。
     */
    DAY_LONG("dd"),

    /**
     * 标准日数。
     */
    DAY(DAY_LONG),

    /**
     * 12小时制。不补0。1-12。
     * 凌晨0点：12:00 AM
     * 上午8点：8:00 AM
     * 中午12点：12:00 PM
     * 下午7点：7:00 PM
     */
    HOUR_12h_1based_SHORT("h"),

    /**
     * 12小时制。补0。01-12。
     * @see HOUR_12h_1based_SHORT
     */
    HOUR_12h_1based_LONG("hh"),

    /**
     * 12小时制。不补0，
     * 0-11。
     * 凌晨0点：0:00 AM
     * 上午8点：8:00 AM
     * 中午12点：0:00 PM
     * 下午7点：7:00 PM
     */
    HOUR_12h_0based_SHORT("K"),

    /**
     * 12小时制。补0，00-11。
     * @see HOUR_12h_0based_SHORT
     */
    HOUR_12h_0based_LONG("KK"),

    /**
     * 24小时制。不补0。
     * 1-24。
     * 凌晨0点：24:00
     * 上午8点：8:00
     * 中午12点：12:00
     * 下午7点：19:00
     */
    HOUR_24h_1based_SHORT("k"),

    /**
     * 24小时制。补0。1-24。
     * @see HOUR_24h_1based_SHORT
     */
    HOUR_24h_1based_LONG("kk"),

    /**
     * 24小时制。不补0。
     * 0-23。
     * 凌晨0点：0:00
     * 上午8点：8:00
     * 中午12点：12:00
     * 下午7点：19:00
     */
    HOUR_24h_0based_SHORT("H"),

    /**
     * 24小时制。补0。
     * 00-23。
     * @see HOUR_24h_0based_SHORT
     */
    HOUR_24h_0based_LONG("HH"),

    HOUR(HOUR_12h_1based_LONG),

    /**
     * 分钟数。不补0。
     * 0-59
     */
    MINUTE_SHORT("m"),

    /**
     * 分钟数。补0。
     * 00-59
     */
    MINUTE_LONG("mm"),

    MINUTE(MINUTE_LONG),

    /**
     * 秒数。不补0。
     * 0-59
     */
    SECOND_SHORT("s"),

    /**
     * 秒数。补0。
     * 00-59
     */
    SECOND_LONG("ss"),

    SECOND(SECOND_LONG),

    /**
     * 上午、下午。
     * 如：AM/PM、上午/下午
     */
    AM_PM("a"),

    /**
     * 星期缩写。
     * 如：Wed、星期三
     */
    WEEK_ABBR("EE"),

    /**
     * 星期全称。
     * 如：Wednesday、星期三
     */
    WEEK_FULL("EEEE"),

    WEEK(WEEK_ABBR),

    /**
     * 最常用的日期格式：年-月-日 时:分:秒
     * 24小时制（1-24）
     */
    COMMON("yyyy-MM-dd kk:mm:ss");

    constructor(enumPattern: DateFormatPattern): this(enumPattern.pattern)

    /**
     * 将[Date]格式化成字符串。
     * @param inDate 日期
     * @param locale 地区。决定了输出字符串的语言。
     * @return 格式化日期字符串
     */
    fun format(inDate: Date, locale: Locale = Locale.getDefault()): String = SimpleDateFormat(pattern, locale).format(inDate)

    /**
     * 将字符串解析成 [Date]
     * @param locale 地区。字符串语言。
     * @return 正确解析的[Date]。如果解析错误，则返回当前时间。
     */
    fun parse(src: String, locale: Locale = Locale.getDefault()): Date = try {
        SimpleDateFormat(pattern, locale).parse(src) ?: Date()
    } catch (e: ParseException) {
        Date()
    }

    /**
     * 将字符串解析成 [Date]
     * @return 正确解析的[Date]。如果解析错误，则返回空。
     */
    fun parseOrNull(src: String, locale: Locale = Locale.getDefault()): Date? = try {
        SimpleDateFormat(pattern, locale).parse(src)
    } catch (e: ParseException) {
        null
    }
}

@JvmOverloads
fun yearMonthDay(separator: Char = '-') = "yyyy${separator}MM${separator}dd"

fun yearMonth() = "MM, yyyy"

fun monthDay(separator: Char = '-') = "MM${separator}dd"

fun monthDayWeek(separator: Char = '-') = "MM${separator}dd EE"

@JvmOverloads
fun hourMinuteIn12(amPm: Boolean = false) = if (amPm) "hh:mm a" else "hh:mm"

@JvmOverloads
fun hourMinuteIn24(amPm: Boolean = false) = if(amPm) "kk:mm a" else "kk:mm"