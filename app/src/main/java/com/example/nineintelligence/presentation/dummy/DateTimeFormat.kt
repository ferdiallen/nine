package com.example.nineintelligence.presentation.dummy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nineintelligence.core.CustomText
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateTimeFormatExample(
    viewModel: DateTimeFormatViewModel = koinViewModel()
) {
    val currentTime = viewModel.currentTime
    val expectedTimeout = viewModel.expectedTimeOut
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(text = "Current Time : $currentTime")
        CustomText(text = "Expected Timeout : $expectedTimeout")
    }
}