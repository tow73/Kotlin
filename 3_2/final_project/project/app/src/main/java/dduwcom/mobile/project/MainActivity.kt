package dduwcom.mobile.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import dduwcom.mobile.project.databinding.ActivityMainBinding
import dduwcom.mobile.project.data.WordDao
import dduwcom.mobile.project.data.WordDatabase
import dduwcom.mobile.project.data.WordDto
import dduwcom.mobile.project.ui.WordAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
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
                CoroutineScope(Dispatchers.IO).launch {
                    intent.putExtra("wordDto", adapter.wordList?.get(position))
                    startActivity(intent)
                }
            }
        })

        adapter.setOnItemLongClickListener(object: WordAdapter.OnWordItemLongClickListener{
            override fun onItemLongClick(position: Int): Boolean {
                showDeleteDialog(position)
                return true
            }
        })

        mainBinding.btnMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                startActivity(intent)
            }
        }
        onResume()
    }

    override fun onResume() {
        super.onResume()
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

    fun showDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("이 아이템을 삭제하시겠습니까?")
            .setPositiveButton("Delete") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    adapter.wordList?.let { wordDao.deleteWord(it.get(position)) }
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}