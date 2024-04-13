package ddwu.com.mobile.foodexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ddwu.com.mobile.foodexam.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity() {
    val TAG = "AddFoodActivity"
    lateinit var binding : ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val food = binding.etNewFood.text.toString()
            val country = binding.etCountry.text.toString()
            val dto = FoodDto(food, country)
            intent.putExtra("data", dto)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}