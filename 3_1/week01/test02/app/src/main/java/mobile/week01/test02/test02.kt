package mobile.week01.test02

fun main() {
    val data1: Array<Int> = Array(3, {0})
    data1[0] = 10
    data1[1] = 20
    data1.set(2, 30)
    println(
        """
            array size: ${data1.size}  
            array data: ${data1[0]}, ${data1[1]}, ${data1.get(2)}
        """
    )
    if(true) {
        println("test")
    } else {
        println("real")
    }
    println(if(true) {
        "test"
    } else {
        "real"
    })
    //println(true ? "test" : "real")
}