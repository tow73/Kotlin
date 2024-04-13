package ddwu.com.mobile.roomexam01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.roomexam01.data.Food
import ddwu.com.mobile.roomexam01.data.FoodDao
import ddwu.com.mobile.roomexam01.data.FoodDatabase
import ddwu.com.mobile.roomexam01.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding
//    lateinit var foodAdapter: FoodAdapter

    lateinit var db : FoodDatabase
    lateinit var foodDao : FoodDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FoodDatabase.getDatabase(this)
        foodDao = db.foodDao()

        /*RecyclerView 의 layoutManager 지정*/
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        /*샘플 데이터, DB 사용 시 DB에서 읽어온 데이터로 대체 필요*/
//        val foods = ArrayList<Food>()
//        foods.add(Food(1,"된장찌개", "한국"))
//        foods.add(Food(2,"김치찌개", "한국"))
//        foods.add(Food(3,"마라탕", "중국"))
//        foods.add(Food(4,"훠궈", "중국"))
//        foods.add(Food(5,"스시", "일본"))
//        foods.add(Food(6,"오코노미야키", "일본"))

//        foodAdapter = FoodAdapter(foods)

        /*foodAdapter 에 LongClickListener 구현 및 설정*/
        val onLongClickListener = object: FoodAdapter.OnItemLongClickListener {
            override fun onItemLongClickListener(view: View, pos: Int) {
                Log.d(TAG, "Long Click!! $pos")
            }
        }
//        foodAdapter.setOnItemLongClickListener(onLongClickListener)

//        binding.foodRecyclerView.adapter = foodAdapter

        db = FoodDatabase.getDatabase(this) // Singleton 패턴을 적용한 FoodDatabase 사용
        foodDao = db.foodDao()


        binding.btnShow.setOnClickListener{
            val country = binding.etCountry.text.toString()
            showCountryFoods(country)
        }

        binding.btnInsert.setOnClickListener{
            val food = binding.etFood.text.toString()
            val country = binding.etCountry.text.toString()
            insertFood(Food( 0 , food, country))
        }

        binding.btnUpdate.setOnClickListener {
            val food = binding.etFood.text.toString()
            val country = binding.etCountry.text.toString()
            if(food.isNotEmpty() && country.isNotEmpty()) {
                updateFood(food, country)
            }
            else {
                Log.d(TAG, "음식 또는 나라 이름을 입력하세요.")
            }
        }

        binding.btnDelete.setOnClickListener {
            val food = binding.etFood.text.toString()
            if(food.isNotEmpty()) {
                deleteFood(food)
            }
            else {
                Log.d(TAG, "음식 이름을 입력하세요.")
            }
        }
        showAllFoods()
    }

    fun showAllFoods() {
        CoroutineScope(Dispatchers.IO).launch {
            val foodflow = foodDao.getAllFoods()

            foodflow.collect {
                foods -> for(food in foods) {
                    Log.d(TAG, food.toString())
                }
            }
        }
    }
    fun showCountryFoods(country: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val foods = foodDao.showCountryFoods(country)
            for (food in foods) {
                Log.d(TAG, food.toString())
            }
        }
    }
    fun insertFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDao.insertFood(food)
        }
    }
    fun updateFood(foodName: String, newCountry: String) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDao.updateFood(foodName, newCountry)
        }
    }
    fun deleteFood(foodName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDao.deleteFood(foodName)
        }
    }
}