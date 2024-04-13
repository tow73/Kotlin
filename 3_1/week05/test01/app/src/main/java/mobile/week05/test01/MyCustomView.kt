package mobile.week05.test01

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyCustomView : View {
    var color : Int = Color.RED

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
                : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.LTGRAY)
        val paint = Paint()
        paint.setColor(color)
        canvas?.drawCircle(200.toFloat(), 200.toFloat(), 100.toFloat(), paint)
    }
}