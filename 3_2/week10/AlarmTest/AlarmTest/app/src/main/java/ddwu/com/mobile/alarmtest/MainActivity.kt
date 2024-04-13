package ddwu.com.mobile.alarmtest

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ddwu.com.mobile.alarmtest.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val alarmManager by lazy {
        getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        val intent = Intent(this, MyBroadcastReceiver::class.java)
        intent.putExtra("MSG", "My Message")
        val alarmIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_IMMUTABLE)

        mainBinding.btnOneShot.setOnClickListener {
            alarmManager?.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 10 * 1000,
                alarmIntent
            )
        }

        mainBinding.btnRepeat.setOnClickListener {

        }

        mainBinding.btnStopAlarm.setOnClickListener {
            finish()
        }

    }
}