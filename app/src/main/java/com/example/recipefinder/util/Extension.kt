package com.example.recipefinder.util

fun Int.toHourMinuteFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return when {
        hours > 0 && minutes > 0 -> "$hours hr $minutes min"
        hours > 0 -> "$hours hr"
        else -> "$minutes min"
    }
}
