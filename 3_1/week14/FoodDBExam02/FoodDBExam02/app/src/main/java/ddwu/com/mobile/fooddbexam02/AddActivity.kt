package ddwu.com.mobile.fooddbexam02

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import ddwu.com.mobile.fooddbexam02.data.FoodDBHelper
import ddwu.com.mobile.fooddbexam02.data.FoodDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var addBinding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        /*EditText에서 값을 읽어와 DB에 저장*/
        addBinding.btnAddFood.setOnClickListener{  // return 값 확인(0이상 추가)
            val food = addBinding.etAddFood.text.toString()
            val country = addBinding.etAddCountry.text.toString()
            val newDto = FoodDto(0, food, country) // id에는 아무 값이나 넣어도 됨, 사용 안 함

            if (addFood(newDto) > 0) {  // return값 확인
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }
        addBinding.btnAddCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

    }

    fun addFood(newDto : FoodDto) : Long {
        val helper = FoodDBHelper(this)
        val db = helper.writableDatabase
        val newValue = ContentValues()

        newValue.put(FoodDBHelper.COL_FOOD, newDto.food)
        newValue.put(FoodDBHelper.COL_COUNTRY, newDto.country)
        val result = db.insert(FoodDBHelper.TABLE_NAME, null, newValue)

        helper.close()

        return result
    }

}