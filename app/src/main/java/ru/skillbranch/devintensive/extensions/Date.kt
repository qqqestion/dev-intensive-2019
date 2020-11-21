package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, unis: TimeUnit): Date {
    var time = this.time
    time += when (unis) {
        TimeUnit.SECOND -> value * SECOND
        TimeUnit.MINUTE -> value * MINUTE
        TimeUnit.HOUR -> value * HOUR
        TimeUnit.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnit {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun Date.humanizeDiff(otherDate: Date = Date()): String {
    val diffSec = abs(time - otherDate.time)
    val diffMin = diffSec / 60 / 1_000
    val diffHou = diffMin / 60
    val diffDay = diffHou / 24
    val isFuture = this > otherDate
    if (diffSec < 1000) {
        return "только что"
    } else if (diffSec < 45_000) {
        return if (isFuture) {
            "через несколько секунд"
        } else {
            "несколько секунд назад"
        }
    } else if (diffSec < 75_000) {
        if (isFuture) {
            return "через минуту"
        } else {
            return "минуту назад"
        }
    } else if (diffMin < 45) {
        return if (isFuture) {
            "через $diffMin ${if (diffMin % 10 < 5 && diffMin % 10 != 0L) "минуты" else "минут"}"
        } else {
            "$diffMin ${if (diffMin % 10 < 5 && diffMin % 10 != 0L) "минуты" else "минут"} назад"
        }
    } else if (diffMin < 75) {
        return if (isFuture) {
            "через час"
        } else {
            "час назад"
        }
    } else if (diffHou < 22) {
        return if (isFuture) {
            "через $diffHou ${if (diffHou % 10 < 5 && diffHou % 10 != 0L) "часа" else "часов"}"
        } else {
            "$diffHou ${if (diffHou % 10 < 5 && diffHou % 10 != 0L) "часа" else "часов"} назад"
        }
    } else if (diffHou < 26) {
        return if (isFuture) {
            "через день"
        } else {
            "день назад"
        }
    } else if (diffDay < 360) {
        return if (isFuture) {
            "через $diffDay ${if (diffDay % 10 < 5 && diffDay % 10 != 0L) "дня" else "дней"}"
        } else {
            "$diffDay ${if (diffDay % 10 < 5 && diffDay % 10 != 0L) "дня" else "дней"} назад"
        }
    } else {
        return if (isFuture) {
            "более чем через год"
        } else {
            "более года назад"
        }
    }
}
