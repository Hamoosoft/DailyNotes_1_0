package com.soft.dailynotes.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.soft.dailynotes.R
import com.soft.dailynotes.presentation.ui.utils.Constants.CHANNEL_DESCRIPTION
import com.soft.dailynotes.presentation.ui.utils.Constants.CHANNEL_ID
import com.soft.dailynotes.presentation.ui.utils.Constants.DAILY_NOTES_NOTIFICATION_CHANNEL_NAME
import com.soft.dailynotes.presentation.ui.utils.Constants.NOTIFICATION_ID
import com.soft.dailynotes.presentation.ui.utils.Constants.NOTIFICATION_TITLE

fun makeStatusNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = DAILY_NOTES_NOTIFICATION_CHANNEL_NAME
        val description = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        return
    }
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}