package mobile.week10.intenttest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mobile.week10.intenttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val DETAIL_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
//            val intent = Intent(this, TestActivity::class.java)
//            startActivity(intent)

//            val intent = Intent(this, DetailActivity::class.java)
//            startActivity(intent)

//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
//            startActivity(intent)

//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:012-3456-7890"))
//            startActivity(intent)

//            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra("greeting", "Hello!!!")
//            startActivity(intent)

//            val dto = FoodDto(R.mipmap.ic_launcher, "치킨", 20)
//            intent.putExtra("food", dto)
//            startActivity(intent)

            val intent = Intent(this, DetailActivity::class.java)
            startActivityForResult(intent, DETAIL_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DETAIL_CODE -> {
                if(resultCode == RESULT_OK) {
                    val result = data?.getStringExtra("result_data")
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}