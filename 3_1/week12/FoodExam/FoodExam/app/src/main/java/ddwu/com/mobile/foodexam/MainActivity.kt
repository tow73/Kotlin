package ddwu.com.mobile.foodexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.foodexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val DETAIL_CODE = 100
    lateinit var binding : ActivityMainBinding
    lateinit var adapter: FoodAdapter
    lateinit var foods : ArrayList<FoodDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foods = FoodDao().foods

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = FoodAdapter(foods)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivityForResult(intent, DETAIL_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DETAIL_CODE -> {
                if(resultCode == RESULT_OK) {
                    val data = data?.getSerializableExtra("data") as FoodDto
                    foods.add(data)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}