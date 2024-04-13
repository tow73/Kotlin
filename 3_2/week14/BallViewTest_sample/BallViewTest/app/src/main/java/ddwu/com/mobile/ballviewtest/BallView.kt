package ddwu.com.mobile.ballviewtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.Log
import android.util.MonthDisplayHelper
import android.view.View
import androidx.core.content.ContextCompat.getSystemService

class BallView : View {

    val TAG = "BallView"

    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){ initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize()}

    fun initialize() {
        isStart = true
        ballR = 100f

        /*센서 매니저 생성 및 센서 준비*/
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    lateinit var sensorManager : SensorManager
    lateinit var accelerometer : Sensor
    lateinit var magnetometer : Sensor

    val mAccmeterReading = FloatArray(3)
    val mMagnetometerReading = FloatArray(3)

    var pitch: Float = 0f
    var roll: Float = 0f

    /*센서 이벤트 처리 리스너*/
    val listener : SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            /*가속도계와 지자기계를 이용하여 pitch, roll 확인(ballX, ballY)*/
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, mAccmeterReading, 0, mAccmeterReading.size)
            } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, mMagnetometerReading, 0, mMagnetometerReading.size)
            }

            if (mAccmeterReading.size != 0 && mMagnetometerReading.size != 0) {
                val rotationMatrix = FloatArray(9)

                val isSuccess: Boolean = SensorManager.getRotationMatrix(
                    rotationMatrix, null, mAccmeterReading, mMagnetometerReading
                )

                if (isSuccess) {
                    var values = FloatArray(3)
                    SensorManager.getOrientation(rotationMatrix, values)
                    for (i in values.indices) {
                        val degrees: Double = Math.toDegrees(values[i].toDouble())
                        values[i] = degrees.toFloat()
                    }
                    pitch = values[1]
                    roll = values[2]
                }
            }
            /*Sensing 값의 결과에 따라 x, y 좌표 값 변경*/
            val distance = 5

            when {
                pitch >= 0 -> ballY -= distance
                pitch < 0 -> ballY += distance
            }
            when {
                 roll >= 0 -> ballX += distance
                roll < 0 -> ballX -= distance
            }
            /*BallView 를 새로운 x, y 좌표로 다시 그림(호출)*/
            invalidate()
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    }

    /*센싱 시작*/
    fun startListening () {
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(listener, magnetometer, SensorManager.SENSOR_DELAY_UI)
    }

    /*센싱 종료*/
    fun stopListening() {
        sensorManager.unregisterListener(listener)
    }

    /*Ball 을 그리는 도구 지정*/
    val paint: Paint by lazy {
        Paint().apply {
            color = Color.RED
            isAntiAlias = true
        }
    }

    var ballX : Float = 0f      // Ball 의 X 좌표
    var ballY : Float = 0f      // Ball 의 Y 좌표
    var ballR : Float = 0f      // Ball 의 반지름

    var width : Int? = 0        // 화면의 너비
    var height: Int? = 0        // 화면의 높이

    var isStart : Boolean = true    // 처음 그려질 때만 초기화 진행을 위한 구분 플래그

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isStart) {
            width = canvas?.width
            height = canvas?.height
            ballX = width?.div(2)?.toFloat() ?: 100f
            ballY = height?.div(2)?.toFloat() ?: 100f
            isStart = false
        }

        canvas?.drawCircle(ballX, ballY, ballR, paint)  //x, y, r, 그리기
    }
}