package com.example.nineintelligence.presentation.dummy

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nineintelligence.core.CustomSnackBar
import com.example.nineintelligence.core.CustomText
import com.example.nineintelligence.core.Notification
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.get

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun NotificationTestScreen(
    modifier: Modifier = Modifier, notif: Notification = get()
) {
    var shouldShowSnackbar by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = shouldShowSnackbar, block = {
        println(shouldShowSnackbar)
    })
    if (shouldShowSnackbar) {
        CustomSnackBar(onDissmiss = {
            shouldShowSnackbar = false
        }, text = "Permission Granted", icon = Icons.Filled.CheckCircle, tint = Color.Green)
    }
    var hasPermission by remember {
        mutableStateOf(false)
    }
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
            /* if (hasPermission) {
                 notif.showNotification(
                     "Hurry up Tryout is near !",
                     "Get ready for Tryout.Make sure you do not miss at the day",
                     context
                 )
             }*/
            shouldShowSnackbar = !shouldShowSnackbar
        }) {
            CustomText(text = "Click Me")
        }
    }
}

