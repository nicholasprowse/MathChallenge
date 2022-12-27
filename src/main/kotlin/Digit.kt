class Digit {
    companion object {
        val D0 = Digit()
        val D1 = Digit()
        val D2 = Digit()
        val D3 = Digit()
        val D4 = Digit()
        val D5 = Digit()
        val D6 = Digit()
        val D7 = Digit()
        val D8 = Digit()
        val D9 = Digit()
    }

    operator fun plus(n: Digit): List<Digit> {
        // Ensure that a <= b
        var a = this
        var b = n
        if (a greaterThan b === True) {
            a = n
            b = this
        }
        return when(a) {
            D0 -> List(b)
            D1 -> when(b) {
                D1 -> List(D2)
                D2 -> List(D3)
                D3 -> List(D4)
                D4 -> List(D5)
                D5 -> List(D6)
                D6 -> List(D7)
                D7 -> List(D8)
                D8 -> List(D9)
                else -> List(D0)(D1)
            }
            D2 -> when(b) {
                D2 -> List(D4)
                D3 -> List(D5)
                D4 -> List(D6)
                D5 -> List(D7)
                D6 -> List(D8)
                D7 -> List(D9)
                D8 -> List(D0)(D1)
                else -> List(D1)(D1)
            }
            D3 -> when(b) {
                D3 -> List(D6)
                D4 -> List(D7)
                D5 -> List(D8)
                D6 -> List(D9)
                D7 -> List(D0)(D1)
                D8 -> List(D1)(D1)
                else -> List(D2)(D1)
            }
            D4 -> when(b) {
                D4 -> List(D8)
                D5 -> List(D9)
                D6 -> List(D0)(D1)
                D7 -> List(D1)(D1)
                D8 -> List(D2)(D1)
                else -> List(D3)(D1)
            }
            D5 -> when(b) {
                D5 -> List(D0)(D1)
                D6 -> List(D1)(D1)
                D7 -> List(D2)(D1)
                D8 -> List(D3)(D1)
                else -> List(D4)(D1)
            }
            D6 -> when(b) {
                D6 -> List(D2)(D1)
                D7 -> List(D3)(D1)
                D8 -> List(D4)(D1)
                else -> List(D5)(D1)
            }
            D7 -> when(b) {
                D7 -> List(D4)(D1)
                D8 -> List(D5)(D1)
                else -> List(D6)(D1)
            }
            D8 -> when(b) {
                D8 -> List(D6)(D1)
                else -> List(D7)(D1)
            }
            else -> List(D8)(D1)
        }
    }

    infix fun lessThan(other: Digit): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) True else False
    }

    infix fun greaterThan(other: Digit): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) True else False
    }

    infix fun lessThanOrEqualTo(other: Digit): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) False else True
    }

    infix fun greaterThanOrEqualTo(other: Digit): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) False else True
    }

    fun compareTo(n: Digit): ComparisonResult {
        if (this === n) return ComparisonResult.EQUAL
        return when(this) {
            D0 -> ComparisonResult.LESS
            D1 -> if (n === D0) ComparisonResult.GREATER else ComparisonResult.LESS
            D2 -> when(n) {
                D0, D1 -> ComparisonResult.GREATER
                else -> ComparisonResult.LESS
            }
            D3 -> when(n) {
                D0, D1, D2 -> ComparisonResult.GREATER
                else -> ComparisonResult.LESS
            }
            D4 -> when(n) {
                D0, D1, D2, D3 -> ComparisonResult.GREATER
                else -> ComparisonResult.LESS
            }
            D5 -> when(n) {
                D6, D7, D8, D9 -> ComparisonResult.LESS
                else -> ComparisonResult.GREATER
            }
            D6 -> when(n) {
                D7, D8, D9 -> ComparisonResult.LESS
                else -> ComparisonResult.GREATER
            }
            D7 -> when(n) {
                D8, D9 -> ComparisonResult.LESS
                else -> ComparisonResult.GREATER
            }
            D8 -> if (n === D9) ComparisonResult.LESS else ComparisonResult.GREATER
            else -> ComparisonResult.GREATER
        }
    }

    override fun toString(): String {
        return when(this) {
            D0 -> "0"
            D1 -> "1"
            D2 -> "2"
            D3 -> "3"
            D4 -> "4"
            D5 -> "5"
            D6 -> "6"
            D7 -> "7"
            D8 -> "8"
            else -> "9"
        }
    }
}