package mobile.week02.test00

fun main() {
    var sum:Int = 0
    var num:Int =0

    for(i in 1..5){
        num = readLine()!!.toInt()
        sum += readLine()!!.toInt()
    }
    println("sum: $sum")
    println("avg: ${sum/5}")
}