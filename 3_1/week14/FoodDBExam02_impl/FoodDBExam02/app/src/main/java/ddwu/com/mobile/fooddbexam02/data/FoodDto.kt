package ddwu.com.mobile.fooddbexam02.data

import java.io.Serializable

/*Intent에 저장하여야 하므로 Serializable 인터페이스 구현*/
data class FoodDto(val id: Int, var food: String, var country: String) : Serializable {
    override fun toString() = "$id - $food ( $country )"
}
