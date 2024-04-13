package dduwcom.mobile.project

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dduwcom.mobile.project.data.WordDao
import dduwcom.mobile.project.data.WordDatabase
import dduwcom.mobile.project.data.WordDto
import dduwcom.mobile.project.databinding.ActivityMapBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "MapActivity"

    private lateinit var googleMap: GoogleMap
    private lateinit var wordDao: WordDao

    private val mapBinding by lazy {
        ActivityMapBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mapBinding.root)

        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        wordDao = WordDatabase.getDatabase(this).wordDao()

        mapBinding.btnMapBack.setOnClickListener {
            finish()
        }
    }

    private fun showAllWordLocationsOnMap() {
        lifecycleScope.launch {
            val wordList: List<WordDto> = wordDao.getAllWords().first()

            for (word in wordList) {
                val location = LatLng(word.latitude.toDouble(), word.longitude.toDouble())
                googleMap.addMarker(MarkerOptions().position(location).title(word.word))
            }

            // (Optional) 모든 위치를 포함하는 경계 계산 및 지도 이동
            val builder = LatLngBounds.Builder()
            for (word in wordList) {
                builder.include(LatLng(word.latitude.toDouble(), word.longitude.toDouble()))
            }
            val bounds = builder.build()
            val padding = 50 // 지도 경계와 화면 경계 간의 여유 공간
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        Log.d(TAG, "GoogleMap is ready")

        googleMap.setOnMarkerClickListener { marker ->
            val word = marker.title
            lifecycleScope.launch {
                val meaningList = word?.let { wordDao.getMeaningByWord(it) }
                if (meaningList?.isNotEmpty()!!) {
                    val meaning = meaningList[0].meaning
                    Toast.makeText(this@MapActivity, meaning, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MapActivity, "Meaning not found", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }

        googleMap.setOnInfoWindowClickListener { marker ->
            Toast.makeText(this@MapActivity, marker.title, Toast.LENGTH_SHORT).show()
        }

        googleMap.setOnMapClickListener { latLng: LatLng ->
            Toast.makeText(applicationContext, latLng.toString(), Toast.LENGTH_SHORT).show()
        }

        googleMap.setOnMapClickListener { latLng: LatLng ->
            Toast.makeText(applicationContext, latLng.toString(), Toast.LENGTH_SHORT).show()
        }

        showAllWordLocationsOnMap()
    }
}
