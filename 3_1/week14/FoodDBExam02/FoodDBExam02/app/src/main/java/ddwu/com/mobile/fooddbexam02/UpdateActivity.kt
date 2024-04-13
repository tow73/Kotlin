package ddwu.com.mobile.fooddbexam02

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import ddwu.com.mobile.fooddbexam02.data.FoodDBHelper
import ddwu.com.mobile.fooddbexam02.data.FoodDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        /*RecyclerView 에서 선택하여 전달한 dto 를 확인*/
        val dto = intent.getSerializableExtra("dto") as FoodDto  // MainActivity clickObj에서 만든 intent

        updateBinding.etUpdateId.setText(dto.id.toString())     // XML 속성에서 편집금지로 지정하였음, id, food, country 꺼내기
        updateBinding.etUpdateFood.setText(dto.food)
        updateBinding.etUpdateCountry.setText(dto.country)

        updateBinding.btnUpdateFood.setOnClickListener{  // 값 update
            dto.food = updateBinding.etUpdateFood.text.toString()  // 화면에 입력한 값 읽어서 DTO
            dto.country = updateBinding.etUpdateCountry.text.toString()

            if (updateFood(dto) > 0) {  // return값 확인
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }

            finish()
        }

        updateBinding.btnUpdateCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    /*ID 에 해당하는 레코드를 찾아 dto 의 내용으로 수정*/
    fun updateFood(dto: FoodDto): Int {
        val helper = FoodDBHelper(this)
        val db = helper.writableDatabase

        val updateValue = ContentValues()
        updateValue.put(FoodDBHelper.COL_FOOD, dto.food)  // 이 column으로 변경할 것
        updateValue.put(FoodDBHelper.COL_COUNTRY, dto.country) // 이 column으로 변경할 것
        val whereClause = "${BaseColumns._ID}=?"  // ?는 String만 가능, 정수 안 됨
        val whereArgs = arrayOf(dto.id.toString())  // 그래서 toString()으로 변경

        val resultCount =  db.update(FoodDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)  // table 업데이트

        helper.close()  // 반드시 close
        return resultCount  // 메소드 사용해야 하는 이유: 반환값 보기 위함(0보다 크면 성공, 아니면 실패)
    }

}