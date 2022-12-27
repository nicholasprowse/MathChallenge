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

    /**
     * Returns a pair containing a boolean indicating carry, and a digit indicating the sum
     */
    operator fun plus(n: Digit): Pair<Boolean, Digit> {
        // Ensure that a <= b
        var a = this
        var b = n
        if (a greaterThan b === True) {
            a = n
            b = this
        }
        return when(a) {
            D0 -> Pair(False, b)
            D1 -> when(b) {
                D1 -> Pair(False, D2)
                D2 -> Pair(False, D3)
                D3 -> Pair(False, D4)
                D4 -> Pair(False, D5)
                D5 -> Pair(False, D6)
                D6 -> Pair(False, D7)
                D7 -> Pair(False, D8)
                D8 -> Pair(False, D9)
                else -> Pair(True, D0)
            }
            D2 -> when(b) {
                D2 -> Pair(False, D4)
                D3 -> Pair(False, D5)
                D4 -> Pair(False, D6)
                D5 -> Pair(False, D7)
                D6 -> Pair(False, D8)
                D7 -> Pair(False, D9)
                D8 -> Pair(True, D0)
                else -> Pair(True, D1)
            }
            D3 -> when(b) {
                D3 -> Pair(False, D6)
                D4 -> Pair(False, D7)
                D5 -> Pair(False, D8)
                D6 -> Pair(False, D9)
                D7 -> Pair(True, D0)
                D8 -> Pair(True, D1)
                else -> Pair(True, D2)
            }
            D4 -> when(b) {
                D4 -> Pair(False, D8)
                D5 -> Pair(False, D9)
                D6 -> Pair(True, D0)
                D7 -> Pair(True, D1)
                D8 -> Pair(True, D2)
                else -> Pair(True, D3)
            }
            D5 -> when(b) {
                D5 -> Pair(True, D0)
                D6 -> Pair(True, D1)
                D7 -> Pair(True, D2)
                D8 -> Pair(True, D3)
                else -> Pair(True, D4)
            }
            D6 -> when(b) {
                D6 -> Pair(True, D2)
                D7 -> Pair(True, D3)
                D8 -> Pair(True, D4)
                else -> Pair(True, D5)
            }
            D7 -> when(b) {
                D7 -> Pair(True, D4)
                D8 -> Pair(True, D5)
                else -> Pair(True, D6)
            }
            D8 -> when(b) {
                D8 -> Pair(True, D6)
                else -> Pair(True, D7)
            }
            else -> Pair(True, D8)
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