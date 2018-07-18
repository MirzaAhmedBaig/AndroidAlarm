package org.avantari.androidalarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {
    private val TAG = BroadcastReceiver::class.java.simpleName
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action != null && context != null) run {
            if (intent.action!!.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED")
                val localData = AppPreferences(context)
                NotificationScheduler.setReminder(context, AlarmReceiver::class.java, localData.getHour(), localData.getMin())
                return
            }
        }

        Log.d(TAG, "onReceive: ")

        //Trigger the notification
        NotificationScheduler.showNotification(context!!, MainActivity::class.java,
                "Alarm Notification", "You need a meditation trip")
    }
}