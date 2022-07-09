val True = Boolean.True
val False = Boolean.False

val D0 = UInt.ZERO
val D1 = UInt.ONE
val D2 = UInt.TWO
val D3 = UInt.THREE
val D4 = UInt.FOUR
val D5 = UInt.FIVE
val D6 = UInt.SIX
val D7 = UInt.SEVEN
val D8 = UInt.EIGHT
val D9 = UInt.NINE

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
    var result = D1
    while (i greaterThan D0 === True) {
        result *= i
        i--
    }
    return result
}

