package ddwu.com.mobile.foodexam

data class FoodDto(var food: String, var country: String) : java.io.Serializable {
    override fun toString() = "$food ($country)"
}

