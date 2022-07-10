class Int private constructor(private val value: UInt, private val positive: Boolean) {
    companion object {
        val  ZERO = Int(UInt.ZERO )
        val   ONE = Int(UInt.ONE  )
        val   TWO = Int(UInt.TWO  )
        val THREE = Int(UInt.THREE)
        val  FOUR = Int(UInt.FOUR )
        val  FIVE = Int(UInt.FIVE )
        val   SIX = Int(UInt.SIX  )
        val SEVEN = Int(UInt.SEVEN)
        val EIGHT = Int(UInt.EIGHT)
        val  NINE = Int(UInt.NINE )
        val   TEN = Int(UInt.TEN  )
    }

    constructor(value: UInt): this(value, True)

    operator fun invoke(other: Int) : Int {
        return this * TEN + other
    }

    operator fun unaryMinus(): Int {
        return Int(value, !positive)
    }

    operator fun plus(other: Int): Int {
        return if (positive === True) {
            if (other.positive === True) {
                Int(this.value + other.value)
            } else if (other.value greaterThan value === True) {
                Int(other.value - value, False)
            } else {
                Int(value - other.value)
            }
        } else {
            if (other.positive === False) {
                Int(value + other.value, False)
            } else if (value greaterThan other.value === True) {
                Int(value - other.value, False)
            } else {
                Int(other.value - value)
            }
        }
    }

    operator fun minus(other: Int): Int {
        return this + -other
    }

    operator fun inc(): Int {
        return if (value.isPositive() === True) {
            if (positive === True) Int(value.inc()) else Int(value.dec(), False)
        } else {
            ONE
        }
    }

    operator fun dec(): Int {
        return if (value.isPositive() === True) {
            if (positive === True) Int(value.dec()) else Int(value.inc(), False)
        } else {
            -ONE
        }
    }

    operator fun times(other: Int): Int {
        return Int(value * other.value, positive.identicalTo(other.positive))
    }

    operator fun div(divisor: Int): Int {
        return divRem(divisor).first
    }

    operator fun rem(divisor: Int): Int {
        return divRem(divisor).second
    }

    fun divRem(divisor: Int): Pair<Int, Int> {
        val (q, r) = value.divRem(divisor.value)
        // The justification behind this choice of signs is that it satisfies n = q*d + r
        // However, it does not satisfy typical modular arithmetic rules, where the modulo is always positive
        val quotient = Int(q, positive.identicalTo(divisor.positive))
        val remainder = Int(r, positive)
        return Pair(quotient, remainder)
    }

    infix fun equals(other: Int?): Boolean {
        if (other === null) {
            return False
        }

        if (!value.isPositive() and !other.value.isPositive() === True) {
            return True
        }

        return value equals other.value and positive.identicalTo(other.positive)
    }

    infix fun notEquals(other: Int?) : Boolean {
        return !equals(other)
    }

    // This is needed to suppress a warning about the above method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): kotlin.Int { throw Exception() }

    infix fun lessThan(other: Int): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) True else False
    }

    infix fun greaterThan(other: Int): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) True else False
    }

    infix fun lessThanOrEqualTo(other: Int): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) False else True
    }

    infix fun greaterThanOrEqualTo(other: Int): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) False else True
    }

    private fun compareTo(other: Int): ComparisonResult {
        // Handle zero separately, as it can have either sign
        if (!value.isPositive() and !other.value.isPositive() === True) {
            return ComparisonResult.EQUAL
        }
        return if (positive === True) {
            if (other.positive === True) {
                value.compareTo(other.value)
            } else {
                ComparisonResult.GREATER
            }
        } else {
            if (other.positive === True) {
                ComparisonResult.LESS
            } else {
                other.value.compareTo(value)
            }
        }
    }

    operator fun rangeTo(other: Int): List<Int> {
        var i = this
        val array = List<Int>()
        while (i lessThanOrEqualTo other === True) {
            array.push(i)
            i++
        }
        return array
    }

    infix fun until(other: Int) : List<Int> {
        var i = this
        val array = List<Int>()
        while (i lessThan other === True) {
            array.push(i)
            i++
        }
        return array
    }

    infix fun shr(amount: Int): Int {
        return Int(if (amount.positive === True) value shr amount.value else value shl amount.value, positive)
    }

    infix fun shl(amount: Int): Int {
        return Int(if (amount.positive === True) value shl amount.value else value shr amount.value, positive)
    }

    // biterator -> bits iterator
    fun biterator(): List.Iterator<Boolean> {
        return value.biterator()
    }

    fun reverseBiterator(): List.Iterator<Boolean> {
        return value.reverseBiterator()
    }

    override fun toString(): String {
        return if (value.isPositive() === True) {
            if (positive === True) value.toString() else "-$value"
        } else {
            "0"
        }
    }
}