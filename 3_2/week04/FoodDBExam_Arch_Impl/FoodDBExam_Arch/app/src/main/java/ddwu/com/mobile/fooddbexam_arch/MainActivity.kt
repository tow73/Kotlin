package ddwu.com.mobile.fooddbexam_arch


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.fooddbexam_arch.data.Food
import ddwu.com.mobile.fooddbexam_arch.databinding.ActivityMainBinding
import ddwu.com.mobile.fooddbexam_arch.ui.FoodAdapter
import ddwu.com.mobile.fooddbexam_arch.ui.FoodViewModel
import ddwu.com.mobile.fooddbexam_arch.ui.FoodViewModelFactory

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    lateinit var mainBinding : ActivityMainBinding
    lateinit var adapter : FoodAdapter

    val viewModel : FoodViewModel by viewModels {
        FoodViewModelFactory( (application as FoodApplication).repository )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        Log.d(TAG, "onCreate!!!")

        adapter = FoodAdapter()
        mainBinding.rvFoods.adapter = adapter
        mainBinding.rvFoods.layoutManager = LinearLayoutManager(this)
        viewModel.allFoods.observe(this, Observer {foods ->
            adapter.foods = foods
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Observing!!!")
        })

        mainBinding.btnAdd.setOnClickListener {
            viewModel.addFood(
                Food(0, mainBinding.etFood.text.toString(),
                    mainBinding.etCountry.text.toString())
            )
        }

        mainBinding.btnModify.setOnClickListener {
            viewModel.modifyFood(
                mainBinding.etFood.text.toString(),
                mainBinding.etCountry.text.toString()
            )
        }

        mainBinding.btnRemove.setOnClickListener {
            viewModel.removeFood(
                mainBinding.etFood.text.toString()
            )
        }

        // 기능 생략
        mainBinding.btnShow.setOnClickListener {  }
    }
}