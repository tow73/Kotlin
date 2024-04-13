package ddwu.com.mobile.exam02

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ddwu.com.mobile.exam02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val colors = arrayOf("Red", "Cyan", "Blue")
    var selectedColor = 1  //초기값: Cyan

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myCustomView.setOnTouchListener(MyTouch())
//        binding.myCustomView.setOnLongClickListener(MyLongClick())
        binding.myCustomView.setOnLongClickListener{
            AlertDialog.Builder(this).run {
                setTitle("색상 선택")
                setSingleChoiceItems(colors, selectedColor, object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        selectedColor = which
                    }
                })
                setPositiveButton("확인", object: DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        when(selectedColor) {
                            0 -> binding.myCustomView.paintColor = Color.RED
                            1 -> binding.myCustomView.paintColor = Color.CYAN
                            2 -> binding.myCustomView.paintColor = Color.BLUE
                        }
                        binding.myCustomView.invalidate()
                    }
                })
                setNegativeButton("취소", null)
                show()
            }
            true
        }
    }

    inner class MyTouch : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            binding.myCustomView.posX = event!!.x
            binding.myCustomView.posY = event!!.y
            binding.myCustomView.invalidate()

            return false  //MyLongClick과 같이 사용하기 위해 false
        }

    }

    inner class MyLongClick : View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            binding.myCustomView.paintColor = Color.RED  //오래 누르면 색 바로 변경

            return true
        }

    }

}