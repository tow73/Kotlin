package mobile.week02.test02

import java.util.*

fun main() {
    val random = Random()
    var lotto:MutableSet<Int> = mutableSetOf()

    while(lotto.size < 6) {
        val num = random.nextInt(45)
        if(num == 0) continue
        lotto.add(num)
    }

    print("예상 번호: ")
    for(i in lotto){
        print("$i ")
    }
}