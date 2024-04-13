package mobile.week10.intenttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mobile.week10.intenttest.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val greeting = intent.getStringExtra("greeting")  //getIntent()와 같음
//        binding.tvText.text = greeting  //editText는 setText써야함

//        val food = intent.getSerializableExtra("food") as FoodDto
//        binding.tvText.text = food.food

        binding.btnOk.setOnClickListener {
            val resultIntent = Intent()  //반드시 비어있는
            resultIntent.putExtra("result_data", "Detail!!!")
            setResult(RESULT_OK, resultIntent)
            finish()  //종료 코드 꼭 쓰기
        }
        binding.btnCancle.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}