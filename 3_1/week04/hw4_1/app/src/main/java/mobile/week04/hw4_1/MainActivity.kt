package mobile.week04.hw4_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear)

        val btnHClick = findViewById<Button>(R.id.btnHello)
        val btnEClick = findViewById<Button>(R.id.btnExit)
        val name = findViewById<EditText>(R.id.etName)
        val phone = findViewById<EditText>(R.id.etPhone)

        btnHClick.setOnClickListener {
            val msg1 = name.getText().toString()
            val msg2 = phone.getText().toString()

            Toast.makeText(this, "안녕하세요. 저는 " + msg1 + "입니다. 전화번호는 " + msg2 + "입니다.", Toast.LENGTH_SHORT).show()
        }
        btnEClick.setOnClickListener {
            finish()
        }
    }
}