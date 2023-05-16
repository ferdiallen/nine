package com.example.nineintelligence.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomSnackBar(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: ImageVector = Icons.Filled.Check, tint: Color,
    onDissmiss: () -> Unit, timeOut: Long = 0L, manualDissmiss: Boolean = true
) {
    CustomSnackBarNotification(duration = timeOut, manualDissmiss = manualDissmiss, onDissmiss = {
        onDissmiss.invoke()
    }) {
        Column(
            modifier = modifier
                .wrapContentWidth()
                .padding(top = 12.dp)
        ) {
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(60.dp)
                    .padding(horizontal = 20.dp), shape = RoundedCornerShape(32.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = tint, modifier = Modifier.size(40.dp)
                    )
                    CustomText(
                        text = text,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }
}


@Composable
private fun CustomSnackBarNotification(
    duration: Long = 0L, manualDissmiss: Boolean = true, onDissmiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var animateBackground by remember {
        mutableStateOf(0.1F)
    }
    val animateValue by animateFloatAsState(targetValue = animateBackground, label = "")
    LaunchedEffect(key1 = Unit, block = {
        animateBackground = 0.5F
    })
    LaunchedEffect(key1 = animateValue, block = {
        if (animateValue == 0F) {
            onDissmiss.invoke()
        }
    })
    LaunchedEffect(key1 = Unit, block = {
        if (duration != 0L) {
            delay(duration)
            animateBackground = 0F
        }
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(animateValue))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    if (!manualDissmiss) return@clickable
                    animateBackground = 0F
                })
    ) {
        AnimatedVisibility(
            visible = animateBackground == 0.5F,
            enter = slideInVertically(tween(500)),
            exit = slideOutVertically(tween(500))
        ) {
            content.invoke()
        }
    }

}