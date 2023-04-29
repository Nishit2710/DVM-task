package com.example.sexiboi


import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat



const val messageExtra = "messageExtra"
const val notificationID = 1
const val channelID = "sexiboi"
const val titleExtra = "titleExtra"

class AlarmReciever: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {





        val notification = NotificationCompat.Builder(context!!, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)


                .setAutoCancel(true)
            .setContentTitle(intent?.getStringExtra(titleExtra))

                .setContentText(intent?.getStringExtra(messageExtra))


                .build()

        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID,notification)
        }









    }




