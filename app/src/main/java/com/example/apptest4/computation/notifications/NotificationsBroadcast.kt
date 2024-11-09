package com.example.apptest4.computation.notifications

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.apptest4.R

class NotificationsBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationBuilder = NotificationCompat.Builder(context, "Reminder")
        notificationBuilder
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Reminder")
            .setAutoCancel(false)
            .setContentText("Test")
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "Not granted, aborting")
            return
        }
        Log.d("Permission", "Granted, scheduling notification")

        manager.notify(intent.getIntExtra("NOTIFICATION_ID", 0), notificationBuilder.build())
    }
}