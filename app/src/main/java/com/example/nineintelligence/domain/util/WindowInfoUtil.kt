package com.example.nineintelligence.domain.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindoInfo(): WindowType {
    val localConfig = LocalConfiguration.current
    return WindowType(
        screenWidthInfo = when {
            localConfig.screenWidthDp < 600 -> WindowType.WindowInfo.Small
            localConfig.screenWidthDp < 840 -> WindowType.WindowInfo.Medium
            else -> WindowType.WindowInfo.Large
        },
        screenHeightInfo = when {
            localConfig.screenHeightDp < 480 -> WindowType.WindowInfo.Small
            localConfig.screenHeightDp < 900 -> WindowType.WindowInfo.Medium
            else -> WindowType.WindowInfo.Large
        },
        screenWidthDp = localConfig.screenWidthDp.dp,
        screenHeightDp = localConfig.screenHeightDp.dp,
    )
}

data class WindowType(
    val screenWidthInfo: WindowInfo,
    val screenHeightInfo: WindowInfo,
    val screenWidthDp: Dp,
    val screenHeightDp: Dp,
) {
    sealed class WindowInfo {
        object Small : WindowInfo()
        object Medium : WindowInfo()
        object Large : WindowInfo()
    }
}