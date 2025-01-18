package com.example.recipefinder.util

import android.text.format.DateUtils

fun Int.toHourMinuteFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return when {
        hours > 0 && minutes > 0 -> "$hours hr $minutes min"
        hours > 0 -> "$hours hr"
        else -> "$minutes min"
    }
}


fun Long.getRelativeTimeSpanString(): String {
    return DateUtils.getRelativeTimeSpanString(
        /* time = */ this,
        /* now = */ System.currentTimeMillis(),
        /* minResolution = */ DateUtils.MINUTE_IN_MILLIS,
        /* flags = */ DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString()
}
