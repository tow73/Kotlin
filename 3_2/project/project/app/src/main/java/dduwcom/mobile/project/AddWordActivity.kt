package dduwcom.mobile.project

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import dduwcom.mobile.project.data.WordDao
import dduwcom.mobile.project.data.WordDatabase
import dduwcom.mobile.project.databinding.ActivityAddWordBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dduwcom.mobile.project.data.WordDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale

        class AddWordActivity : AppCompatActivity() {
            val TAG = "AddWordActivityTag"

            val addWordBinding by lazy {
                ActivityAddWordBinding.inflate(layoutInflater)
            }

            val wordDB: WordDatabase by lazy {
                WordDatabase.getDatabase(this)
            }

            val wordDao: WordDao by lazy {
                wordDB.wordDao()
            }

            val source = "en"
            val target = "ko"
            var meaning: String? = null

            private lateinit var fusedLocationClient : FusedLocationProviderClient
            private lateinit var geocoder : Geocoder
            private lateinit var currentLoc : Location
            private lateinit var googleMap : GoogleMap
            var centerMarker : Marker? = null

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(addWordBinding.root)

                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                geocoder = Geocoder(this, Locale.getDefault())

                addWordBinding.btnAdd.setOnClickListener {
                    if (meaning != null) {
                        val word = addWordBinding.editWord.text.toString()
                        val date = addWordBinding.editTextDate.text.toString()
                        val memo = addWordBinding.tvAddMemo.text.toString()

                        checkPermissions()
                        startLocUpdates()
                        addMarker(LatLng(currentLoc.latitude, currentLoc.longitude))

                        CoroutineScope(Dispatchers.IO).launch {
                            wordDao.insertWord(WordDto(0, word, meaning!!, date, currentLoc.latitude.toLong(), currentLoc.longitude.toLong(), memo))
                        }

                        Toast.makeText(this@AddWordActivity, "New word is added!", Toast.LENGTH_SHORT).show()

                        fusedLocationClient.removeLocationUpdates(locCallback)
                    }
                }

                addWordBinding.translate.setOnClickListener {
                    val word = addWordBinding.editWord.text.toString()
                    val queue: RequestQueue = Volley.newRequestQueue(this)

                    if (word == null) {
                        Toast.makeText(applicationContext, "번역할 단어를 입력하세요", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val request = object : StringRequest(Request.Method.POST, resources.getString(R.string.api_url),
                        { response ->
                            try {
                                val result = JSONObject(response)
                                val translatedText =
                                    result.getJSONObject("message").getJSONObject("result").getString("translatedText")
                                addWordBinding.showMeaning.setText(translatedText)
                                meaning = addWordBinding.showMeaning.text.toString()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        { error -> }
                    ) {
                        override fun getHeaders(): Map<String, String> {
                            val params: MutableMap<String, String> = HashMap()
                            params["Content-Type"] = "application/x-www-form-urlencoded; charset=UTF-8"
                            params["X-Naver-Client-Id"] = resources.getString(R.string.client_id)
                            params["X-Naver-Client-Secret"] = resources.getString(R.string.client_secret)
                            return params
                        }

                        override fun getParams(): Map<String, String> {
                            val params: MutableMap<String, String> = HashMap()
                            params["source"] = source
                            params["target"] = target
                            params["text"] = word
                            return params
                        }
                    }
                    queue.add(request)
                }
            }

            fun checkPermissions () {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions are already granted", Toast.LENGTH_SHORT).show()
                } else {
                    locationPermissionRequest.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                }
            }

            val locationPermissionRequest
                    = registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions() ) {
                    permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        Toast.makeText(this, "FINE_LOCATION is granted", Toast.LENGTH_SHORT).show()
                    }

                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        Toast.makeText(this, "COARSE_LOCATION is granted", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "Location permissions are required", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val locRequest = LocationRequest.Builder(5000)
                .setMinUpdateIntervalMillis(3000)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .build()
            val locCallback : LocationCallback = object : LocationCallback() {
                @SuppressLint("NewApi")
                override fun onLocationResult(locResult: LocationResult) {
                    currentLoc = locResult.locations.get(0)
                    val targetLoc: LatLng = LatLng(currentLoc.latitude, currentLoc.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 17F))
                }
            }

            @SuppressLint("MissingPermission")
            private fun startLocUpdates() {
                fusedLocationClient.requestLocationUpdates(
                    locRequest,     // LocationRequest 객체
                    locCallback,    // LocationCallback 객체
                    Looper.getMainLooper()  // System 메시지 수신 Looper
                )
            }

            fun addMarker(targetLoc: LatLng) {  // LatLng(37.606320, 127.041808)
                val markerOptions: MarkerOptions = MarkerOptions()  // 마커 표현
                markerOptions.position(targetLoc)  // 필수
                    .title("마커 제목")
                    .snippet("마커 말풍선")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                centerMarker = googleMap.addMarker(markerOptions)
                centerMarker?.showInfoWindow()
                centerMarker?.tag = "database_id"
            }
        }