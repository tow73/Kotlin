package ddwu.com.mobile.sensorlistenertest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.sensorlistenertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val sensorManager : SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val sensorListener = object: SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            Log.d(TAG, "${event?.timestamp} : ${event?.values?.get(0)}")
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d(TAG, "")
        }
    }
        
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.btnStart.setOnClickListener {
            lateinit var result : String

            /* 센싱 구현 */
            val sensorType = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            val sensorDelay = SensorManager.SENSOR_DELAY_UI
            sensorManager.registerListener(
                sensorListener, sensorType, SensorManager.SENSOR_DELAY_UI
            )
            //Log.d(TAG, result)
        }

        mainBinding.btnStop.setOnClickListener {
            /* 센싱 종료 */
            sensorManager.unregisterListener(sensorListener)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }
}