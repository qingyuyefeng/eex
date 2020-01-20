package com.eex.compat

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.widget.RemoteViews

object NotificationHelper {
  const val NOTIFY_CHANNEL_ID = "default"

  fun sendNotify(
    context: Context,
    clickIntent: Intent? = null,
    contentView: RemoteViews? = null,
    title: String = "",
    content: String = "",
    flags: Int = Notification.FLAG_AUTO_CANCEL,
    @DrawableRes icon: Int? = null,
    @ColorRes textColor: Int? = null
  ) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // 创建
      val channel =
        NotificationChannel(NOTIFY_CHANNEL_ID, title, NotificationManager.IMPORTANCE_DEFAULT)
      channel.enableLights(true)
      textColor?.apply {
        channel.lightColor = ContextCompat.getColor(context, textColor)
      }
      channel.setShowBadge(true)
      channel.description = content
      manager.createNotificationChannel(channel)
    }

    val mBuilder = NotificationCompat.Builder(context, NOTIFY_CHANNEL_ID)
    mBuilder.setContentTitle(title)
        .setOnlyAlertOnce(true)
        .setDefaults(Notification.DEFAULT_ALL)
        .setWhen(System.currentTimeMillis())
    when (flags) {
      Notification.FLAG_AUTO_CANCEL -> mBuilder.setAutoCancel(true)
      Notification.FLAG_ONGOING_EVENT -> mBuilder.setOngoing(true)
      Notification.FLAG_ONLY_ALERT_ONCE -> mBuilder.setOnlyAlertOnce(true)
    }
    icon?.apply {
      mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.resources, icon))
          .setSmallIcon(icon)
    }
    textColor?.apply {
      mBuilder.color = ContextCompat.getColor(context, textColor)
    }
    if (content.isNotEmpty()) {
      mBuilder.setContentText(content)
    }
    contentView?.apply {
      mBuilder.setContent(this)
    }
    clickIntent?.apply {
      clickIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      val clickPendingIntent =
        PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT)
      mBuilder.setContentIntent(clickPendingIntent)
    }
    manager.notify(1, mBuilder.build())
  }

  fun cancel(context: Context) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.cancel(1)
  }

}