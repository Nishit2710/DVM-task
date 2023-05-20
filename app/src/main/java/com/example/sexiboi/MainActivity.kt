import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.text.SimpleDateFormat
import com.example.sexiboi.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var reminderList: ArrayList<Reminder>
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reminderList = ArrayList()
        adapter = ReminderAdapter(this, reminderList)

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter

        val addReminderButton: Button = findViewById(R.id.addReminderButton)
        addReminderButton.setOnClickListener {
            val intent = Intent(this, `AddReminderActivity`::class.java)
            startActivityForResult(intent, ADD_REMINDER_REQUEST_CODE)
        }

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 31) {
            val name = resources.getString(R.string.channel_name)
            val descriptionText = resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_REMINDER_REQUEST_CODE && resultCode == RESULT_OK) {
            val title = data?.getStringExtra(`AddReminderActivity`.EXTRA_TITLE)
            val date = data?.getLongExtra(`AddReminderActivity`.EXTRA_DATE, 0)

            if (title != null && date != null) {
                val reminder = Reminder(title, Date(date))
                reminderList.add(reminder)
                adapter.notifyDataSetChanged()

                scheduleNotification(reminder)
            }
        }
    }

    private fun scheduleNotification(reminder: Reminder) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, ReminderBroadcastReceiver::class.java)
        intent.putExtra(EXTRA_TITLE, reminder.title)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.date.time, pendingIntent)
    }

    companion object {
        const val ADD_REMINDER_REQUEST_CODE = 1
        const val CHANNEL_ID = "reminder_channel"
        const val EXTRA_TITLE = "extra_title"
    }
}

class ReminderAdapter(private val context: Context, private val reminderList: ArrayList<Reminder>) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return reminderList.size
    }

    override fun getItem(position: Int): Any {
        return reminderList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.reminder_item, null)

        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)

        val reminder = reminderList[position]

        titleTextView.text = reminder.title

        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        val dateString = dateFormat.format(reminder.date)
        dateTextView.text = dateString

        return view
    }
}

data class Reminder(val title: String, val date: Date)

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(MainActivity.EXTRA_TITLE)
        if (title != null) {
            // Display notification here
        }
    }
}
