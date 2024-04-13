package ddwu.com.mobile.foodexam

class FoodDao {
    val foods = ArrayList<FoodDto>()

    init {
        foods.add( FoodDto("불고기", "한국") )
        foods.add( FoodDto("비빔밥", "한국") )
        foods.add( FoodDto("마라탕", "중국") )
        foods.add( FoodDto("딤섬", "중국") )
        foods.add( FoodDto("스시", "일본") )
        foods.add( FoodDto("오코노미야키", "일본") )
    }
}