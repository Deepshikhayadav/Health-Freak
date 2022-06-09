package com.deepshikhayadav.geetacollege.helper

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import com.deepshikhayadav.geetacollege.R
import com.deepshikhayadav.geetacollege.util.Utils

class NotifierReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val prefs = context.getSharedPreferences(Utils.USERS_SHARED_PREF, Utils.PRIVATE_MODE)
        val notificationsTone = prefs.getString(
            Utils.NOTIFICATION_TONE_URI_KEY, RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION
            ).toString()
        )

        val title = context.resources.getString(R.string.app_name)
        val messageToShow = prefs.getString(
            Utils.NOTIFICATION_MSG_KEY,
            context.resources.getString(R.string.drink_water)
        )

        /* Notify */
        val nHelper = NotificationHelper(context)
        @SuppressLint("ResourceType") val nBuilder = messageToShow?.let {
            nHelper
                .getNotification(title, it, notificationsTone)
        }
        nHelper.notify(1, nBuilder)

    }
}