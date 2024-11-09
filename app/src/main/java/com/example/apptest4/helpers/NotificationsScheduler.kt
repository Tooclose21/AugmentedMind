package com.example.apptest4.helpers

import java.util.Calendar
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import com.example.apptest4.computation.notifications.NotificationsBroadcast

 fun createNotification(activity: Context, time: Long, id: Int, week: List<Int>, hour: Int) {
    val intent = Intent(activity.applicationContext, NotificationsBroadcast::class.java)
    intent.putExtra("NOTIFICATION_ID", id)
    intent.putExtra("NOTIFICATION_WEEK", week.toIntArray())
    intent.putExtra("NOTIFICATION_HOUR", hour)

    val pendingIntent = PendingIntent.getBroadcast(
        activity.applicationContext,
        id,
        intent,
        PendingIntent.FLAG_MUTABLE
    )
    val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        time,
        pendingIntent
    )
}
