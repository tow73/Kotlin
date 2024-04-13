package ddwu.com.mobile.fooddbexam02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.fooddbexam02.data.FoodDBHelper
import ddwu.com.mobile.fooddbexam02.data.FoodDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200


    lateinit var binding : ActivityMainBinding
    lateinit var adapter : FoodAdapter
    lateinit var foods : ArrayList<FoodDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*RecyclerView 의 layoutManager 지정*/
        binding.rvFoods.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        foods = getAllFoods()               // DB 에서 모든 food를 가져옴
        adapter = FoodAdapter(foods)        // adapter 에 데이터 설정
        binding.rvFoods.adapter = adapter   // RecylcerView 에 adapter 설정

        binding.btnAdd.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQ_ADD)
        }


        /*RecyclerView 항목 클릭 시 실행할 객체*/
        val onClickListener = object : FoodAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                /*클릭 항목의 dto 를 intent에 저장 후 UpdateActivity 실행*/
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", foods.get(position) )
                startActivityForResult(intent, REQ_UPDATE)
            }
        }

        adapter.setOnItemClickListener(onClickListener)


        /*RecyclerView 항목 롱클릭 시 실행할 객체*/
        val onLongClickListener = object: FoodAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                /*롱클릭 항목의 dto 에서 id 확인 후 함수에 전달*/
                if ( deleteFood(foods.get(position).id) > 0) {
                    refreshList(RESULT_OK)
                    Toast.makeText(this@MainActivity, "삭제 완료, 대화상자 사용 고려", Toast.LENGTH_SHORT).show()
                }
            }
        }

        adapter.setOnItemLongClickListener(onLongClickListener)
    }

    /*화면이 보일 때마다 화면을 갱신하고자 할 경우에는 onResume()에 갱신작업 추가*/
//    override fun onResume() {
//        super.onResume()
//        adapter.notifyDataSetChanged()
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
    }

    /*다른 액티비티에서 DB 변경 시 DB 내용을 읽어와 Adapter 의 list 에 반영하고 RecyclerView 갱신*/
    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            foods.clear()
            foods.addAll(getAllFoods())
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
        }
    }


    /*전체 레코드를 가져와 dto 로 저장한 후 dto를 저장한 list 반환*/
    fun getAllFoods() : ArrayList<FoodDto> {
        val helper = FoodDBHelper(this)
        val db = helper.readableDatabase

//        val cursor = db.rawQuery("SELECT * FROM ${FoodDBHelper.TABLE_NAME}", null)
        val cursor = db.query(FoodDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val foods = arrayListOf<FoodDto>()

        with (cursor) {
            while (moveToNext()) {
                val id = getInt( getColumnIndex(BaseColumns._ID) )
                val food = getString ( getColumnIndex(FoodDBHelper.COL_FOOD) )
                val country = getString ( getColumnIndex(FoodDBHelper.COL_COUNTRY) )
                val dto = FoodDto(id, food, country)
                foods.add(dto)
            }
        }

        cursor.close()      // cursor 사용을 종료했으므로 close()
        helper.close()      // DB 사용이 끝났으므로 close()

        return foods
    }


    /*ID 에 해당하는 레코드를 삭제 후 삭제된 레코드 개수 반환*/
    fun deleteFood(id: Int) : Int {
        val helper = FoodDBHelper(this)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(FoodDBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }


}