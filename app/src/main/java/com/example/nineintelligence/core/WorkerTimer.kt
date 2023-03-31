package com.example.nineintelligence.core

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class WorkerTimer(
    private val context: Context,
    private val worker: WorkerParameters,
    private val authPrefs: AuthPrefs
) : CoroutineWorker(context, worker) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val expectedTimeout = authPrefs.readTime() ?: return@withContext Result.failure()
            println("Expected Timeout : $expectedTimeout")
            do {
                delay(1000L)
                val currentTime = ZonedDateTime.now()
                val formattedCurrentTime = DateTimeFormatter.ofPattern("HH:mm").format(currentTime)
                println("CurrentTime : $formattedCurrentTime")
            } while (formattedCurrentTime.removePrefix(":")
                    .toInt() < expectedTimeout.removePrefix(":").toInt()
            )
            println("Expired")
            return@withContext Result.success()
        }
    }
}