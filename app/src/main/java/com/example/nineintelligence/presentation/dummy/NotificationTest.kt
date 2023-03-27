package com.example.nineintelligence.presentation.dummy

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.core.Notification
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.get

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun NotificationTestScreen(
    modifier: Modifier = Modifier,
    notif: Notification = get()
) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(false)
    }
    val permission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        } else {
            null
        }
    LaunchedEffect(key1 = Unit, block = {
        permission?.launchPermissionRequest()
    })
    LaunchedEffect(key1 = permission?.status, block = {
        permission?.let {
            if (it.status.isGranted) {
                hasPermission = true
            }
        }
    })
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (hasPermission) {
                notif.showNotification(
                    "Hurry up Tryout is near !",
                    "Get ready for Tryout.Make sure you do not miss at the day",
                    context
                )
            }
        }) {
            CustomText(text = "Click Me")
        }
    }
}