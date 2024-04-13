package ddwu.com.mobile.fooddbexam02.data

import java.io.Serializable

data class FoodDto(val id: Int, var food: String, var country: String) : Serializable {
    override fun toString() = "$id - $food ( $country )"
}
