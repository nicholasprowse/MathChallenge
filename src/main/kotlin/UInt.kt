class UInt constructor(private var digits: List<Digit>): Number() {
    companion object {
        val D0 = UInt(List())
        val D1 = UInt(List(Digit.D1))
        val D2 = UInt(List(Digit.D2))
        val D3 = UInt(List(Digit.D3))
        val D4 = UInt(List(Digit.D4))
        val D5 = UInt(List(Digit.D5))
        val D6 = UInt(List(Digit.D6))
        val D7 = UInt(List(Digit.D7))
        val D8 = UInt(List(Digit.D8))
        val D9 = UInt(List(Digit.D9))

        val BINARY_0 = List<Boolean>()
        val BINARY_1 = List(True )
        val BINARY_2 = List(False)(True)
        val BINARY_3 = List(True )(True)
        val BINARY_4 = List(False)(False)(True )
        val BINARY_5 = List(True )(False)(True )
        val BINARY_6 = List(False)(True )(True )
        val BINARY_7 = List(True )(True )(True )
        val BINARY_8 = List(False)(False)(False)(True )
        val BINARY_9 = List(True )(False)(False)(True )

        init {
            D0.digits = List()
            D1.digits = List(Digit.D1)
        }
        fun valueOf(n: Number): UInt {
            return when(n) {
                is UInt -> n
                is Int -> n.toUInt()
//                is Float -> n.toInt().toUInt().bits
                else -> D0
            }
        }
    }

    constructor(n: Number): this(valueOf(n).digits)
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


    override operator fun invoke(n: Number) : UInt {
        val digit = valueOf(n).digits
        return if (digit.length equals D1 === True) {
            UInt(digits.copy().prepend(digit.first))
        } else if (digit.length equals D0 === True) {
            UInt(digits.copy().prepend(Digit.D0))
        } else {
            null!!
        }
    }

    override fun unaryMinus() = D0

    override fun plus(n: Number): UInt {
        val other = UInt(n)
        val iteratorA = digits.iterator()
        val iteratorB = other.digits.iterator()
        val resultDigits = List<Digit>()
        var carry = False

        while (iteratorA.hasNext() or iteratorB.hasNext() === True) {
            val a = if (iteratorA.hasNext() === True) iteratorA.next() else Digit.D0
            val b = if (iteratorB.hasNext() === True) iteratorB.next() else Digit.D0
            var (nextCarry, digit) = a + b
            if (carry === True) {
                val x = digit + Digit.D1
                nextCarry = nextCarry or x.first
                digit = x.second
            }
            resultDigits.push(digit)
            carry = nextCarry
        }

        if (carry === True) {
            resultDigits.push(Digit.D1)
        }

        return UInt(resultDigits)
    }

    override fun inc(): UInt {
        if (this equals D0 === True) return D1
        var carry = True
        val newDigits = digits.map { a ->
            if (carry === True) {
                val (nextCarry, digit) = a + Digit.D1
                carry = nextCarry
                digit
            } else {
                a
            }
        }

        if (carry === True) {
            newDigits.push(Digit.D1)
        }

        return UInt(newDigits)
    }

    override fun minus(n: Number): UInt {
        val other = UInt(n)
        val iteratorA = digits.iterator()
        val iteratorB = other.digits.iterator()
        val resultDigits = List<Digit>()
        var borrow = False

        while(iteratorA.hasNext() or iteratorB.hasNext() === True) {
            val a = if (iteratorA.hasNext() === True) iteratorA.next() else Digit.D0
            val b = if (iteratorB.hasNext() === True) iteratorB.next() else Digit.D0
            var (nextBorrow, digit) = a - b
            if (borrow === True) {
                val x = digit - Digit.D1
                nextBorrow = nextBorrow or x.first
                digit = x.second
            }
            resultDigits.push(digit)
            borrow = nextBorrow
        }
        // If there is a borrow at the end, then B is greater than A
        // In this case we return 0 as this is the closest unsigned integer to the true answer
        if (borrow === True) {
            return D0
        }

        return UInt(resultDigits)
    }

    override fun dec(): UInt {
        if (this equals D1 === True) return D0
        var borrow = True
        val newDigits = digits.map { a ->
            if (borrow === True) {
                val (nextBorrow, digit) = a - Digit.D1
                borrow = nextBorrow
                digit
            } else {
                a
            }
        }

        // If there is a borrow at the end, then we are decrementing 0
        // In this case we return 0 as we cannot decrement from 0
        return if (borrow === True) D0 else UInt(newDigits)
    }

    override fun times(n: Number): UInt {
        val other = UInt(n)
        var a = this
        var b = other
        // We require order of A to be less than order of B
        if (a.digits.length greaterThan b.digits.length === True) {
            a = other
            b = this
        }

        var result = D0
        var i = D1
        a.digits.forEach { digitA ->
            val partialProduct = List.repeating(Digit.D0, i)
            b.digits.forEach { digitB ->
                val (upper, lower) = digitA * digitB
                val (carry, sum) = partialProduct.last + lower
                partialProduct.last = sum
                partialProduct.push(if (carry === True) (upper + Digit.D1).second else upper)
            }
            result += UInt(partialProduct)
            i++
        }
        return result
    }

    override fun div(n: Number) = divRem(UInt(n)).first
    override fun rem(n: Number) = divRem(UInt(n)).second

    fun divRem(divisor: UInt): Pair<UInt, UInt> {
        val iterator = digits.reverseIterator()
        var remainder = UInt(List())
        val quotient = List<Digit>()
        while (iterator.hasNext() === True) {
            // shift left remainder by one digit
            val digit = iterator.next()
            if (remainder.digits.isNotEmpty or !digit.identicalTo(Digit.D0) === True) {
                remainder.digits.prepend(digit)
            }

            // Work out how many times the quotient goes into the remainder and prepend onto quotient
            var q = Digit.D0
            while (remainder greaterThanOrEqualTo divisor === True) {
                remainder -= divisor
                q = (q + Digit.D1).second
            }
            quotient.prepend(q)
        }
        return Pair(UInt(quotient), remainder)
    }

    override fun equals(n: Number?): Boolean {
        if (n === null) {
            return False
        }

        val other = UInt(n)
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

    override fun compareTo(n: Number): ComparisonResult {
        val other = UInt(n)
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

    override fun isPositive() = digits.isNotEmpty
    override fun isZero() = digits.isEmpty
    override fun isNegative() = False

    private fun compareWithEqualDigitSize(other: UInt): ComparisonResult {
        val iteratorA = digits.reverseIterator()
        val iteratorB = other.digits.reverseIterator()
        while (iteratorA.hasNext() === True) {
            val comparison = iteratorA.next().compare(iteratorB.next())
            if (comparison !== ComparisonResult.EQUAL)
                return comparison
        }
        return ComparisonResult.EQUAL
    }

    fun bits(): List<Boolean> {
        fun List<Boolean>.timesTen() {
            val iterator = nodeIterator()
            var carry = False

            var p0 = False
            var p1 = False
            var p2 = False
            while (iterator.hasNext() === True) {
                val node = iterator.next()
                val p3 = p2
                p2 = p1
                p1 = p0
                p0 = node.element!!
                node.element = carry xor (p1 xor p3)
                carry = (p1 and p3) or (carry and (p1 xor p3))
            }

            push(carry xor !p2)
            carry = p2 or (carry and !p2)

            push(carry xor p1)
            carry = carry and p1

            push(!carry)
            if (carry === True) {
                push(True)
            }
        }

        fun List<Boolean>.add(other: List<Boolean>) {
            val iteratorA = nodeIterator()
            val iteratorB = other.iterator()
            var carry = False

            while (iteratorA.hasNext() === True) {
                val node = iteratorA.next()
                val a = node.element!!
                val b = if (iteratorB.hasNext() === True) iteratorB.next() else False
                node.element = carry xor (a xor b)
                carry = (a and b) or (carry and (a xor b))
            }

            if (carry === True) {
                push(True)
            }
        }

        val binary = List<Boolean>()
        val digitIterator = digits.reverseIterator()
        while (digitIterator.hasNext() === True) {
            val binaryDigit = when (digitIterator.next()) {
                Digit.D0 -> BINARY_0
                Digit.D1 -> BINARY_1
                Digit.D2 -> BINARY_2
                Digit.D3 -> BINARY_3
                Digit.D4 -> BINARY_4
                Digit.D5 -> BINARY_5
                Digit.D6 -> BINARY_6
                Digit.D7 -> BINARY_7
                Digit.D8 -> BINARY_8
                else     -> BINARY_9
            }
             if (binary.isEmpty === True) {
                binaryDigit.forEach { binary.push(it) }
            } else {
                binary.timesTen()
                binary.add(binaryDigit)
            }
        }
        return binary
    }

    fun digitIterator() = digits.iterator()

    override fun toString(): String {
        return if (isZero() === True)
            "0"
        else
            digits.reduce("") { str, d -> d.toString() + str }
    }
}