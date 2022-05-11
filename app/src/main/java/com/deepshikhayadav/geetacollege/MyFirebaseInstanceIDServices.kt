package com.deepshikhayadav.geetacollege

import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceIDServices : FirebaseMessagingService() {
/*  val FCM_PARAM = "picture"
    private val CHANNEL_NAME = "FCM"
    private val CHANNEL_DESC = "Firebase Cloud Messaging"
    private var numMessages = 0
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("fcmToken", "From: " + message.from)
        val notification: RemoteMessage.Notification? = message.notification
        val mapData: Map<String, String> = message.data

        val actionClick =message.notification!!.clickAction
        val intent = Intent(actionClick)

        sendNotification(notification,mapData)

      *//*  if (message.notification != null) {
            showNotification(
                message.notification!!.title,
                message.notification!!.body
            )
        }*//*
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendNotification(notification: RemoteMessage.Notification?, mapData: Map<String, String>) {
        val bundle = Bundle()
        bundle.putString("data", "data")
        val intent = Intent(applicationContext, MainActivity2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data", "data")
        intent.putExtras(bundle)
        val uniqueInt = (System.currentTimeMillis() and 0xff).toInt()


        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            uniqueInt,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, getString(R.string.app_name))
                .setContentTitle(notification!!.title)
                .setContentText(notification!!.body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo("")
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources,
                        R.mipmap.ic_launcher
                    )
                )
                .setColor(getColor(R.color.purple_200))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setSmallIcon(R.mipmap.ic_launcher_round)

        try {
            val picture = mapData[FCM_PARAM]
            if (picture != null && "" != picture) {
                val url = URL(picture)
                val bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bigPicture)
                        .setSummaryText(notification!!.body)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default",
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description =
                CHANNEL_DESC
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            notificationManager!!.createNotificationChannel(channel)
        }

        notificationManager!!.notify(12, notificationBuilder.build())
    }

    private fun showNotification(title: String?, body: String?) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(applicationContext, "Water")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getCustomDesign(title, body))

        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        var notificationChannel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                "default", "default",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())


    }

   // @SuppressLint("RemoteViewLayout")
    private fun getCustomDesign(title: String?, body: String?): RemoteViews? {
        val remoteViews = RemoteViews(
            applicationContext.packageName,
            R.layout.custom_notification_layout
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, body)
        remoteViews.setImageViewResource(R.id.icon, R.mipmap.ic_launcher)
        return remoteViews
    }*/

}