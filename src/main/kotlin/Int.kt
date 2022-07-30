class Int private constructor(private val value: UInt, private val positive: Boolean): Number() {
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

        private fun valueOf(n: Number): Pair<UInt, Boolean> {
            return when (n) {
                is UInt -> Pair(n, True)
                is Int -> Pair(n.value, n.positive)
                is Float -> {
                    val intVal = n.toInt()
                    Pair(intVal.value, intVal.positive)
                }
                else -> Pair(UInt.ZERO, True)
            }
        }
    }

    constructor(args: Pair<UInt, Boolean>): this(args.first, args.second)
    constructor(n: Number): this(valueOf(n))

    override operator fun invoke(n: Number) : Int {
        return this * TEN + n
    }

    fun toUInt(): UInt {
        return if (!positive === True) UInt.ZERO else value
    }

    override operator fun unaryMinus(): Int {
        return Int(value, !positive)
    }

    override operator fun plus(n: Number): Int {
        val other = Int(n)
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

    override operator fun minus(n: Number): Int {
        return this + -Int(n)
    }

    override operator fun inc(): Int {
        return if (value.isPositive() === True) {
            if (positive === True) Int(value.inc()) else Int(value.dec(), False)
        } else {
            ONE
        }
    }

    override operator fun dec(): Int {
        return if (value.isPositive() === True) {
            if (positive === True) Int(value.dec()) else Int(value.inc(), False)
        } else {
            -ONE
        }
    }

    override operator fun times(n: Number): Int {
        val other = Int(n)
        return Int(value * other.value, positive.identicalTo(other.positive))
    }

    override operator fun div(n: Number): Int {
        return divRem(Int(n)).first
    }

    override operator fun rem(n: Number): Int {
        return divRem(Int(n)).second
    }

    fun divRem(divisor: Int): Pair<Int, Int> {
        val (q, r) = value.divRem(divisor.value)
        // The justification behind this choice of signs is that it satisfies n = q*d + r
        // However, it does not satisfy typical modular arithmetic rules, where the modulo is always positive
        val quotient = Int(q, positive.identicalTo(divisor.positive))
        val remainder = Int(r, positive)
        return Pair(quotient, remainder)
    }

    override infix fun equals(n: Number?): Boolean {
        if (n === null) {
            return False
        }

        val other = Int(n)
        if (!value.isPositive() and !other.value.isPositive() === True) {
            return True
        }

        return value equals other.value and positive.identicalTo(other.positive)
    }

    // This is needed to suppress a warning about the above method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): kotlin.Int { throw Exception() }

    override fun compareTo(n: Number): ComparisonResult {
        val other = Int(n)
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

    override infix fun shr(n: Number): Int {
        val amount = Int(n)
        return Int(if (amount.positive === True) value shr amount.value else value shl amount.value, positive)
    }

    override infix fun shl(n: Number): Int {
        val amount = Int(n)
        return Int(if (amount.positive === True) value shl amount.value else value shr amount.value, positive)
    }

    // biterator -> bits iterator
    fun biterator(): List.Iterator<Boolean> {
        return value.biterator()
    }

    fun reverseBiterator(): List.Iterator<Boolean> {
        return value.reverseBiterator()
    }

    override fun toDigits(base: UInt): List<UInt> {
        return value.toDigits(base)
    }

    override fun toString(): String {
        return if (value.isPositive() === True) {
            if (positive === True) value.toString() else "-$value"
        } else {
            "0"
        }
    }
}