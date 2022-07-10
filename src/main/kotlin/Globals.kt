val True = Boolean.True
val False = Boolean.False

val D0 = Int.ZERO
val D1 = Int.ONE
val D2 = Int.TWO
val D3 = Int.THREE
val D4 = Int.FOUR
val D5 = Int.FIVE
val D6 = Int.SIX
val D7 = Int.SEVEN
val D8 = Int.EIGHT
val D9 = Int.NINE

enum class ComparisonResult {
    LESS, EQUAL, GREATER
}

val Any?.isNull: Boolean get() {
    if(this === null) {
        return True
    }
    return False
}

val Any?.isNotNull: Boolean get() {
    if (this === null) {
        return False
    }
    return True
}

fun Any?.identicalTo(obj: Any?): Boolean {
    if (this === obj) {
        return True
    }
    return False
}

fun factorial(n: UInt): UInt {
    var i = n
    var result = UInt.ONE
    while (i.isPositive() === True) {
        result *= i
        i--
    }
    return result
}

