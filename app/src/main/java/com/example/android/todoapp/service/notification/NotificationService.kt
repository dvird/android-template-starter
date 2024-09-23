package com.example.android.todoapp.service.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.android.todoapp.R

/**
 * NotificationService is responsible for creating and managing notifications within the ToDoApp.
 * This service provides functionality to show notifications, manage notification channels, and
 * support notification actions and custom intents.
 */
class NotificationService(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "TODO_APP_NOTIFICATION_CHANNEL"
        private const val CHANNEL_NAME = "ToDo App Notifications"
        private const val CHANNEL_DESCRIPTION = "Notifications for task reminders and updates"
    }

    init {
        createNotificationChannel()
    }

    /**
     * Displays a notification with optional actions and a pending intent.
     *
     * @param title the title of the notification
     * @param message the message body of the notification
     * @param notificationId a unique identifier for the notification
     * @param intent an optional intent to be executed when the notification is tapped
     * @param actionTitle an optional title for an action button
     * @param actionIntent an optional PendingIntent to be executed when the action button is pressed
     */
    @SuppressLint("MissingPermission")
    fun showNotification(
        title: String,
        message: String,
        notificationId: Int,
        intent: Intent? = null,
        actionTitle: String? = null,
        actionIntent: PendingIntent? = null
    ) {
        val notification = buildNotification(title, message, intent, actionTitle, actionIntent)
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }

    /**
     * Builds the notification using the provided parameters.
     *
     * @param title the title of the notification
     * @param message the message body of the notification
     * @param intent an optional intent to launch when the notification is tapped
     * @param actionTitle an optional title for an action button
     * @param actionIntent an optional PendingIntent for the action button
     * @return a configured Notification instance
     */
    private fun buildNotification(
        title: String,
        message: String,
        intent: Intent?,
        actionTitle: String?,
        actionIntent: PendingIntent?
    ): Notification {
        val pendingIntent = intent?.let {
            PendingIntent.getActivity(
                context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_no_fill)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)  // Notification tap intent
            .apply {
                // Optional: add an action if provided
                if (actionTitle != null && actionIntent != null) {
                    addAction(R.drawable.logo_no_fill, actionTitle, actionIntent)
                }
            }.build()
    }

    /**
     * Creates a notification channel for Android O (API 26) and above.
     * The channel is required to display notifications on these Android versions.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
