package dduwcom.mobile.project

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dduwcom.mobile.project.data.WordDto
import dduwcom.mobile.project.databinding.ActivityShowWordBinding

class ShowWordActivity : AppCompatActivity(), OnMapReadyCallback {
    val TAG = "ShowWordActivityTag"

    val showWordBinding by lazy {
        ActivityShowWordBinding.inflate(layoutInflater)
    }

    lateinit var wordDto : WordDto

    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(showWordBinding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        showWordBinding.btnModify.setOnClickListener {
            Toast.makeText(this, "Implement modifying data", Toast.LENGTH_SHORT).show()
        }

        showWordBinding.btnClose.setOnClickListener {
            finish()
        }

        val receivedIntent = intent
        if (receivedIntent.hasExtra("word")) {
            wordDto = receivedIntent.getSerializableExtra("word") as WordDto

            showWordBinding.showWord.text = wordDto.word
            showWordBinding.showMean.text = wordDto.meaning
            showWordBinding.showDates.text = wordDto.date
            showWordBinding.tvShowMemo.text = wordDto.memo
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        showLocationOnMap(wordDto.latitude.toDouble(), wordDto.longitude.toDouble())
    }

    private fun showLocationOnMap(latitude: Double, longitude: Double) {
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("Marker Title"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
    }
}