package ddwu.com.mobile.fooddbexam

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.fooddbexam.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding

    lateinit var helper : FoodDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        helper = FoodDBHelper(this)

        updateBinding.btnUpdateFood.setOnClickListener {
            val id = updateBinding.etUpdateId.text.toString()
            val food =updateBinding.etUpdateFood.text.toString()
            modifyFood(id, food)
            finish()
        }
        updateBinding.btnUpdateCancel.setOnClickListener {
            finish()
        }
    }

    fun modifyFood(id: String, food: String) {
        val db = helper.writableDatabase
        val updateRow = ContentValues()
        updateRow.put(FoodDBHelper.COL_FOOD, food)
        val whereClause = "_id=?"
        val whereArgs = arrayOf(id)
        db.update("food_table", updateRow, whereClause, whereArgs)
        helper.close()
    }
}