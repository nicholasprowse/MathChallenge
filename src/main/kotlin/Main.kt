import kotlin.math.sqrt

fun main() {
    val array = Array<Double>()

    for (i in 0..100) {
        array.append(sqrt(i.toDouble()))
    }

    for (i in array) {
        println(i)
    }
}