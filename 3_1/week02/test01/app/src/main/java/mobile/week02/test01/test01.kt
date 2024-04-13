package mobile.week02.test01

fun main() {
    var height:Double
    var weight:Int

    println("키(cm): ")
    height = readLine()!!.toInt() * 0.01
    println("몸무게(kg): ")
    weight = readLine()!!.toInt()

    val bmi = weight/(height*height)

    val result = when {
        bmi <= 18.5 -> "저체중"
        bmi <= 23 -> "정상"
        bmi <= 25 -> "과체중"
        else -> "비만"
    }
    println("키 ${height}cm, 몸무게 ${weight}kg의 BMI지수는 ${bmi} ${result}입니다.")
}