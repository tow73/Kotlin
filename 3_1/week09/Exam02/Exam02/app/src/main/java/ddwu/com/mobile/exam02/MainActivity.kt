package ddwu.com.mobile.exam02

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ddwu.com.mobile.exam02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerForContextMenu(binding.myCustomView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_size, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.big -> binding.myCustomView.r += 50
            R.id.small -> binding.myCustomView.r -= 50
        }
        binding.myCustomView.invalidate()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.menu_color, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.c_red -> binding.myCustomView.paintColor = Color.RED
            R.id.c_green -> binding.myCustomView.paintColor = Color.GREEN
            R.id.c_blue -> binding.myCustomView.paintColor = Color.BLUE
        }
        binding.myCustomView.invalidate()
        return super.onContextItemSelected(item)
    }
}