package dduwcom.mobile.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dduwcom.mobile.project.data.WordDao
import dduwcom.mobile.project.data.WordDatabase
import dduwcom.mobile.project.databinding.ActivityMainBinding
import dduwcom.mobile.project.ui.WordAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val wordDB : WordDatabase by lazy {
        WordDatabase.getDatabase(this)
    }

    val wordDao : WordDao by lazy {
        wordDB.wordDao()
    }

    val adapter : WordAdapter by lazy {
        WordAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.rvWord.adapter = adapter
        mainBinding.rvWord.layoutManager = LinearLayoutManager(this)

        mainBinding.btnAddNew.setOnClickListener {
            val intent = Intent (this, AddWordActivity::class.java)
            startActivity(intent)
        }

        adapter.setOnItemClickListener(object: WordAdapter.OnWordItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent (this@MainActivity, ShowWordActivity::class.java )
                intent.putExtra("wordDto", adapter.wordList?.get(position))
                startActivity(intent)
            }
        })

        showAllWord()
    }

    fun showAllWord() {
        CoroutineScope(Dispatchers.Main).launch {
            wordDao.getAllWords().collect { words ->
                adapter.wordList = words
                adapter.notifyDataSetChanged()
            }
        }
    }
}