package com.example.sexiboi

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity


class DestinationActivity : AppCompatActivity() {

     lateinit var notificationManager: NotificationManager
     lateinit var notificationChannel: NotificationChannel
     lateinit var builder:Notification.Builder
     private val channelId = "i.apps.notification"
     private val description="Test Notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_destination)

        val buttonAlarm =  findViewById<Button>(R.id.buttonAlarm)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        buttonAlarm.setOnClickListener {

        val intent = Intent(this,DestinationActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val contentView = RemoteViews(packageName, R.layout.activity_destination)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            } else {

                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234, builder.build())
        }
    }
}



