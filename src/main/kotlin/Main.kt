import kotlin.math.sqrt

fun main() {
    println(D0)
    println(D1)
    println(D2)
    println(D3)
    println(D4)
    println(D5)
    println(D6)
    println(D7)
    println(D8)
    println(D9)
    println()
    var x = D9
    for (i in 0..10) {
        println(x)
        x = x + x + D1
    }
}