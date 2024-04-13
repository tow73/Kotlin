typealias myFunc = (Int, Int) -> Boolean

fun main() {
//    fun some(no1:Int, no2:Int): Boolean {
//        println("call some")
//        return no1 > no2
//    }
//    val some: (Int, Int) -> Boolean = {
//        no1:Int, no2:Int ->
//        println("call some")
//        no1 > no2
//    }
//    val some: (Int, Int) -> Boolean = {
//        no1, no2 ->
//        println("call some")
//        no1 > no2
//    }
//    val some:myFunc = {
//        no1, no2 ->
//        println("call some")
//        no1 > no2
//    }
//    val some = {
//        no1:Int, no2:Int ->
//        println("call some")
//        no1 > no2
//    }

//    println("result: ${some(10, 20)}")

    fun hofFun(arg: (Int) -> Boolean) {  //매개변수로 람다함수 들어옴, 매개변수 int 타입 반환값이 boolean인 함수
        println(arg(10))  //밑의 some 함수 호출
    }

    val some: (Int) -> Boolean = {
        no1 ->
        println("call some $no1")
        true
    }

    hofFun(some)

    val newFun = {no: Int -> no > 10}
    hofFun(newFun)
}