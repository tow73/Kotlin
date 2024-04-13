package mobile.week09.menutest

import androidx.appcompat.app.AppCompatActivity  //앱의 하위 호환성을 위해 만들어진 activity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import mobile.week09.menutest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var selection = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        1번 활동
//        setContentView(R.layout.activity_main)
//        2번 활동
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerForContextMenu(binding.textView)
    }
//    1번 활동: menu_main / 3번 활동: menu_test
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_test, menu)
        return super.onCreateOptionsMenu(menu)
    }
//    1번, 3번 활동
    val TAG = "MainActivity"
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
//            R.id.item01 -> Log.d(TAG, "item01")
            R.id.subitem01 -> {  //1: subitem01 / 3: groupitem01
                Log.d(TAG, "subitem01")
                selection = 0 //3번 활동
            }
            R.id.subitem02 -> {  //1: subitem02 / 3: groupitem02
                Log.d(TAG, "subitem02")
                selection = 1 //3번 활동
            }
        }
        return super.onOptionsItemSelected(item)
    }
//    1번 활동
    fun onMenuClick(menuItem: MenuItem?) {
        Toast.makeText(this, "Item01!!!", Toast.LENGTH_SHORT).show()
    }
//    3번 활동
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when(selection) {
            0 -> menu?.findItem(R.id.groupitem01)?.setChecked(true)
            1 -> menu?.findItem(R.id.groupitem02)?.setChecked(true)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    //    2번 활동
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        when(v?.id) {
            R.id.textView -> menuInflater.inflate(R.menu.menu_context, menu)
        }
        super.onCreateContextMenu(menu, v, menuInfo)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.citem01 -> Log.d(TAG, "citem01")
            R.id.citem02 -> Log.d(TAG, "citem02")
        }
        return super.onContextItemSelected(item)
    }
}