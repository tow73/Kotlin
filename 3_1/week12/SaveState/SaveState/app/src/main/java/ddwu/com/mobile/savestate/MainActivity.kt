package ddwu.com.mobile.savestate

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import ddwu.com.mobile.savestate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {  //여기에서도 임시로 보관할 수 있음
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myView.x = savedInstanceState?.getInt("x") ?: 100
        Log.d(TAG, "Main: (${binding.myView.x}, ${binding.myView.y})")

        val pref = getSharedPreferences("save_state", 0)
        binding.myView.y = pref.getInt("y", 300)
    }

    val TAG = "MainActivity"

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {  //영구 저장
        super.onPause()
        val pref : SharedPreferences = getSharedPreferences("save_state", 0)  //save_state라는 이름의 파일을 만들어 저장
        val editor : SharedPreferences.Editor = pref.edit()  //편집할 때 사용
        editor.putInt("y", binding.myView.y)
        editor.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("x", binding.myView.x)
        outState.putInt("y", binding.myView.y)
        Log.d(TAG, "Save: (${binding.myView.x}, ${binding.myView.y})")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {  //on SaveInstanceState에서 보관했던 것 꺼냄, 임시 저장
        super.onRestoreInstanceState(savedInstanceState)
        binding.myView.x = savedInstanceState.getInt("x")
        binding.myView.y = savedInstanceState.getInt("y")
        Log.d(TAG, "Restore: (${binding.myView.x}, ${binding.myView.y})")
    }
}