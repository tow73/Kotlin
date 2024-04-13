package mobile.week10.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import mobile.week10.recyclerviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = SubjectDao()
        val dataList = dao.dataList

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL  //생략해도 됨. default: VERTICAL
        binding.recyclerView.layoutManager = layoutManager

        val adapter = MyAdapter(this, R.layout.list_item, dataList)
        binding.recyclerView.adapter = adapter
    }
}