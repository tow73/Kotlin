package ddwu.com.mobile.orientationtest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.orientationtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val sensorManager : SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    lateinit var accelerometer: Sensor
    lateinit var magnetometer: Sensor

    var mAccelerometer = FloatArray(3)
    var mMagnetometer = FloatArray(3)

    val sensorListener = object: SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, mAccelerometer, 0, mAccelerometer.size)
            } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, mMagnetometer, 0, mMagnetometer.size)
            }

            if (mAccelerometer.size != 0 && mMagnetometer.size != 0) {
                val rotationMatrix = FloatArray(9)

                val isSuccess: Boolean = SensorManager.getRotationMatrix(
                    rotationMatrix, null, mAccelerometer, mMagnetometer
                )

                if (isSuccess) {
                    var values = FloatArray(3)
                    SensorManager.getOrientation(rotationMatrix, values)
                    for (i in values.indices) {
                        val degrees: Double = Math.toDegrees(values[i].toDouble())
                        values[i] = degrees.toFloat()
                    }
                    val azimuth: Float = values[0]
                    val pitch: Float = values[1]
                    val roll: Float = values[2]
                    Log.d(TAG, "azimuth: ${azimuth}, pitch: ${pitch}, roll: ${roll}")
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d(TAG, "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        mainBinding.btnStart.setOnClickListener {
            sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI)
            sensorManager.registerListener(sensorListener, magnetometer, SensorManager.SENSOR_DELAY_UI)
        }

        mainBinding.btnStop.setOnClickListener {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }

    fun showData(data: String) {
        val text = mainBinding.tvDisplay.text.toString()
        mainBinding.tvDisplay.setText("${text}\n${data}")
    }
}