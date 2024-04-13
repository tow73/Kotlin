package ddwu.com.mobile.adaptereventtest

data class FoodDto (val photo: Int, val food: String, var count: Int) {
//    override fun toString(): String {
//        return "$food ($count)"
//    }
    override fun toString() = "$food ($count)"
}