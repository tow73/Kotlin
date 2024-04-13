package ddwu.com.mobile.photomemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import ddwu.com.mobile.photomemo.data.MemoDto
import ddwu.com.mobile.photomemo.databinding.ActivityShowMemoBinding
import java.io.File

class ShowMemoActivity : AppCompatActivity() {

    val TAG = "ShowMemoActivityTag"

    val showMemoBinding by lazy {
        ActivityShowMemoBinding.inflate(layoutInflater)
    }

    lateinit var memoDto : MemoDto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(showMemoBinding.root)

        showMemoBinding.btnModify.setOnClickListener {
            Toast.makeText(this, "Implement modifying data", Toast.LENGTH_SHORT).show()
        }

        showMemoBinding.btnClose.setOnClickListener {
            finish()
        }

        /*전달받은 intent 에서 memoDto 를 읽어온 후 각 view 에 지정*/
        val receivedIntent = intent
        if (receivedIntent.hasExtra("memo")) {
            memoDto = receivedIntent.getSerializableExtra("memo") as MemoDto

            // MemoDto에 저장된 이미지 경로를 사용하여 이미지 표시
            val imagePath = memoDto.photoName
            if (imagePath != null && File(imagePath).exists()) {
                Glide.with(this).load(imagePath).into(showMemoBinding.ivPhoto)
            }

            // 나머지 데이터 처리 (예: 메모 내용)
//            showMemoBinding.tvMemo.text = memoDto.memo
        }
    }
}