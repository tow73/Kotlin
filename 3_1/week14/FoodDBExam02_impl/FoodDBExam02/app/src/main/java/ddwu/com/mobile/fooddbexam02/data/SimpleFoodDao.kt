package ddwu.com.mobile.fooddbexam02.data

/*데이터베이스가 아닌 list 로 간단하게 구현 DAO*/
class SimpleFoodDao {
    private val foods = ArrayList<FoodDto>()

    init {
        foods.add( FoodDto(1, "불고기", "한국"))
        foods.add( FoodDto(2, "비빔밥", "한국") )
        foods.add( FoodDto(3, "마라탕", "중국") )
        foods.add( FoodDto(4, "딤섬", "중국") )
        foods.add( FoodDto(5, "스시", "일본") )
        foods.add( FoodDto(6, "오코노미야키", "일본") )
    }

    fun getAllFoods() : ArrayList<FoodDto> {
        return foods
    }

    fun addNewFood(newFoodDto : FoodDto) {
        foods.add(newFoodDto)
    }

    fun modifyFood(pos: Int, modifyFoodDto : FoodDto) {
        foods.set(pos, modifyFoodDto)
    }

    fun removeFood(removeFoodDto : FoodDto) {
        val index = foods.indexOf(removeFoodDto)
        foods.removeAt(index)
    }

    fun getFoodByPos(pos : Int) = foods.get(pos)
}