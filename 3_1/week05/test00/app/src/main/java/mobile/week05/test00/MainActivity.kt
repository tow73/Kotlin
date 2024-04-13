package mobile.week05.test00

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import mobile.week05.test00.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setContentView(R.layout.activity_main)  //resourc.layout.activity_main의 정보를 갖고와 화면 안에 있는 객체로 만들어줌
//        val textView = findViewById<TextView>(R.id.textView)
//        textView.setText("Mobile Programming")

//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)  //가장 꼭대기에 있는 것을 기본 화면으로 설정(activity_main.xml의 constraintlayout)
//        val textView = binding.textView  //더이상 지원하지 않는 방법
//        binding.textView.setText("Mobile")  //선언과 동시에 사용


    }
}