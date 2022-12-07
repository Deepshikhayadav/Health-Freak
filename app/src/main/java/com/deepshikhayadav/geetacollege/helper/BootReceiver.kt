package com.deepshikhayadav.geetacollege.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.deepshikhayadav.geetacollege.util.Utils



class BootReceiver : BroadcastReceiver() {
    private val alarm = AlarmHelper()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action != null) {
            if (intent.action == "android.intent.action.BOOT_COMPLETED") {
                val prefs = context!!.getSharedPreferences(Utils.USERS_SHARED_PREF, Utils.PRIVATE_MODE)
                val notificationFrequency = prefs.getInt(Utils.NOTIFICATION_FREQUENCY_KEY, 60)
                val notificationsNewMessage = prefs.getBoolean("notifications_new_message", true)
                alarm.cancelAlarm(context)
                if (notificationsNewMessage) {
                    alarm.setAlarm(context, notificationFrequency.toLong())
                }
            }
        }
    }
}
