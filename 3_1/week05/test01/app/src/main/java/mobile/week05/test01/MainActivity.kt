package mobile.week05.test01

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val myView = MyView(this)  //this: activity class
//        setContentView(myView)

        val button = findViewById<Button>(R.id.button)
        val myCustomView = findViewById<MyCustomView>(R.id.myCustomView)
        button.setOnClickListener {
            myCustomView.color = Color.YELLOW
            myCustomView.invalidate()  //view 그리는 메소드 다시 호출 ?onDraw는 호출 불가(객체 색 바뀐 것 호출, 전부 다 다시 그려짐)

            Toast.makeText(this, "Toast!!!", Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, "in onDraw", Toast.LENGTH_SHORT).show()  //오류 ?이 객체가 아님
//            Toast.makeText(this@MainActivity, "in onDraw", Toast.LENGTH_SHORT).show()  //이렇게 써줘야 함
        }
    }

    class MyView(context: Context?) : View(context) {
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            canvas?.drawColor(Color.LTGRAY)
            val paint = Paint()
            paint.setColor(Color.BLUE)
            canvas?.drawCircle(200.toFloat(), 200.toFloat(), 100.toFloat(), paint)
        }
    }
}