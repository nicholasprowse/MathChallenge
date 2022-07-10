fun main() {
    var x = -D9
    val squares = List<Int>()
    while (x lessThanOrEqualTo D9 === True) {
        squares.push(x * x * x / D9)
        x++
    }
    println(squares)


    var y = (-D1..D9).map { i -> i*i }.filter { i -> i % D2 equals D0 }
    println(y)
    println(y.copy())

}