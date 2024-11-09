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
import com.example.apptest4.helpers.createNotification
import java.util.Calendar

class NotificationsBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val week = intent.getIntArrayExtra("NOTIFICATION_WEEK")
        val hour = intent.getIntExtra("NOTIFICATION_HOUR", 0)
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
        val calendar = Calendar.getInstance().apply { add(Calendar.DATE, 1) }
        while (week?.contains(calendar.get(Calendar.DAY_OF_WEEK)) != true) {
            calendar.add(Calendar.DATE, 1)
        }
        calendar.set(Calendar.HOUR, hour/60)
        calendar.set(Calendar.MINUTE, hour%60)
        createNotification(context, calendar.timeInMillis, 0, week.toList(), hour)
    }
}