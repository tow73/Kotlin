package ddwu.com.mobile.fooddbexam_arch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.fooddbexam_arch.data.Food
import ddwu.com.mobile.fooddbexam_arch.data.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel (val repository : FoodRepository) : ViewModel() {
    var allFoods: LiveData<List<Food>> = repository.allFoods.asLiveData()

    fun addFood(food: Food) = viewModelScope.launch {
        repository.addFood(food)
    }

    fun modifyFood(foodName: String, country: String) = viewModelScope.launch {
        repository.modifyFood(foodName, country)
    }

    fun removeFood(foodName: String) = viewModelScope.launch {
        repository.removeFood(foodName)
    }
}

// FoodViewModelFactory 를 별개의 클래스로 작성하는 것도 가능
class FoodViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}