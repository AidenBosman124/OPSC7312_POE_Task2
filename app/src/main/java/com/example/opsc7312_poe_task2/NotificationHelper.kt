package com.example.opsc7312_poe_task2

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

// Helper class for managing notifications
class NotificationHelper(private val context: Context) {

    // Initialization block to create the notification channel
    init {
        createNotificationChannel()
    }

    // Function to create the notification channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
    }

    // Function to show a notification with the given title and content
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun showNotification(title: String?, content: String?) {
        // Check if the app has the necessary notification permissions
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            // Build and display the notification
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

    // Companion object with constants for channel ID, notification ID, and permissions request code
    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 1
        private const val PERMISSIONS_REQUEST_NOTIFICATION = 123
    }
}
