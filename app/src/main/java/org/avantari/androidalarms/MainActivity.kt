package org.avantari.androidalarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.avantari.androidalarms.NotificationScheduler.CHANNEL_ID
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    companion object {
        var id = 0
    }

    private val appPreferences by lazy {
        AppPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        createNotificationChannel()

        btn_timer.setOnClickListener {

            showTimePickerDialog(appPreferences.getHour(), appPreferences.getMin())
        }
    }


    private fun showTimePickerDialog(h: Int, m: Int) {
        val builder = TimePickerDialog(this, R.style.DialogTheme,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->
                    Log.d(TAG, "onTimeSet: hour $hour")
                    Log.d(TAG, "onTimeSet: min $min")
                    appPreferences.setHour(hour)
                    appPreferences.setMin(min)
                    textView.text = getFormattedTime(hour, min)
                    NotificationScheduler.setReminder(this@MainActivity, AlarmReceiver::class.java, hour, min, id)
                    id++
                }, h, m, false)

        builder.show()

    }

    private fun getFormattedTime(h: Int, m: Int): String {
        val OLD_FORMAT = "HH:mm"
        val NEW_FORMAT = "hh:mm a"

        val oldDateString = h.toString() + ":" + m
        var newDateString = ""

        try {
            val sdf = SimpleDateFormat(OLD_FORMAT, getCurrentLocale())
            val d = sdf.parse(oldDateString)
            sdf.applyPattern(NEW_FORMAT)
            newDateString = sdf.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return newDateString
    }

    private fun getCurrentLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {

            resources.configuration.locale
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "Alarm", importance)
            channel.description = "Alarm Notification"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
