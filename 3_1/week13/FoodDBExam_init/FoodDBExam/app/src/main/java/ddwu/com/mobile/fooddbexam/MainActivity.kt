package ddwu.com.mobile.fooddbexam

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import ddwu.com.mobile.fooddbexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var binding : ActivityMainBinding

    lateinit var helper : FoodDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = FoodDBHelper(this)

        binding.btnSelect.setOnClickListener{
            showFoods()
        }

        binding.btnAdd.setOnClickListener{  // 정해진 조건으로 추가
            addFood()
        }

        binding.btnUpdate.setOnClickListener{  // 첫 번째, 정해진 조건으로 수정
            modifyFood()
        }

        binding.btnRemove.setOnClickListener{  // 첫 번째 삭제
            //deleteFood()
            //val intent
        }
    }

    @SuppressLint("Range")
    fun addFood() {
        val db = helper.writableDatabase
        val newRow = ContentValues()

        newRow.put(FoodDBHelper.COL_FOOD, "타코")
        newRow.put(FoodDBHelper.COL_COUNTRY, "멕시코")
        db.insert(FoodDBHelper.TABLE_NAME, null, newRow)

        helper.close()
    }

    fun modifyFood() {
        val db = helper.writableDatabase
        val updateRow = ContentValues()
        updateRow.put("food", "떡볶이")
        val whereClause = "_id=?"
        val whereArgs = arrayOf("1")
        db.update(FoodDBHelper.TABLE_NAME, updateRow, whereClause, whereArgs)
        helper.close()
    }

    fun deleteFood() {
        val db = helper.writableDatabase
        val whereClause = "_id=?"
        val whereArgs = arrayOf("1")
        db.delete(FoodDBHelper.TABLE_NAME, whereClause, whereArgs)
        helper.close()
    }

    val foodList = arrayListOf<FoodDto>()

    @SuppressLint("Range")
    fun showFoods() {
        val db = helper.readableDatabase
        val columns = null
        val selection = null
        val selectionArgs = null
        val cursor : Cursor = db.query(FoodDBHelper.TABLE_NAME, columns, selection, selectionArgs,
            null, null, null, null) // select * 써도 됨

        with (cursor) {
            while(moveToNext()) {  //원래는 cursor.moveToNext()
                val no = cursor.getInt(getColumnIndex(BaseColumns._ID)) // (cursor.getColumnIndex(BaseColumns._ID))
                val food = getString(getColumnIndex("food"))
                val country = getString(getColumnIndex("country"))

                val dto = FoodDto(no, food, country)
                foodList.add(dto)
            }
        }
        cursor.close()
        helper.close()

        var result : String = ""
        for (dto in foodList) {
            result += dto.toString() + "\n"
        }
        binding.tvDisplay.text = result
    }

}