package ddwu.com.mobile.adaptereventtest

class FoodDao {
    val foods = ArrayList<FoodDto>()

    init {
        foods.add (FoodDto(R.drawable.food01, "치즈", 10))
        foods.add (FoodDto(R.drawable.food02, "치킨", 5))
        foods.add (FoodDto(R.drawable.food03, "도넛", 15))
        foods.add (FoodDto(R.drawable.food04, "사과", 20))
        foods.add (FoodDto(R.drawable.food05, "핫도그", 3))
        foods.add (FoodDto(R.drawable.food06, "파스타", 5))
        foods.add (FoodDto(R.drawable.food07, "아이스크림", 15))
    }
}