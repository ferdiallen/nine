package com.example.nineintelligence.core

import android.util.Log
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

fun String.toPreferrableFormatDate(): String {
    val parser = ZonedDateTime.parse(this)
    val dateTime = DateTimeFormatter.ofPattern("EEEE,dd MMM yyyy", Locale.getDefault())
    return dateTime.format(parser)
}

fun String.toSimpleDate(): String {
    val parser = ZonedDateTime.parse(this)
    val dateTime = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return dateTime.format(parser)
}

fun calculateTwoDaysBetween(ends: String): List<String> {
    val startTime = ZonedDateTime.now()
    val endsTime = ZonedDateTime.parse(ends)
    val comparisonDateHours = startTime.until(endsTime, ChronoUnit.MINUTES).minutes
    val list = mutableListOf<String>()
    comparisonDateHours.toComponents { days, hours, minutes, _, _ ->
        list.add(days.toString())
        list.add(hours.toString())
        list.add(minutes.toString())
    }
    return list.toList()
}