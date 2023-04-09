package com.example.nineintelligence.presentation.dummy

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.core.WorkerTimer
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateTimeFormatViewModel(
    private val prefs: AuthPrefs,
    private val workerTimer: WorkManager
) : ViewModel() {
    private val workerTimerCoroutine = OneTimeWorkRequestBuilder<WorkerTimer>().build()
    val currentTime = DateTimeFormatter.ofPattern("HH:mm").format(ZonedDateTime.now())
    var expectedTimeOut by mutableStateOf("")
        private set

    init {
        simulateAuthTimer(2)
    }

    private fun simulateAuthTimer(timer: Int) = viewModelScope.launch {
        async {
            prefs.clearData()
            Log.d("TAG","Wait a minute")
            val time = ZonedDateTime.now().plusMinutes(timer.toLong())
            expectedTimeOut = DateTimeFormatter.ofPattern("HH:mm").format(time)
            prefs.saveToken("Lmaooo", expectedTimeOut)
        }.await()
        Log.d("TAG","Service Started")
        workerTimer.beginUniqueWork(
            "timerAuth", ExistingWorkPolicy.KEEP,
            workerTimerCoroutine
        )
            .enqueue()
    }
}