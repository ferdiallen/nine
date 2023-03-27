package com.example.nineintelligence.core

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toPreferrableFormatDate(): String {
    val parser = ZonedDateTime.parse(this)
    val dateTime = DateTimeFormatter.ofPattern("EEEE,dd MMM yyyy",Locale.getDefault())
    return dateTime.format(parser)
}

fun String.toSimpleDate():String{
    val parser = ZonedDateTime.parse(this)
    val dateTime = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return dateTime.format(parser)
}