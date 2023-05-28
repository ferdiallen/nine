package com.example.nineintelligence.core

import android.icu.number.NumberFormatter
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


fun Int.toProperRupiah(): String {
    val formatter = NumberFormat.getInstance(Locale("id","ID"))
    formatter.maximumFractionDigits = 0
    formatter.currency = Currency.getInstance(Locale("id","ID"))
    return "Rp."+formatter.format(this)
}