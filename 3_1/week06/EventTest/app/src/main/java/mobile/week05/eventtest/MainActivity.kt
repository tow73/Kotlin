package mobile.week05.eventtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import mobile.week05.eventtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"  //activity 이름과 동일하게 하는 것이 일반적

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
//        val myClick = MyClick()  //방법1
        
//        binding.button.setOnClickListener(MyClick())  //이벤트 핸들러 객체를 생성해 끼워넣기 //방법1

//        binding.button.setOnClickListener(click)  //방법2

//        방법3
//        binding.button.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {  //리스너가 클릭했을 때 동작 구현
//                Toast.makeText(this@MainActivity, "Click!!!", Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "Click!!!")
//            }
//        })

//        방법4: 람다함수 사용 -SAM(Single Abstract Method)일 경우(동작 여러개 불가)
//        binding.button.setOnClickListener {
//            v: View? -> Log.d(TAG, "Click!!!!")
//            Log.d(TAG, "Click!!!!")  //동작 하나일 떄만 가능
//        }
    }

//    방법1: 가장 정석적
//    inner class MyClick : View.OnClickListener {  //리스너 상속받는 클래스
//        override fun onClick(v: View?) {  //리스너가 클릭했을 때 동작 구현
//            Toast.makeText(this@MainActivity, "Click!", Toast.LENGTH_SHORT).show()
//            Log.d(TAG, "Click")
//        }
//    }
//    방법2: 핵심, 각 뷰에 준비된 리스너를 필요에 맞게 끼워넣기
//    val click = object : View.OnClickListener {
//    override fun onClick(v: View?) {  //리스너가 클릭했을 때 동작 구현
//        Toast.makeText(this@MainActivity, "Object Click!", Toast.LENGTH_SHORT).show()
//        Log.d(TAG, "Object Click")
//    }
//    }
//    방법5: layout(xml)에서 직접 이름 지정해서 생성
    fun onMyClick(view: View?) {
//        when(view.id) {  //버튼 구분도 가능
//            R.id.button -> Toast.makeText(this, "On MyClick!!!", Toast.LENGTH_SHORT).show()
//        }
        Toast.makeText(this, "On MyClick!!!", Toast.LENGTH_SHORT).show()
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        when(event?.action) {
//            MotionEvent.ACTION_DOWN ->
//                Toast.makeText(this, "Touch Down", Toast.LENGTH_SHORT).show()
//            MotionEvent.ACTION_UP ->
//                Toast.makeText(this, "Touch Up", Toast.LENGTH_SHORT).show()
//        }
//
//        return true //false면 처리하지 못했기에 상위로 전달
//    }
}