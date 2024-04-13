class NonData(val name:String, val email:String, val count:Int)
data class Data(val name:String, val email:String, val count:Int)
fun main() {
    val non1 = NonData("test", "a@a.com", 10)
    val non2 = NonData("test", "a@a.com", 10)

    val data1 = Data("test", "a@a.com", 10)
    val data2 = Data("test", "a@a.com", 10)

    println("non $non1 ${non1.equals(non2)}")
    println("non $data1 ${data1.equals(data2)}")
}