package mobile.week05.hw5_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.Toast

class MyCustomView : View {
    var x : Int = 100
    var y : Int = 100
    var r : Int = 100
    val color : Int = Color.CYAN

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.LTGRAY)
        val paint = Paint()
        paint.setColor(color)
        canvas?.drawCircle(x.toFloat(), y.toFloat(), r.toFloat(), paint)
    }
}