package com.example.nineintelligence.domain.util

fun main() {
    println(timeConversion("01:12:02AM"))
}

private fun timeConversion(s: String): String {
    return when {
        s.contains("AM") -> {
            var amTime = 0
            while (amTime < s.take(2).toInt()) {
                amTime++
            }
            s.replace(s.take(2), amTime.toString()).dropLast(2)
        }

        s.contains("PM") -> {
            var startTime = 12
            var pmTime = 0
            if (s.take(2).toInt() == 12) {
                s.dropLast(2)
            } else {
                while (pmTime < s.take(2).toInt()) {
                    pmTime++
                    startTime++
                }
                s.replace(s.take(2), startTime.toString()).dropLast(2)
            }
        }

        else -> {
            ""
        }
    }
}