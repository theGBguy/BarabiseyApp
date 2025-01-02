package io.github.thegbguy.barabiseyapp.utils

import androidx.compose.runtime.Composable
import io.github.thegbguy.date.NepaliDate
import io.github.thegbguy.date.NepaliDateUtils
import io.github.thegbguy.date.NepaliDateUtils.adToBs
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import locale

@Composable
fun NepaliDate.toFormattedDate() = buildString {
    append(convertToLocale(year))
    append(" ")
    append(NepaliDateUtils.getNepaliMonthString(month))
    append(" ")
    append(convertToLocale(day))
}

fun convertToLocale(value: Int): String {
    val listNepaliDigit = listOf("०", "१", "२", "३", "४", "५", "६", "७", "८", "९")

    return if (locale == "ne") {
        val nepInt = StringBuilder()
        value.toDigits().forEach {
            nepInt.append(listNepaliDigit[it])
        }
        nepInt.toString().reversed()
    } else {
        value.toString()
    }
}

fun Int.toDigits(base: Int = 10): List<Int> = sequence {
    var n = this@toDigits
    require(n >= 0)
    while (n != 0) {
        yield(n % base)
        n /= base
    }
}.toList()

fun getInstance(): NepaliDate {
    val localDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return adToBs(
        localDate.year,
        localDate.monthNumber + 1,
        localDate.dayOfMonth
    )
}

@Composable
fun getFormattedNepaliDate(): String {
    val localDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return adToBs(
        localDate.year,
        localDate.monthNumber,
        localDate.dayOfMonth
    ).toFormattedDate()
}

fun getFormattedDate() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).let {
    "${it.year}/${it.monthNumber}/${it.dayOfMonth}"
}

fun getFormattedTime() = Clock.System.now()
    .toLocalDateTime(TimeZone.of("Asia/Kathmandu"))
    .time.format(LocalTime.Format {
        amPmHour(); char(':'); minute(); char(':'); second(); char(' '); amPmMarker("AM", "PM")
    })