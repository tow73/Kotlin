package ddwu.com.mobile.lbstest

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import ddwu.com.mobile.lbstest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var geocoder : Geocoder
    private lateinit var currentLoc : Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        geocoder = Geocoder(this, Locale.getDefault())

        mainBinding.btnPermit.setOnClickListener {
            checkPermissions ()
        }

        mainBinding.btnLastLoc.setOnClickListener {
            getLastLocation()
//            callExternalMap()
        }


        mainBinding.btnLocStart.setOnClickListener {
            startLocUpdates()
        }

        mainBinding.btnLocStop.setOnClickListener {
            fusedLocationClient.removeLocationUpdates(locCallback)
//            callExternalMap()
        }

        mainBinding.btnLocTitle.setOnClickListener {  // Android ver.33 이상
            getLocationTitle()
        }

//        showData("Geocoder isEnabled: ${Geocoder.isPresent()}")
    }


    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locCallback)
    }


    fun checkPermissions () {
        if (checkSelfPermission(ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            showData("Permissions are already granted")  // textView에 출력
        } else {
            locationPermissionRequest.launch(arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION))
        }
    }

    /*registerForActivityResult 는 startActivityForResult() 대체*/
    val locationPermissionRequest
        = registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions() ) {
            permissions ->
                when {
                    permissions.getOrDefault(ACCESS_FINE_LOCATION, false) -> {
                        showData("FINE_LOCATION is granted")
                    }
                    permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) -> {
                        showData("COARSE_LOCATION is granted")
                    }
                    else -> {
                        showData("Location permissions are required")
                    }
                }
        }


    /* 위치 정보 수신 결과 전달 받는 클래스 */
    val locCallback : LocationCallback = object: LocationCallback() {
        override fun onLocationResult(locResult: LocationResult) {
            currentLoc = locResult.locations[0]
            showData("위도: ${currentLoc.latitude}, 경도: ${currentLoc.longitude}")
        }
    }

    /* 위치 정보 수신 조건 */
    val locRequest : LocationRequest = LocationRequest.Builder(5000)
        .setMinUpdateIntervalMillis(10000)
        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        .build()

    @SuppressLint("MissingPermission")
    private fun startLocUpdates() {
        fusedLocationClient.requestLocationUpdates(locRequest, locCallback, Looper.getMainLooper())
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                showData(location.toString())
            }
        }
        fusedLocationClient.lastLocation.addOnFailureListener {e: Exception ->
            Log.d(TAG, e.toString())
        }
    }

    private fun getLocationTitle() {
        geocoder.getFromLocation(currentLoc.latitude, currentLoc.longitude, 5) {
                address -> CoroutineScope(Dispatchers.Main).launch {
                    showData(address.get(0).getAddressLine(0).toString())
                }
        }
//            geocoder.getFromLocationName("동덕여자대학교", 5) {
//                    address -> CoroutineScope(Dispatchers.Main).launch {
//                        showData(address.get(0).toString())
//                    }
//            }
    }


    fun callExternalMap() {
        val locLatLng   // 위도/경도 정보로 지도 요청 시
                = String.format("geo:%f,%f?z=%d", 37.606320, 127.041808, 17)
        val locName     // 위치명으로 지도 요청 시
                = "https://www.google.co.kr/maps/place/" + "Hawolgok-dong"
        val route       // 출발-도착 정보 요청 시(잘 구현 안 됨)
            = String.format("https://www.google.co.kr/maps?saddr=%f,%f&daddr=%f,%f",
                            37.606320, 127.041808, 37.601925, 127.041530)
        val uri = Uri.parse(locLatLng)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


    private fun showData(data : String) {
        mainBinding.tvData.setText(mainBinding.tvData.text.toString() + "\n${data}")
    }

}