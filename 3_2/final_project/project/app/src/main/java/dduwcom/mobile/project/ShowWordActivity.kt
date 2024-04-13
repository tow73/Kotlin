package dduwcom.mobile.project

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dduwcom.mobile.project.data.WordDao
import dduwcom.mobile.project.data.WordDatabase
import dduwcom.mobile.project.data.WordDto
import dduwcom.mobile.project.databinding.ActivityShowWordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowWordActivity : AppCompatActivity(), OnMapReadyCallback {
    val TAG = "ShowWordActivityTag"

    val showWordBinding by lazy {
        ActivityShowWordBinding.inflate(layoutInflater)
    }
    val wordDB: WordDatabase by lazy {
        WordDatabase.getDatabase(this)
    }
    val wordDao: WordDao by lazy {
        wordDB.wordDao()
    }

    lateinit var wordDto : WordDto
    lateinit var googleMap: GoogleMap

    lateinit var loc :LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(showWordBinding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        val receivedIntent = intent
        wordDto = receivedIntent.getSerializableExtra("wordDto") as WordDto

        showWordBinding.showWord.setText(wordDto.word)
        showWordBinding.showMeaning.setText(wordDto.meaning)
        showWordBinding.showDate.setText(wordDto.date)
        showWordBinding.showMemo.setText(wordDto.memo.toEditable())
        loc = LatLng(wordDto.latitude, wordDto.longitude)
        mapFragment.getMapAsync(this)

        showWordBinding.btnModify.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                wordDao.updateWord(WordDto(wordDto.id, showWordBinding.showWord.text.toString()
                    , showWordBinding.showMeaning.text.toString()
                    , showWordBinding.showDate.text.toString()
                    , loc.latitude, loc.longitude
                    , showWordBinding.showMemo.text.toString()))
            }
            finish()
        }

        showWordBinding.btnClose.setOnClickListener {
            finish()
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        showLocationOnMap(wordDto.latitude, wordDto.longitude)
    }

    private fun showLocationOnMap(latitude: Double, longitude: Double) {
        val location = LatLng(wordDto.latitude, wordDto.longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("Marker Title"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
    }
}