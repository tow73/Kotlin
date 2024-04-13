package ddwu.com.mobile.savestate

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.processNextEventInCurrentThread

class MyView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var color = Color.RED
    var x = 200
    var y = 200
    var r = 100

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.setColor(color)
        canvas?.drawCircle(x.toFloat(), y.toFloat(), r.toFloat(), paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        if (event?.action == MotionEvent.ACTION_DOWN) {
            x = event.x.toInt()
            y = event.y.toInt()
            invalidate()
            return true
        }
        return false
    }
}