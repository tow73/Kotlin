package mobile.week05.hw5_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val myCustomView = findViewById<MyCustomView>(R.id.myCustomView)
        button.setOnClickListener {
            val random = Random()
            val x2 = random.nextInt(500)
            val y2 = random.nextInt(500)
            val r2 = (random.nextInt(2) + 1) * 100
            myCustomView.x = x2
            myCustomView.y = y2
            myCustomView.r = r2
            myCustomView.invalidate()
        }
    }

    class MyView(context: Context?) : View(context) {
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            canvas?.drawColor(Color.LTGRAY)
            val paint = Paint()
            paint.setColor (Color.CYAN)
            canvas?.drawCircle(x.toFloat(), y.toFloat(), r.toFloat(), paint)
        }
    }
}