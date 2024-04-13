package mobile.week04.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear)

        val textView: TextView = findViewById(R.id.textView)
//        val textView = findViewByID<TextView>(R.id.textView)

        val btnClick = findViewById<Button>(R.id.btnClick)
//        btnClick.setOnClickListener {textView.visibility = View.INVISIBLE}
//        btnClick.setOnClickListener({textView.visibility = View.INVISIBLE})
//        btnClick.setOnClickListener() {textView.visibility = View.INVISIBLE}

        val etText = findViewById<EditText>(R.id.etText)

        btnClick.setOnClickListener {
            val msg = etText.getText().toString()
            textView.setText(msg)

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}