package ddwu.com.mobile.fooddbexam_arch


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
        FoodViewModelFactory((application as FoodApplication).foodRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        Log.d(TAG, "onCreate!!!")

        adapter = FoodAdapter()
        mainBinding.rvFoods.adapter = adapter
        mainBinding.rvFoods.layoutManager = LinearLayoutManager(this)

        /*foodAdapter 에 LongClickListener 구현 및 설정*/
        val onLongClickListener = object: FoodAdapter.OnItemLongClickListener {
            override fun onItemLongClickListener(view: View, pos: Int) {
                Log.d(TAG, "Long Click!! $pos")
                // 삭제하기 구현 -> 복습 필요
            }
        }
        adapter.setOnItemLongClickListener(onLongClickListener)
        mainBinding.rvFoods.adapter = adapter

        viewModel.allFoods.observe(this, Observer {
            adapter.foods = it
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Observing!!!")
        })

        mainBinding.btnAdd.setOnClickListener {
            viewModel.addFood(Food(0, mainBinding.etFood.text.toString(), mainBinding.etCountry.text.toString()))
        }

        mainBinding.btnModify.setOnClickListener {
            viewModel.updateFood(mainBinding.etFood.text.toString(), mainBinding.etCountry.text.toString())
        }
        mainBinding.btnRemove.setOnClickListener {
            viewModel.deleteFood(mainBinding.etFood.text.toString())
        }
        mainBinding.btnShow.setOnClickListener {
            viewModel.getFoodByCountry(mainBinding.etCountry.text.toString())
        }
    }
}