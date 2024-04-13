package ddwu.com.mobile.alarmtest

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class MyBroadcastReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val msg = intent?.getStringExtra("MSG")
        Log.d("MyBroadCastReceiver", "Received: ${msg}")

        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelID = "MY_CHANNEL"

        var builder = NotificationCompat.Builder(context!!, channelID!!)
            .setSmallIcon(R.drawable.ic_action_name)
            .setContentTitle("AlarmTest")
            .setContentText("기상 시간")
            .setStyle(NotificationCompat.BigTextStyle().bigText("일어나! 공부할 시간이야!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notiManager = NotificationManagerCompat.from(context)
        notiManager.notify(100, builder.build())  //경고
    }

    private fun createNotificationChannel(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MY_CHANNEL"
            val descriptionText = "My Channel Message"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("MY_CHANNEL_ID", name, importance)
            mChannel.description = descriptionText

            val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}