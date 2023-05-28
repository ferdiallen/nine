package com.example.nineintelligence.core

import android.util.Log
import com.example.nineintelligence.domain.models.TakenTryOutModel
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

fun String.isTryoutOver(): Boolean {
    val currentTime = ZonedDateTime.now()
    val targetTime = ZonedDateTime.parse(this)
    return currentTime > targetTime
}

fun List<TakenTryOutModel>.nearestTryoutSchedule(): TakenTryOutModel? {
    val currentDate = ZonedDateTime.now()
    val tryoutList = this.find {
        val eachDateTime = it.tryoutDetails?.startsAt
        val parser = ZonedDateTime.parse(eachDateTime)
        val res = currentDate.until(parser, ChronoUnit.DAYS)
        res <= 1
    }
    return tryoutList
}