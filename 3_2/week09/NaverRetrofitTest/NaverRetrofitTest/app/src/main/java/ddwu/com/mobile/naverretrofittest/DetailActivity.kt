package ddwu.com.mobile.naverretrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import ddwu.com.mobile.naverretrofittest.databinding.ActivityDetailBinding
import java.io.File

class DetailActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    val fileManager: FileManager by lazy {
        FileManager(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)
        val url = intent.getStringExtra("url")
        val imageName = fileManager.getCurrentTime() + ".jpg"

        detailBinding.btnSave.setOnClickListener {
            if (url != null) {
                fileManager.writeImage(imageName, url)
                Log.d(TAG, "Image saved")
            }
        }

        detailBinding.btnRead.setOnClickListener {
            fileManager.readImage(imageName, detailBinding.imgBookCover)
            Log.d(TAG, "Image read")
        }

        detailBinding.btnInit.setOnClickListener {
            detailBinding.imgBookCover.setImageResource(R.mipmap.ic_launcher)
        }

        detailBinding.btnRemove.setOnClickListener {
            val file = File(filesDir, imageName)
            if (file.exists()) {
                file.delete()
                Log.d(TAG, "Image deleted")
            }
        }
    }
}