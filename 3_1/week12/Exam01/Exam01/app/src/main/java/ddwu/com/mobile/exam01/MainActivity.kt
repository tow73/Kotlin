package ddwu.com.mobile.exam01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.exam01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener{

            val msg = binding.etReceivedMsg.text.toString()

            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("msg", msg)
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if(resultCode == RESULT_OK) {
                    val result = data?.getStringExtra("result")
                    binding.etReceivedMsg.setText(result)
                }
            }
        }
    }
}