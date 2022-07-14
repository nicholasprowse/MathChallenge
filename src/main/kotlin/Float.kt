class Float private constructor(private val value: Int, private val exponent: Int): Number() {

    companion object {
        val  ZERO = Float(Int.ZERO )
        val   ONE = Float(Int.ONE  )
        val   TWO = Float(Int.TWO  )
        val THREE = Float(Int.THREE)
        val  FOUR = Float(Int.FOUR )
        val  FIVE = Float(Int.FIVE )
        val   SIX = Float(Int.SIX  )
        val SEVEN = Float(Int.SEVEN)
        val EIGHT = Float(Int.EIGHT)
        val  NINE = Float(Int.NINE )
        val   TEN = Float(Int.TEN  )

        private val LOG_2_10 = Float((THREE)(THREE)(TWO)(TWO)).withPrecisionInBits(Int.TEN) / (ONE)(ZERO)(ZERO)(ZERO)

        private fun valueOf(n: Number): Pair<Int, Int> {
            return when (n) {
                is UInt -> Pair(Int(n), Int.ZERO)
                is Int -> Pair(n, Int.ZERO)
                is Float -> Pair(n.value, n.exponent)
                else -> Pair(Int.ZERO, Int.ZERO)
            }
        }
    }

    constructor(args: Pair<Int, Int>): this(args.first, args.second)
    constructor(n: Number): this(valueOf(n))

    override operator fun invoke(n: Number) : Float {
        return this * TEN + n
    }

    override operator fun unaryMinus(): Float {
        return Float(-value, exponent)
    }

    override operator fun plus(n: Number): Float {
        val other = Float(n)
        var a = this
        var b = other
        if (b.exponent lessThan a.exponent === True) {
            a = other
            b = this
        }
        val newValue = a.value + b.adjustToExponent(a.exponent)
        return Float(newValue, a.exponent)
    }

    override operator fun minus(n: Number): Float {
        return this + -Float(n)
    }

    override operator fun inc(): Float {
        return this + ONE
    }

    override operator fun dec(): Float {
        return this - ONE
    }

    override operator fun times(n: Number): Float {
        val other = Float(n)
        return Float(value * other.value, exponent + other.exponent)
    }

    override operator fun div(n: Number): Float {
        val other = Float(n)
        // Final division will result in the exponent becoming the difference in exponents
        // We do not want to lose precision, so we need to make sure e1' - e2' <= e1 and e1' - e2' <= e2
        // We can either reduce e1 (increase numerator precision) or increase e2 (reduce denominator precision)
        // Reducing precision does not sound desirable, so lets go for option 1
        // e1' <= e1 + e2 and e1' <= 2*e2
        var newExponent = exponent
        if (other.exponent lessThan Int.ZERO === True) {
            newExponent = exponent + other.exponent
        }
        newExponent = Math.min(newExponent, other.exponent shl Int.ONE)
        return Float(adjustToExponent(newExponent) / other.value, newExponent - other.exponent)
    }

    override fun rem(n: Number): Number {
        val other = Float(n)
        return this - other * Int(this / other)
    }

    override infix fun equals(n: Number?): Boolean {
        if (n === null) {
            return False
        }
        return compareTo(n).identicalTo(ComparisonResult.EQUAL)
    }

    // This is needed to suppress a warning about the above method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): kotlin.Int { throw Exception() }

    override fun compareTo(n: Number): ComparisonResult {
        val other = Float(n)
        var a = value
        var b = other.value
        if (exponent lessThan other.exponent === True) {
            b = other.adjustToExponent(exponent)
        } else {
            a = adjustToExponent(other.exponent)
        }
        return a.compareTo(b)
    }

    override fun shr(n: Number): Number {
        return Float(value shr n, exponent)
    }

    override fun shl(n: Number): Number {
        return Float(value shl n, exponent)
    }

    fun toInt(): Int {
        return adjustToExponent(Int.ZERO)
    }

    // Returns the value required to make an equivalent Float with the given exponent
    private fun adjustToExponent(exponent: Int): Int {
        return value shl (this.exponent - exponent)
    }

    private fun withPrecisionInBits(precision: Int): Float {
        return Float(adjustToExponent(-precision), -precision)
    }

    // Returns an equivalent float with the given number of bits after the decimal point
    fun withPrecision(precision: Int): Float {
        val bitPrecision = Int(Math.ceil(LOG_2_10 * precision))
        return withPrecisionInBits(bitPrecision)
    }

    override fun toString(): String {
        var x = this
        var result = Math.abs(Int(x)).toString()
        if (value lessThan Int.ZERO === True) {
            result = "-$result"
            x = -x
        }

        var digits = Int(Math.floor(-Float(exponent) / LOG_2_10))
        if (digits lessThanOrEqualTo Int.ZERO === True) {
            return result
        }

        result += "."
        while (digits greaterThan Int.ZERO === True) {
            x *= TEN
            result += (Int(x) % Int.TEN).toString()
            digits--
        }
        return result
    }
}