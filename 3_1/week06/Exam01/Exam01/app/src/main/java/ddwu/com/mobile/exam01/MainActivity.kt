package ddwu.com.mobile.exam01

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ddwu.com.mobile.exam01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding  //binding 지역변수를 바깥으로 뽑기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        방법1
//        binding.btnDisplay.setOnClickListener(MyClick1())

//        방법3
        binding.btnDisplay.setOnClickListener {
//            binding.tvDisplay.setText(binding.etText.getText().toString())  //입력값 바로 구현
            val text = binding.etText.text // getText().toString() = text
            AlertDialog.Builder(this)
                .setTitle("입력 확인")
                .setMessage("$text 를 입력하시겠습니까?")
                .setPositiveButton("네", object: DialogInterface.OnClickListener {
                    override fun onClick(dalog: DialogInterface?, which: Int) {
                        binding.tvDisplay.setText(text)
                    }
                })
                .setNegativeButton("아니오", null)
                .show()
        }

    }
//    방법1
//    inner class MyClick1 : View.OnClickListener {
//        override fun onClick(v: View?) {
//            binding.tvDisplay.setText(binding.etText.getText().toString())
//        }
//    }

//    방법2
//    val click2 = object : View.OnClickListener {
//        override fun onClick(v: View?) {
//            binding.tvDisplay.setText(binding.etText.getText().toString())
//        }
//    }
}