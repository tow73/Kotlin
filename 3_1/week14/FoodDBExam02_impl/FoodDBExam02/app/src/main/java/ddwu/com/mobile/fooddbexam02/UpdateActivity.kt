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
        val dto = intent.getSerializableExtra("dto") as FoodDto

        /*전달받은 값으로 화면에 표시*/
        updateBinding.etUpdateId.setText(dto.id.toString())
        updateBinding.etUpdateFood.setText(dto.food)
        updateBinding.etUpdateCountry.setText(dto.country)

        updateBinding.btnUpdateFood.setOnClickListener{
            /*dto 는 아래와 같이 기존의 dto 를 재사용할 수도 있음*/
            dto.food = updateBinding.etUpdateFood.text.toString()
            dto.country = updateBinding.etUpdateCountry.text.toString()

            if (updateFood(dto) > 0) {
                setResult(RESULT_OK)      // update 를 수행했으므로 RESULT_OK 를 반환
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


    /*update 정보를 담고 있는 dto 를 전달 받아 dto 의 id 를 기준으로 수정*/
    fun updateFood(dto: FoodDto): Int {
        val helper = FoodDBHelper(this)
        val db = helper.writableDatabase
        val updateValue = ContentValues()
        updateValue.put(FoodDBHelper.COL_FOOD, dto.food)
        updateValue.put(FoodDBHelper.COL_COUNTRY, dto.country)
        val whereCaluse = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        /*upate 가 적용된 레코드의 개수 반환*/
        val result =  db.update(FoodDBHelper.TABLE_NAME,
            updateValue, whereCaluse, whereArgs)

        helper.close()      // DB 작업 후에는 close()

        return result
    }

}