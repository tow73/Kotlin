package ddwu.com.mobile.roomexam01.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAllFoods() : Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE country = :country")
    suspend fun showCountryFoods(country: String) : List<Food>

    @Insert
    suspend fun insertFood(vararg food : Food)

//    @Update
    @Query("UPDATE food_table SET country = :country WHERE food = :foodName")
    suspend fun updateFood ( foodName : String, country : String)

//    @Delete
    @Query("DELETE FROM food_table WHERE food = :foodName")
    suspend fun deleteFood(foodName : String)
}