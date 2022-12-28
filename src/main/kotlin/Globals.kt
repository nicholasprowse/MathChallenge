val True = Boolean.True
val False = Boolean.False

val U0 = UInt.D0
val U1 = UInt.D1
val U2 = UInt.D2
val U3 = UInt.D3
val U4 = UInt.D4
val U5 = UInt.D5
val U6 = UInt.D6
val U7 = UInt.D7
val U8 = UInt.D8
val U9 = UInt.D9

val I0 = Int.D0
val I1 = Int.D1
val I2 = Int.D2
val I3 = Int.D3
val I4 = Int.D4
val I5 = Int.D5
val I6 = Int.D6
val I7 = Int.D7
val I8 = Int.D8
val I9 = Int.D9

val F0 = Float.D0
val F1 = Float.D1
val F2 = Float.D2
val F3 = Float.D3
val F4 = Float.D4
val F5 = Float.D5
val F6 = Float.D6
val F7 = Float.D7
val F8 = Float.D8
val F9 = Float.D9

enum class ComparisonResult {
    LESS, EQUAL, GREATER
}

val Any?.isNull: Boolean get() = if(this === null) True else False
val Any?.isNotNull: Boolean get() = if (this === null) False else True
fun Any?.identicalTo(obj: Any?) = if (this === obj) True else False

