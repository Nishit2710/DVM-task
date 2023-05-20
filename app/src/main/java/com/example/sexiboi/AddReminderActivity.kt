import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.sexiboi.R

import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import java.util.*

class AddReminderActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        titleEditText = findViewById(R.id.titleEditText)
        timePicker = findViewById(R.id.timePicker)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val date = calendar.timeInMillis

            if (title.isNotEmpty()) {
                val intent = intent
                intent.putExtra(EXTRA_TITLE, title)
                intent.putExtra(EXTRA_DATE, date)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DATE = "extra_date"
    }
}
