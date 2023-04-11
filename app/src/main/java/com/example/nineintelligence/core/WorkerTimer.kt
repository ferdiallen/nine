package com.example.nineintelligence.core

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.time.Duration.Companion.seconds

class WorkerTimer(
    private val context: Context,
    private val worker: WorkerParameters,
    private val authPrefs: AuthPrefs
) : CoroutineWorker(context, worker) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            var expectedTimeout = 10.seconds
            do {
                delay(1000L)
                Log.d("TAG", "Running")
                expectedTimeout -= 1.seconds
            } while (expectedTimeout > 1.seconds)
            return@withContext Result.success()
        }
    }
}