package ddwu.com.mobile.fooddbexam_arch.data

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val foodDao: FoodDao) {
    val allFoods : Flow<List<Food>> = foodDao.getAllFoods()

    suspend fun addFood(food: Food) {
        foodDao.insertFood(food)
    }

    suspend fun modifyFood(foodName: String, country: String) {
        foodDao.updateFood(foodName, country)
    }

    suspend fun removeFood(foodName: String) {
        foodDao.deleteFood(foodName)
    }

    suspend fun getFoodByCountry(country: String) : List<Food> = foodDao.getFoodByCountry(country)
}