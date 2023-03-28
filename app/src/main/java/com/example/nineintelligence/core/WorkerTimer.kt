package com.example.nineintelligence.core

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class WorkerTimer(
    private val context: Context,
    private val worker: WorkerParameters,
    private val authPrefs: AuthPrefs
) : CoroutineWorker(context, worker) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            Result.success()
        }
    }
}