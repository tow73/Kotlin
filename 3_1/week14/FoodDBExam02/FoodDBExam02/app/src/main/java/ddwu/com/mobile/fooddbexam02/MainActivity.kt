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
//    lateinit var foodDao : FoodDao()  // 최종 과제 예시

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

        val clickObj = object : FoodAdapter.OnItemClickListener {  // 클릭하는 기능 구현 x
            override fun onItemClick(view: View, position: Int) {  // FoodAdapter의 FoodViewHolder에서의 itemBinding.root.setOnClickListener이 호출
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", foods.get(position) )   // 클릭한 RecyclerView 항목 위치의 dto 전달
                startActivityForResult(intent, REQ_UPDATE)      // 수정결과를 받아오도록 Activity 호출
            }
        }
        adapter.setOnItemClickListener(clickObj)
//        adapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {  // 클릭하는 기능 구현 x
//            override fun onItemClick(view: View, position: Int) {  // FoodAdapter의 FoodViewHolder에서의 itemBinding.root.setOnClickListener이 호출
//                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
//                intent.putExtra("dto", foods.get(position) )   // 클릭한 RecyclerView 항목 위치의 dto 전달
//                startActivityForResult(intent, REQ_UPDATE)      // 수정결과를 받아오도록 Activity 호출
//            }
//        })

        val longClickObj = object : FoodAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                deleteFood(foods.get(position).id)  // position 위치의 dto id를 사용해 db 삭제
                refreshList(RESULT_OK)   // RecyclerView 갱신작업
            }
        }
        adapter.setOnItemLongClickListener(longClickObj)
    }

    override fun onResume() {
        super.onResume()
//        adapter.notifyDataSetChanged()   // 액티비티가 보일 때마다 RecyclerView 를 갱신하고자 할 경우
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_UPDATE -> {  // 같은 코드이므로 함수로 뽑을 수 있음
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
    }

    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {  // DB data 변경
            foods.clear()                       // 기존 항목 제거
            foods.addAll(getAllFoods())         // 항목 추가(바꿔치기x)
            adapter.notifyDataSetChanged()      // RecyclerView 갱신
        } else {
            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllFoods() : ArrayList<FoodDto> {  // FoodDao로 옮김 class FoodDao(val context: Context)
        val helper = FoodDBHelper(this)  // 최초 1회만 호출
        val db = helper.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM ${FoodDBHelper.TABLE_NAME}", null)  // SQL 직접 작성: 테이블에서 모든 column 가져 와라(권장x)
        val cursor = db.query(FoodDBHelper.TABLE_NAME, null, null, null, null, null, null) // 메소드 방식(권장)

        val foods = arrayListOf<FoodDto>()
        with (cursor) {
            while (moveToNext()) {  // cursor.moveToNext()
                val id = getInt( getColumnIndex(BaseColumns._ID) )  // column의 이름을 몇 번째인지 확인(column의 순서)(원래 cursor.~)
                val food = getString ( getColumnIndex(FoodDBHelper.COL_FOOD) )  // FoodDBHelper.~ = "food"(원래 cursor.~)
                val country = getString ( getColumnIndex(FoodDBHelper.COL_COUNTRY) )  // (원래 cursor.~)
                val dto = FoodDto(id, food, country)
                foods.add(dto)  // 리스트 생성
            }
        }
        return foods
    }

    fun deleteFood(id: Int) {
        // 대화상자 떠서 확인 or 취소
    }

}