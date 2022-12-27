class Int private constructor(private val value: BinaryUInt, private val positive: Boolean): Number() {
    companion object {
        val D0 = Int(BinaryUInt.D0)
        val D1 = Int(BinaryUInt.D1)
        val D2 = Int(BinaryUInt.D2)
        val D3 = Int(BinaryUInt.D3)
        val D4 = Int(BinaryUInt.D4)
        val D5 = Int(BinaryUInt.D5)
        val D6 = Int(BinaryUInt.D6)
        val D7 = Int(BinaryUInt.D7)
        val D8 = Int(BinaryUInt.D8)
        val D9 = Int(BinaryUInt.D9)
        val TEN = Int(BinaryUInt.TEN)

        private fun valueOf(n: Number): Pair<BinaryUInt, Boolean> {
            return when (n) {
                is BinaryUInt -> Pair(n, True)
                is Int -> Pair(n.value, n.positive)
//                is Float -> {
//                    val intVal = n.toInt()
//                    Pair(intVal.value, intVal.positive)
//                }
                else -> Pair(BinaryUInt.D0, True)
            }
        }
    }

    constructor(args: Pair<BinaryUInt, Boolean>): this(args.first, args.second)
    constructor(n: Number): this(valueOf(n))

    override operator fun invoke(n: Number) : Int {
        return this * TEN + n
    }

    fun toUInt(): BinaryUInt {
        return if (!positive === True) BinaryUInt.D0 else value
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
            D1
        }
    }

    override operator fun dec(): Int {
        return if (value.isPositive() === True) {
            if (positive === True) Int(value.dec()) else Int(value.inc(), False)
        } else {
            -D1
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

    override fun toDigits(base: Int): List<Digit> {
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