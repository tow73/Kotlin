package mobile.week10.intenttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mobile.week10.intenttest.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    lateinit var binding : ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}