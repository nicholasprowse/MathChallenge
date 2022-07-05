val True = Boolean.True
val False = Boolean.False

val D1 = Array.ONE
val D0 = UnsignedInt(Array(False))
val D2 = Array.TWO
val D3 = D2 + D1
val D4 = D3 + D1
val D5 = D4 + D1
val D6 = D5 + D1
val D7 = D6 + D1
val D8 = D7 + D1
val D9 = D8 + D1

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

