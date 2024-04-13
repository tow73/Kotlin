package ddwu.com.mobile.fooddbexam_arch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.fooddbexam_arch.data.Food
import ddwu.com.mobile.fooddbexam_arch.data.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel (var repository: FoodRepository) : ViewModel() {
    var allFoods: LiveData<List<Food>> = repository.allFoods.asLiveData()  //flowList를 LiveData로 변경(LiveData: 보고 있는 것만 관찰)

    fun addFood(food: Food) = viewModelScope.launch {   //viewModel 작업 계속 유지
        repository.addFood(food)
    }
    fun updateFood(food: String, country: String) = viewModelScope.launch {
        repository.updateFood(food, country)
    }
    fun deleteFood(food: String) = viewModelScope.launch {
        repository.deleteFood(food)
    }
    fun getFoodByCountry(country: String) = viewModelScope.launch {
        repository.getFoodByCountry(country)
    }
}