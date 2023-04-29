package com.example.sexiboi

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sexiboi.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar:Calendar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = Calendar.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        createNotificationChannel()




        binding.timePicker.setOnClickListener {





        }

        binding.buttonAlarm.setOnClickListener {


            scheduleNotification()



        }




    }







    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext,AlarmReciever::class.java)

        val message = binding.messageET.tag.toString()

        intent.putExtra(messageExtra,message)

        val pendingIntent= PendingIntent.getBroadcast(

            applicationContext,
            notificationID,


            intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
       showAlert(time,message)
    }

    private fun showAlert(time: Long,  message: String) {



        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title:"+ title +
                        "\nMessage:" + message +
                        "\nAt:"+dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Okay"){_,_ ->}


    }


    private fun getTime(): Long {

        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year =  binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year,month,day,hour,minute)
        return calendar.timeInMillis

    }


    private fun createNotificationChannel(){


            val name : CharSequence = "Sexiboi Reminder Channel"
            val description = "Channel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("sexiboi",name,importance)
            channel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }


