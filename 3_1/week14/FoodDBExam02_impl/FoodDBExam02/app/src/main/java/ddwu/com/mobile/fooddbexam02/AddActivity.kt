package ddwu.com.mobile.fooddbexam02

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.fooddbexam02.data.FoodDBHelper
import ddwu.com.mobile.fooddbexam02.data.FoodDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    val TAG = "AddActivity"

    lateinit var addBinding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        /*추가버튼 클릭*/
        addBinding.btnAddFood.setOnClickListener{
            val food = addBinding.etAddFood.text.toString()
            val country = addBinding.etAddCountry.text.toString()
            val newDto = FoodDto(0, food, country)      // 화면 값으로 dto 생성, id 는 임의의 값 0

            if ( addFood(newDto) > 0) {
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }

        /*취소버튼 클릭*/
        addBinding.btnAddCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }


    /*추가할 정보를 담고 있는 dto 를 전달받아 DB에 추가, id 는 autoincrement 이므로 제외
    * DB추가 시 추가한 항목의 ID 값 반환, 추가 실패 시 -1 반환 */
    fun addFood(newDto : FoodDto) : Long  {
        val helper = FoodDBHelper(this)
        val db = helper.writableDatabase

        val newValues = ContentValues()
        newValues.put(FoodDBHelper.COL_FOOD, newDto.food)
        newValues.put(FoodDBHelper.COL_COUNTRY, newDto.country)

        /*insert 한 항목의 id 를 반환*/
        val result = db.insert(FoodDBHelper.TABLE_NAME, null, newValues)

        helper.close()      // DB 작업 후 close() 수행

        return result
    }



}