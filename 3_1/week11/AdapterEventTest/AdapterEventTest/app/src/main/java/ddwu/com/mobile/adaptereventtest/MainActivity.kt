package ddwu.com.mobile.adaptereventtest

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.adaptereventtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val dao = SubjectDao()
//        val dataList = dao.dataList
        val foods = FoodDao().foods
//        val adapter = FoodAdapter(foods)
        val adapter = BindingFoodAdapter(foods)
//        val weathers = WeatherDao().weathers
//        val adapter = CustomAdapter(weathers)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL    // 생략 가능
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        val listener = object : BindingFoodAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Toast.makeText(this@MainActivity, "${foods[position]}", Toast.LENGTH_SHORT).show()
            }
        }
        adapter.setOnItemClickListener(listener)
        val longListener = object : BindingFoodAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@MainActivity).run {
                    setTitle("Food delete")
                    setMessage("정말 삭제하시겠습니까?")
                    setPositiveButton("확인", object: DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            foods.removeAt(position)
                            adapter.notifyDataSetChanged()
                        }
                    })
                    setNegativeButton("취소", null)
                    show()
                }
            }
        }
//        val longListener = object : BindingFoodAdapter.OnItemLongClickListener {
//            override fun onItemLongClick(view: View, position: Int) {
//                foods.removeAt(position)
//                adapter.notifyDataSetChanged()
//            }
//        }
        adapter.setOnItemLongClickListener(longListener)

//        val adapter = MyAdapter(this, R.layout.list_view, dataList)
//        binding.recyclerView.adapter = adapter
//
//        binding.btnAdd.setOnClickListener{
//            dataList.add(binding.etText.text.toString())
//            adapter.notifyDataSetChanged()
//        }
    }
}