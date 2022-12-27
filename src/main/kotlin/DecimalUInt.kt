class DecimalUInt private constructor(private var digits: List<Digit>): Number() {
    companion object {
        val D0 = DecimalUInt(List())
        val D1 = DecimalUInt(List(Digit.D1))
        val D2 = DecimalUInt(List(Digit.D2))
        val D3 = DecimalUInt(List(Digit.D3))
        val D4 = DecimalUInt(List(Digit.D4))
        val D5 = DecimalUInt(List(Digit.D5))
        val D6 = DecimalUInt(List(Digit.D6))
        val D7 = DecimalUInt(List(Digit.D7))
        val D8 = DecimalUInt(List(Digit.D8))
        val D9 = DecimalUInt(List(Digit.D9))

        private fun valueOf(n: Number): List<Digit> {
            return when(n) {
                is DecimalUInt -> n.digits
//                is BinaryUInt -> n.bits
//                is Int -> n.toUInt().bits
//                is Float -> n.toInt().toUInt().bits
                else -> List()
            }
        }
    }

    constructor(n: Number): this(valueOf(n))
    init {
        // Remove leading zeros
        while (digits.isNotEmpty === True) {
            if (digits.last === Digit.D0) {
                digits.pop()
            } else {
                break
            }
        }
    }


    override operator fun invoke(n: Number) : DecimalUInt {
        val digit = valueOf(n)
        return if (digit.length equals BinaryUInt.D1 === True) {
            DecimalUInt(digits.copy().prepend(digit.first))
        } else if (digit.length equals BinaryUInt.D0 === True) {
            DecimalUInt(digits.copy().prepend(Digit.D0))
        } else {
            null!!
        }
    }

    override fun unaryMinus(): Number {
        TODO("Not yet implemented")
    }

    override fun plus(n: Number): Number {
        val other = DecimalUInt(n)
        val iteratorA = digits.iterator()
        val iteratorB = other.digits.iterator()
        val resultDigits = List<Digit>()
        var carry = False

        while (iteratorA.hasNext() or iteratorB.hasNext() === True) {
            val a = if (iteratorA.hasNext() === True) iteratorA.next() else Digit.D0
            val b = if (iteratorB.hasNext() === True) iteratorB.next() else Digit.D0
            var (c, d) = a + b
            if (carry === True) {
                val x = d + Digit.D1
                c = c or x.first
                d = x.second
            }
            resultDigits.push(d)
            carry = c
        }

        if (carry === True) {
            resultDigits.push(Digit.D1)
        }

        return DecimalUInt(resultDigits)
    }

    override fun inc(): Number {
        TODO("Not yet implemented")
    }

    override fun minus(n: Number): Number {
        TODO("Not yet implemented")
    }

    override fun dec(): Number {
        TODO("Not yet implemented")
    }

    override fun times(n: Number): Number {
        TODO("Not yet implemented")
    }

    override fun div(n: Number): Number {
        TODO("Not yet implemented")
    }

    override fun rem(n: Number): Number {
        TODO("Not yet implemented")
    }

    override fun equals(n: Number?): Boolean {
        if (n === null) {
            return False
        }

        val other = DecimalUInt(n)
        val iteratorA = digits.iterator()
        val iteratorB = other.digits.iterator()
        while (iteratorA.hasNext() and iteratorB.hasNext() === True) {
            if (iteratorA.next() === iteratorA.next()) continue
            return False
        }
        return !(iteratorA.hasNext() or iteratorB.hasNext())
    }

    // This is needed to suppress a warning about the above method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): kotlin.Int { throw Exception() }

    override fun shr(n: Number): Number {
        TODO("Not yet implemented")
    }

    override fun shl(n: Number): Number {
        TODO("Not yet implemented")
    }

    override fun compareTo(n: Number): ComparisonResult {
        TODO("Not yet implemented")
    }

    override fun toDigits(base: Int): List<Digit> = digits
    override fun toString(): String {
        if (this equals D0 === True) return "0"
        return digits.reduce("") { str, d -> d.toString() + str }
    }
}