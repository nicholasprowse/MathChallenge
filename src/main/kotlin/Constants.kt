val True = Boolean.True
val False = Boolean.False

val D0 = UnsignedInt.ZERO
val D1 = UnsignedInt.ONE
val D2 = UnsignedInt.TWO
val D3 = UnsignedInt.THREE
val D4 = UnsignedInt.FOUR
val D5 = UnsignedInt.FIVE
val D6 = UnsignedInt.SIX
val D7 = UnsignedInt.SEVEN
val D8 = UnsignedInt.EIGHT
val D9 = UnsignedInt.NINE

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

fun factorial(n: UnsignedInt): UnsignedInt {
    var i = n
    var result = D1
    while (i greaterThan D0 === True) {
        result *= i
        i--
    }
    return result
}

