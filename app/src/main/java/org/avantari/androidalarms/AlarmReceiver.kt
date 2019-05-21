package org.avantari.androidalarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import java.util.*


class AlarmReceiver : BroadcastReceiver() {
    private val TAG = BroadcastReceiver::class.java.simpleName
    override fun onReceive(context: Context?, intent: Intent?) {
        /*if (intent!!.action != null && context != null) run {
            if (intent.action!!.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED")
                val localData = AppPreferences(context)
                NotificationScheduler.setReminder(context, AlarmReceiver::class.java, localData.getHour(), localData.getMin(), id)
                id++
                return
            }
            Log.d(TAG, "onReceive: $intent!!.action")
        }*/

//        Log.d(TAG, "onReceive: $intent!!.action")

        //Trigger the notification
        Log.d(TAG, "onReceive: ${intent}")
        NotificationScheduler.showNotification(context!!, MainActivity::class.java,
                "Alarm Notification", "You need a meditation trip", intent!!.getIntExtra("name", 100))
        Log.d(TAG, "onReceive: ${intent.getIntExtra("name", 100)}")
        Log.d(TAG, "onReceive: ${intentToString(intent)}")
    }

    fun intentToString(intent: Intent?): String? {
        return if (intent == null) {
            null
        } else intent.toString() + " " + bundleToString(intent.extras)

    }

    fun bundleToString(bundle: Bundle?): String {
        val out = StringBuilder("Bundle[")

        if (bundle == null) {
            out.append("null")
        } else {
            var first = true
            for (key in bundle.keySet()) {
                if (!first) {
                    out.append(", ")
                }

                out.append(key).append('=')

                val value = bundle.get(key)

                if (value is IntArray) {
                    out.append(Arrays.toString(value))
                } else if (value is ByteArray) {
                    out.append(Arrays.toString(value))
                } else if (value is BooleanArray) {
                    out.append(Arrays.toString(value))
                } else if (value is ShortArray) {
                    out.append(Arrays.toString(value))
                } else if (value is LongArray) {
                    out.append(Arrays.toString(value))
                } else if (value is FloatArray) {
                    out.append(Arrays.toString(value))
                } else if (value is DoubleArray) {
                    out.append(Arrays.toString(value))
                } else if (value is Array<*>) {
                    out.append(Arrays.toString(value))
                } else if (value is Array<*>) {
                    out.append(Arrays.toString(value))
                } else if (value is Array<*>) {
                    out.append(Arrays.toString(value))
                } else if (value is Bundle) {
                    out.append(bundleToString(value))
                } else {
                    out.append(value)
                }

                first = false
            }
        }

        out.append("]")
        return out.toString()
    }
}