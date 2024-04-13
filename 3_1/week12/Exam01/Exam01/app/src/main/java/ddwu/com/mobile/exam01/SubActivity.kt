package ddwu.com.mobile.exam01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.exam01.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {

    lateinit var subBinding : ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subBinding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(subBinding.root)

        val msg = intent.getStringExtra("msg")
        subBinding.etReceivedMsg.setText(msg)

        subBinding.btnClose.setOnClickListener {
            val result = subBinding.etReceivedMsg.text.toString()
            val intent = Intent()
            intent.putExtra("result", result)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}