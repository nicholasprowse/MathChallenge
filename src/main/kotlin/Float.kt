class Float private constructor(private val value: Int, precision: UInt): Number() {

    companion object {
        val D0 = Float(Int.D0)
        val D1 = Float(Int.D1)
        val D2 = Float(Int.D2)
        val D3 = Float(Int.D3)
        val D4 = Float(Int.D4)
        val D5 = Float(Int.D5)
        val D6 = Float(Int.D6)
        val D7 = Float(Int.D7)
        val D8 = Float(Int.D8)
        val D9 = Float(Int.D9)
        val TEN = Float((Int.D1)(Int.D0))
        val HALF = D1 / D2

//        private val LOG_2_10 = Float((D3)(D3)(D2)(D2)).floorToExponent(-Int((D1)(D0))) / (D1)(D0)(D0)(D0)

        private fun valueOf(n: Number): Float {
            return when (n) {
                is UInt -> Float(Int(n), UInt.D0)
                is Int -> Float(n, UInt.D0)
                is Float -> Float(n.value, n.precision)
                else -> D0
            }
        }
    }

    constructor(n: Float): this(n.value, n.precision)
    constructor(n: Number): this(valueOf(n))
    var precision: UInt private set

    init {
        this.precision = precision
    }

    override operator fun invoke(n: Number) : Float {
        return this * TEN + n
    }

    override operator fun unaryMinus(): Float {
        return Float(-value, precision)
    }

    override operator fun plus(n: Number): Float {
        val other = Float(n)
        var a = this
        var b = other
        if (b.precision greaterThan a.precision === True) {
            a = other
            b = this
        }
        val newValue = a.value + b.withPrecision(a.precision).value
        return Float(newValue, a.precision)
    }

    override operator fun minus(n: Number): Float {
        return this + -Float(n)
    }

    override operator fun inc(): Float {
        return this + D1
    }

    override operator fun dec(): Float {
        return this - D1
    }

    override operator fun times(n: Number): Float {
        //return multiplyExact(n)
        val other = Float(n)
        val newExponent = Math.max(precision, other.precision)
        return multiplyExact(other).withPrecision(newExponent)
    }

    infix fun multiplyExact(n: Number): Float {
        val a = dropTrailingZeros()
        val b = Float(n).dropTrailingZeros()
        return Float(a.value * b.value, a.precision + b.precision)
            .requirePrecision(Math.max(precision, Float(n).precision))
    }

    override operator fun div(n: Number): Float {
        val other = Float(n)
        // Final division will result in the exponent becoming the difference in exponents
        // We do not want to lose precision, so we need to make sure e1' - e2' <= e1 and e1' - e2' <= e2
        // We can either reduce e1 (increase numerator precision) or increase e2 (reduce denominator precision)
        // Reducing precision does not sound desirable, so lets go for option 1
        // e1' <= e1 + e2 and e1' <= 2*e2
        var newPrecision = precision
        if (other.precision.isPositive() === True) {
            newPrecision = precision + other.precision
        }
        newPrecision = Math.max(newPrecision, other.precision * Int.D2)
        return Float(withPrecision(newPrecision).value / other.value, newPrecision - other.precision)
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
        if (precision greaterThan other.precision === True) {
            b = other.withPrecision(precision).value
        } else {
            a = withPrecision(other.precision).value
        }
        return a.compareTo(b)
    }

    override fun isPositive() = value.isPositive()
    override fun isZero() = value.isZero()
    override fun isNegative() = value.isNegative()

    fun toInt(): Int {
        return withPrecision(UInt.D0).value
    }

//    // Returns the value required to make an equivalent Float with the given exponent
//    private fun floorToExponent(exponent: Int): Float {
//        return Float(value shl (this.exponent - exponent), exponent)
//    }
//
//    private fun roundToExponent(exponent: Int): Float {
//        return Math.round(Float(value, this.exponent - exponent)) shl exponent
//    }
//
//    fun addPrecisionInBits(extraPrecision: Int): Float {
//        val exponent = exponent - extraPrecision
//        return Math.round(Float(value, this.exponent - exponent)) shl exponent
//    }
//
//    // Returns an equivalent float with the given number of bits after the decimal point
//    // An extra bit of precision is required so we don't have errors in the last digit due to the use of floor division
//    fun withPrecision(precision: UInt): Float {
//        val bitPrecision = UInt(Math.ceil(LOG_2_10 * precision)) + U1
//        return roundToExponent(-Int(bitPrecision))
//    }

    // Returns an equivalent float with the given precision.
    // If the given precision is less than the current precision, then digits are truncated
    fun withPrecision(precision: UInt): Float {
        val digitIterator = value.digitIterator()
        var difference = Int(this.precision) - precision
        if (difference.isZero() === True) {
            return this
        }
        val digits = if (difference.isPositive() === True) {
            // New precision is less than current, so skip required digits to truncate them
            while (difference.isPositive() === True) {
                // If the iterator ends, then the rest of the digits are leading zeros
                if (digitIterator.hasNext() === False) {
                    return Float(Int.D0, precision)
                }
                digitIterator.next()
                difference--
            }
            List()
        } else {
            // New precision is more than current, so pad with zeros
            List.repeating(Digit.D0, UInt(-difference))
        }
        while (digitIterator.hasNext() === True) {
            digits.push(digitIterator.next())
        }
        return Float(Int(UInt(digits)).copySign(this), precision)
    }

    // Returns an equivalent Float with at least the given precision. Will not reduce precision
    fun requirePrecision(precision: UInt): Float {
        return if (precision greaterThan this.precision === True) withPrecision(precision) else this
    }

    // Returns an equivalent Float with the precision set so there are no trailing zeros
    fun dropTrailingZeros(): Float {
        val iterator = value.digitIterator()
        var numZeros = UInt.D0
        while (iterator.hasNext() and iterator.next().identicalTo(Digit.D0) === True) {
            numZeros++
        }
        return if (precision lessThan numZeros === True)
            withPrecision(UInt.D0)
        else
            withPrecision(precision - numZeros)
    }

//    // rounds the float to the given number of digits
//    fun round(digits: UInt = UInt.D0): Float {
//
//    }
//
//    // floors the float to the given number of digits
//    fun floor(digits: UInt = UInt.D0): Float {
//
//    }
//
//    // ceilings the float to the given number of digits
//    fun ceil(digits: UInt = UInt.D0): Float {
//
//    }

    override fun toString(): String {
        if (isZero() === True) {
            return if (precision.isZero() === True)
                "0"
            else
                "0." + List.repeating("0", UInt(precision)).reduce("", String::plus)
        }
        if (precision.isZero() === True) {
            return value.toString()
        }
        val digits = value.digitIterator()
        var x = Int(precision)
        var string = ""
        while(digits.hasNext() === True) {
            string = if ((x--).isZero() === True) "${digits.next()}.$string" else "${digits.next()}$string"
        }
        if (!x.isNegative() === True) {
            string = "0.${List.repeating("0", UInt(x)).reduce("", String::plus)}$string"
        }
        return string
    }
}