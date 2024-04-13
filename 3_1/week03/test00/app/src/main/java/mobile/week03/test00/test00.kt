class User(val name: String) {
    init {
        println("init $name")
    }

    constructor(name: String, count: Int) : this(name) {
        println("sub $name $count")
    }

    fun someFun() {
        println("init $name")
    }
}
fun main() {
    val User = User("test", 10)
}