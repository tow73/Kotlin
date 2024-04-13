package ddwu.com.mobile.fooddbexam_arch.data

import kotlinx.coroutines.flow.Flow

class FoodRepository(private val foodDao: FoodDao) {
    val allFoods : Flow<List<Food>> = foodDao.getAllFoods()  //Flow 생략 가능

    suspend fun addFood(food: Food) {
        foodDao.insertFood(food)
    }
    suspend fun updateFood(food: String, country: String) {
        foodDao.updateFood(food, country)
    }
    suspend fun deleteFood(food: String) {
        foodDao.deleteFood(food)
    }
    suspend fun getFoodByCountry(country: String) {
        foodDao.getFoodByCountry(country)
    }
}