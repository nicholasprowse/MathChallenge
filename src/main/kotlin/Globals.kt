val True = Boolean.True
val False = Boolean.False

val U0 = UInt.ZERO
val U1 = UInt.ONE
val U2 = UInt.TWO
val U3 = UInt.THREE
val U4 = UInt.FOUR
val U5 = UInt.FIVE
val U6 = UInt.SIX
val U7 = UInt.SEVEN
val U8 = UInt.EIGHT
val U9 = UInt.NINE

val I0 = Int.ZERO
val I1 = Int.ONE
val I2 = Int.TWO
val I3 = Int.THREE
val I4 = Int.FOUR
val I5 = Int.FIVE
val I6 = Int.SIX
val I7 = Int.SEVEN
val I8 = Int.EIGHT
val I9 = Int.NINE

val F0 = Float.ZERO
val F1 = Float.ONE
val F2 = Float.TWO
val F3 = Float.THREE
val F4 = Float.FOUR
val F5 = Float.FIVE
val F6 = Float.SIX
val F7 = Float.SEVEN
val F8 = Float.EIGHT
val F9 = Float.NINE

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

