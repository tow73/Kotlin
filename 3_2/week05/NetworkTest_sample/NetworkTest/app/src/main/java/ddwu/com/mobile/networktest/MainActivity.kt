package ddwu.com.mobile.networktest


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import ddwu.com.mobile.networktest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding
    lateinit var networkManager : NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkManager = NetworkManager(this)


        val pol = StrictMode.ThreadPolicy.Builder().permitNetwork().build()  //mainthread 사용 가능하도록 임시로 설정
        StrictMode.setThreadPolicy(pol)


        binding.btnConnInfo.setOnClickListener{
            getNetworkInfo()
        }

        binding.btnActiveInfo.setOnClickListener {
            Log.d(TAG, "Network is connected: ${isOnline()}")
        }

        binding.btnDown.setOnClickListener {
//            val url = "https://httpbin.org/get?user=somsom&dept=computer"
            val url = resources.getString(R.string.kobis_url) + binding.etDate.text.toString()
            binding.tvDisplay.text = networkManager.downloadText(url)
        }

        binding.btnImg.setOnClickListener {
            val imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2tuoLt3wVnUbbmP40JbeBLcqHJ1dWO8Tfgw&usqp=CAU"
            val result = networkManager.downloadImage(imageUrl)
            binding.ivDisplay.setImageBitmap(result)
        }

        binding.btnSend.setOnClickListener {
            binding.tvDisplay.text = networkManager.sendPostData("https://httpbin.org/post")
        }

    }


    private fun getNetworkInfo() {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn: Boolean = false
        var isMobileConn: Boolean = false

        connMgr.allNetworks.forEach { network ->
            connMgr.getNetworkInfo(network)?.apply {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = isWifiConn or isConnected
                }
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = isMobileConn or isConnected
                }
            }
        }

        Log.d(TAG, "Wifi connected: $isWifiConn")
        Log.d(TAG, "Mobile connected: $isMobileConn")
    }


    private fun isOnline(): Boolean { //최소한 구현되어 있어야 함
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

}