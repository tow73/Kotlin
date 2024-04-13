package ddwu.com.mobile.adaptereventtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.adaptereventtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = SubjectDao()
        val dataList = dao.catList

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager

        val adapter = MyAdapter(this, R.layout.list_view, dataList)
        binding.recyclerView.adapter = adapter

        binding.btnAdd.setOnClickListener {
            dataList.add(binding.etText.text.toString())
            adapter.notifyDataSetChanged()
        }
    }
}