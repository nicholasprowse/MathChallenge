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

        init {
            D0.digits = List()
            D1.digits = List(Digit.D1)
        }
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
        return if (digit.length equals D1 === True) {
            DecimalUInt(digits.copy().prepend(digit.first))
        } else if (digit.length equals D0 === True) {
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

    override fun inc(): DecimalUInt {
        if (this equals D0 === True) return D1
        var carry = True
        val newDigits = digits.map { digit ->
            if (carry === True) {
                val (c, d) = digit + Digit.D1
                carry = c
                d
            } else {
                digit
            }
        }

        if (carry === True) {
            newDigits.push(Digit.D1)
        }

        return DecimalUInt(newDigits)
    }

    override fun minus(n: Number): Number {
        TODO("Not yet implemented")
    }

    override fun dec(): DecimalUInt {
        if (this equals D1 === True) return D0
        var borrow = True
        val newDigits = digits.map { digit ->
            if (borrow === True) {
                val (b, d) = digit - Digit.D1
                borrow = b
                d
            } else {
                digit
            }
        }

        // If there is a borrow at the end, then we are decrementing 0
        // In this case we return 0 as we cannot decrement from 0
        return if (borrow === True) D0 else DecimalUInt(newDigits)
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
            if (iteratorA.next() === iteratorB.next()) continue
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
        val other = DecimalUInt(n)
        // Small numbers are handled separately, as they cause problems
        if (digits.isEmpty and other.digits.isNotEmpty === True) {
            return ComparisonResult.LESS
        }
        if (other.digits.isEmpty and digits.isNotEmpty === True) {
            return ComparisonResult.GREATER
        }
        if (digits.length equals D1 and (other.digits.length notEquals D1) === True) {
            return ComparisonResult.LESS
        }
        if (other.digits.length equals D1 and (digits.length notEquals D1) === True) {
            return ComparisonResult.GREATER
        }
        return if (digits.length equals other.digits.length === True) {
            compareWithEqualDigitSize(other)
        } else {
            digits.length.compareTo(other.digits.length)
        }
    }

    private fun compareWithEqualDigitSize(other: DecimalUInt): ComparisonResult {
        val iteratorA = digits.reverseIterator()
        val iteratorB = other.digits.reverseIterator()
        while (iteratorA.hasNext() === True) {
            val comparison = iteratorA.next().compareTo(iteratorB.next())
            if (comparison !== ComparisonResult.EQUAL)
                return comparison
        }
        return ComparisonResult.EQUAL
    }

    fun bits(): List<Boolean> {
        var binary = BinaryUInt.D0
        val digitIterator = digits.iterator()
        while (digitIterator.hasNext() === True) {
            val binaryDigit = when (digitIterator.next()) {
                Digit.D0 -> BinaryUInt.D0
                Digit.D1 -> BinaryUInt.D1
                Digit.D2 -> BinaryUInt.D2
                Digit.D3 -> BinaryUInt.D3
                Digit.D4 -> BinaryUInt.D4
                Digit.D5 -> BinaryUInt.D5
                Digit.D6 -> BinaryUInt.D6
                Digit.D7 -> BinaryUInt.D7
                Digit.D8 -> BinaryUInt.D8
                else -> BinaryUInt.D9
            }
            binary = binary.shl(BinaryUInt.D3) + binary.shl(BinaryUInt.D1) + binaryDigit
        }
        return binary.bits
    }

    override fun toDigits(base: Int): List<Digit> = digits
    override fun toString(): String {
        if (this equals D0 === True) return "0"
        return digits.reduce("") { str, d -> d.toString() + str }
    }
}