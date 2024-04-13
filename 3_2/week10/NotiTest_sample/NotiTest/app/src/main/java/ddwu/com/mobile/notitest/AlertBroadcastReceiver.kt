package ddwu.com.mobile.notitest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlertBroadcastReceiver : BroadcastReceiver() {  //항상 방송을 수신하게 하고 싶다면 manifest에 등록
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "휴식중...", Toast.LENGTH_SHORT).show()
        val notiId = intent?.getIntExtra("NOTI_ID", 0)
        Log.d("AlertBroadcastReceiver", "Notification ID: ${notiId}")
    }
}