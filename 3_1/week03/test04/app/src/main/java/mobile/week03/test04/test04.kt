fun main() {
//    fun some(no:Int):Unit {  //매개변수 integer, 반환타입 unit(없음)
//        println(no)
//    }

    val varName = 10
    val some = {no: Int -> println(no)}
//    val varName:Int = 0
//    val some: (Int) -> Unit = {println(it)}  //it은 앞의 변수를 가리킴
//    val some: (Int) -> Unit = {no:Int -> println(no)}

//    val 변수이름: 변수타입 = 변수값

    fun test1(a:Int) {
        //
    }
    test1(varName)

    fun test2(a:(Int)->Unit) {  //변수 타입이 함수
        //
    }
    test2(some)

    some(10)
}