package ddwu.com.mobile.fooddbexam

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
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
        binding = ActivityMainBinding.inflate(layoutInflater)  //activity_main 화면 전체
        setContentView(binding.root)

        helper = FoodDBHelper(this)

        binding.btnSelect.setOnClickListener{
            showFoods()
        }

        binding.btnAdd.setOnClickListener{
//            addFood()

            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        binding.btnUpdate.setOnClickListener{
//            modifyFood()

            val intent = Intent(this, UpdateActivity::class.java)
            startActivity(intent)
        }

        binding.btnRemove.setOnClickListener{
//            deleteFood()

            val intent = Intent(this, RemoveActivity::class.java)
            startActivity(intent)
        }

    }

    fun addFood() {
        val db = helper.writableDatabase
        val newRow = ContentValues()
        newRow.put("food", "된장찌개")
        newRow.put("country", "한국")
        db.insert("food_table", null, newRow)
        helper.close()
    }

    fun modifyFood() {
        val db = helper.writableDatabase
        val updateRow = ContentValues()
        updateRow.put("country", "한국")
        val whereClause = "food=?"
        val whereArgs = arrayOf("된장찌개")
        db.update("food_table", updateRow, whereClause, whereArgs)
        helper.close()
    }

    fun deleteFood() {
        val db = helper.writableDatabase
        val whereClause = "food=?"
        val whereArgs = arrayOf("된장찌개")
        db.delete("food_table", whereClause, whereArgs)
        helper.close()
    }

    @SuppressLint("Range")
    fun showFoods() {
        val db = helper.readableDatabase
        val columns = null
        val selection = null
        val selectionArgs = null
        val cursor = db.query(
            FoodDBHelper.TABLE_NAME, columns, selection, selectionArgs,
            null, null, null, null
        )
        val foodList = arrayListOf<FoodDto>()

        with(cursor) {
            while(moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val food = getString(getColumnIndex(FoodDBHelper.COL_FOOD))
                val country = getString(getColumnIndex(FoodDBHelper.COL_COUNTRY))
                Log.d(TAG, "$id - $food ( $country )")

                val dto = FoodDto(id, food, country)
                foodList.add(dto)
            }
        }

        cursor.close()
        helper.close()

        var data = ""
        for(dto in foodList) {
            data += dto.toString() + "\n"
        }
        binding.tvDisplay.text = data
    }

}