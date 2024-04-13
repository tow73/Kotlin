package ddwu.com.mobile.basicfiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.basicfiletest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val fileManager: FileManager by lazy {
        FileManager(applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        Log.d(TAG, "Internal filesDir: ${filesDir}")
        Log.d(TAG, "Internal cacheDir: ${cacheDir}")
        Log.d(TAG, "External filesDir: ${getExternalFilesDir(null).toString()}")
        Log.d(TAG, "External cacheDir: ${externalCacheDir}")

        Log.d(TAG, fileManager.getImageFileName(resources.getString(R.string.image_url)))
        Log.d(TAG, "file name: ${fileManager.getCurrentTime()}.jpg")


        mainBinding.btnWrite.setOnClickListener {
            val data = mainBinding.etText.text.toString()
            fileManager.writeText("text.txt", data)
        }

        mainBinding.btnRead.setOnClickListener {
            val data = fileManager.readText("text.txt")
            mainBinding.etText.setText(data)
        }


        mainBinding.btnReadInternet.setOnClickListener {

        }

        mainBinding.btnWriteImage.setOnClickListener {
            fileManager.writeImage("image.jpg", resources.getString(R.string.image_url))
        }

        mainBinding.btnReadImageFile.setOnClickListener {
            fileManager.readImage("image.jpg", mainBinding.imageView)
        }

    }


}