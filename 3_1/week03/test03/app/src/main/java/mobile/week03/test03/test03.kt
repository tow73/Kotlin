interface Super {
    var data : Int
    fun some()
}
val obj = object : Super {
    override var data = 10
    override fun some() {
        println("data: $data")
    }
}
fun main() {
    obj.data = 20
    obj.some()
}