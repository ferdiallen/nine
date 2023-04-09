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

class WorkerTimer(
    private val context: Context,
    private val worker: WorkerParameters,
    private val authPrefs: AuthPrefs
) : CoroutineWorker(context, worker) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val expectedTimeout = authPrefs.readTime() ?: return@withContext Result.failure()
            do {
                delay(1000L)
                val currentTime = ZonedDateTime.now()
                val formattedCurrentTime = DateTimeFormatter.ofPattern("HH:mm")
                    .format(currentTime)
                Log.d("TAG", "CurrentTime : $formattedCurrentTime")
                Log.d("TAG", "expiredTime : $expectedTimeout")
            } while (formattedCurrentTime.replace(":", "")
                    .toInt() < expectedTimeout.replace(":", "").toInt()
            )
            Log.d("TAG", "Expired")
            return@withContext Result.success()
        }
    }
}