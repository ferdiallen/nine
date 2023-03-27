package com.example.nineintelligence.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.nineintelligence.R
import com.example.nineintelligence.domain.util.NotificationIdentifier

class Notification(
    private val notification: NotificationManager
) {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationIdentifier.NORMAL_CHANNEL_ID, "Anonymous Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notification.createNotificationChannel(channel)
        }
    }

    fun showNotification(titleText: String, notificationDesc: String, context: Context) {
        val notifications = NotificationCompat.Builder(
            context, NotificationIdentifier.NORMAL_CHANNEL_ID
        ).apply {
            setSmallIcon(R.drawable.grade)
            setContentTitle(titleText)
            setContentText(notificationDesc)
        }.build()
        notification.notify(
            1, notifications
        )
    }
}