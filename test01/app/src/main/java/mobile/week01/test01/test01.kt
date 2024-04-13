package mobile.week01.test01

var inputValue:Int = 0
//var inputValue:Int?  //null선언 위해서는 이렇게 써줘야 함

val data:Int by lazy{
    println("lazy init")
    inputValue
}

fun main() {
//    inputValue = null //kotlin의 nullsaft
    inputValue = 5
    println(data)

    val str1 = "Hello \n World"
    val str2 = """
        Hellov 
        World
    """

    println("str1: $str1")
    println("str2: $str2")
}