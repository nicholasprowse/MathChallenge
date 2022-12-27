class BinaryUInt private constructor(private var bits: List<Boolean>): Number() {

    companion object {
        val D0: BinaryUInt = BinaryUInt(List())
        val D1: BinaryUInt = BinaryUInt(List())
        val D2: BinaryUInt = BinaryUInt(List())
        val D3: BinaryUInt
        val D4: BinaryUInt
        val D5: BinaryUInt
        val D6: BinaryUInt
        val D7: BinaryUInt
        val D8: BinaryUInt
        val D9: BinaryUInt
        val TEN: BinaryUInt

        init {
            D0.bits = List()
            D1.bits = List(True)
            D2.bits = List(False)(True)

            D3 = D2 + D1
            D4 = D3 + D1
            D5 = D4 + D1
            D6 = D5 + D1
            D7 = D6 + D1
            D8 = D7 + D1
            D9 = D8 + D1
            TEN = D9 + D1
        }

        private fun valueOf(n: Number): List<Boolean> {
            return when(n) {
                is BinaryUInt -> n.bits
                is Int -> n.toUInt().bits
//                is Float -> n.toInt().toUInt().bits
                else -> List()
            }
        }
    }

    init {
        // Remove leading zeros
        while (bits.isNotEmpty === True) {
            if (bits.last === False) {
                bits.pop()
            } else {
                break
            }
        }
    }

    constructor(n: Number): this(valueOf(n))
    val numBits get() = bits.length

    override operator fun invoke(n: Number) : BinaryUInt {
        return this * TEN + n
    }

    override operator fun unaryMinus() : BinaryUInt {
        return D0
    }

    override operator fun plus(n: Number) : BinaryUInt {
        val other = BinaryUInt(n)
        val iteratorA = bits.iterator()
        val iteratorB = other.bits.iterator()
        val resultBits = List<Boolean>()
        var carry = False

        while (iteratorA.hasNext() or iteratorB.hasNext() === True) {
            val a = if (iteratorA.hasNext() === True) iteratorA.next() else False
            val b = if (iteratorB.hasNext() === True) iteratorB.next() else False
            resultBits.push(carry xor (a xor b))
            carry = (a and b) or (carry and (a xor b))
        }

        if (carry === True) {
            resultBits.push(True)
        }

        return BinaryUInt(resultBits)
    }

    override operator fun inc(): BinaryUInt {
        var carry = True
        val newBits = bits.map { i ->
            val bit = carry xor i
            carry = carry and i
            bit
        }

        if (carry === True) {
            newBits.push(True)
        }

        return BinaryUInt(newBits)
    }

    override operator fun minus(n: Number): BinaryUInt {
        val other = BinaryUInt(n)
        val iteratorA = bits.iterator()
        val iteratorB = other.bits.iterator()
        val resultBits = List<Boolean>()
        var borrow = False

        while(iteratorA.hasNext() or iteratorB.hasNext() === True) {
            val a = if (iteratorA.hasNext() === True) iteratorA.next() else False
            val b = if (iteratorB.hasNext() === True) iteratorB.next() else False
            resultBits.push(borrow xor (a xor b))
            borrow = (!a and b) or (borrow and (!a xor b))
        }
        // If there is a borrow at the end, then B is greater than A
        // In this case we return 0 as this is the closest unsigned integer to the true answer
        if (borrow === True) {
            return D0
        }

        return BinaryUInt(resultBits)
    }

    override operator fun dec(): BinaryUInt {
        var borrow = True
        val newBits = bits.map { i ->
            val bit = borrow xor i
            borrow = borrow and !i
            bit
        }

        // If there is a borrow at the end, then we are decrementing 0
        // In this case we return 0 as we cannot decrement from 0
        return if (borrow === True) D0 else BinaryUInt(newBits)
    }

    override infix fun equals(n: Number?) : Boolean {
        if (n === null) {
            return False
        }

        val other = BinaryUInt(n)
        val iteratorA = bits.iterator()
        val iteratorB = other.bits.iterator()
        while (iteratorA.hasNext() and iteratorB.hasNext() === True) {
            val a = iteratorA.next()
            val b = iteratorB.next()
            if (a xor b === True) {
                return False
            }
        }
        return !(iteratorA.hasNext() or iteratorB.hasNext())
    }

    // This is needed to suppress a warning about the above method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): kotlin.Int { throw Exception() }

    override operator fun times(n: Number) : BinaryUInt {
        val other = BinaryUInt(n)
        var a = this
        var b = other
        // We require order of A to be less than order of B
        if (a.bits.length greaterThan b.bits.length === True) {
            a = other
            b = this
        }

        var result = D0
        b = BinaryUInt(b.bits.copy())
        a.bits.forEach { i ->
            if (i === True) {
                result += b
            }
            // shift b left by one bit
            b.bits.prepend(False)
        }
        return result
    }

    override operator fun div(n: Number): BinaryUInt {
        return divRem(BinaryUInt(n)).first
    }

    override operator fun rem(n: Number): BinaryUInt {
        return divRem(BinaryUInt(n)).second
    }

    fun divRem(divisor: BinaryUInt): Pair<BinaryUInt, BinaryUInt> {
        val iterator = bits.reverseIterator()
        var remainder = BinaryUInt(List())
        val quotient = List<Boolean>()
        while (iterator.hasNext() === True) {
            // shift left remainder by one bit
            val bit = iterator.next()
            if (remainder.bits.isNotEmpty or bit === True) {
                remainder.bits.prepend(bit)
            }

            // shift left quotient by one bit
            quotient.prepend(remainder greaterThanOrEqualTo divisor)
            if (quotient.first === True) {
                remainder -= divisor
            }
        }
        return Pair(BinaryUInt(quotient), remainder)
    }

    override fun compareTo(n: Number): ComparisonResult {
        val other = BinaryUInt(n)
        // Small numbers are handled separately, as they cause problems
        if (bits.isEmpty and other.bits.isNotEmpty === True) {
            return ComparisonResult.LESS
        }
        if (other.bits.isEmpty and bits.isNotEmpty === True) {
            return ComparisonResult.GREATER
        }
        if (this equals D1 and (other notEquals D1) === True) {
            return ComparisonResult.LESS
        }
        if (other equals D1 and (this notEquals D1) === True) {
            return ComparisonResult.GREATER
        }
        return if (bits.length equals other.bits.length === True) {
            compareWithEqualBitSize(other)
        } else {
            bits.length.compareTo(other.bits.length)
        }
    }

    private fun compareWithEqualBitSize(other: BinaryUInt): ComparisonResult {
        val iteratorA = bits.reverseIterator()
        val iteratorB = other.bits.reverseIterator()
        while (iteratorA.hasNext() === True) {
            val a = iteratorA.next()
            val b = iteratorB.next()
            if (a and !b === True) {
                return ComparisonResult.GREATER
            }
            if (!a and b === True) {
                return ComparisonResult.LESS
            }
        }
        return ComparisonResult.EQUAL
    }

    fun isPositive(): Boolean {
        return bits.isNotEmpty
    }
    
    override infix fun shr(n: Number): BinaryUInt {
        val amount = BinaryUInt(n)
        val iterator = bits.iterator()
        var x = amount
        while (iterator.hasNext() and x.isPositive() === True) {
            iterator.next()
            x--
        }
        val newBits = List<Boolean>()
        while (iterator.hasNext() === True) {
            newBits.push(iterator.next())
        }
        return BinaryUInt(newBits)
    }

    override infix fun shl(n: Number): BinaryUInt {
        val amount = BinaryUInt(n)
        val newBits = List.repeating(False, amount)
        bits.forEach { i -> newBits.push(i) }
        return BinaryUInt(newBits)
    }

    infix fun and(n: Number): BinaryUInt {
        val iteratorA = bits.iterator()
        val iteratorB = BinaryUInt(n).bits.iterator()
        val resultBits = List<Boolean>()

        while (iteratorA.hasNext() and iteratorB.hasNext() === True) {
            resultBits.push(iteratorA.next() and iteratorB.next())
        }

        return BinaryUInt(resultBits)
    }

    // biterator -> bits iterator
    fun biterator(): List.Iterator<Boolean> {
        return bits.iterator()
    }

    fun reverseBiterator(): List.Iterator<Boolean> {
        return bits.reverseIterator()
    }

    override fun toDigits(base: Int): List<Digit> {
        val binaryBase = BinaryUInt(base)
        val digits = List<Digit>()
        if (bits.isEmpty === True) {
            return digits
        }

        var value = this
        while (value.isPositive() === True) {
            val (v, r) = value.divRem(binaryBase)
            value = v
            val digit = when(r) {
                D0 -> Digit.D0
                D1 -> Digit.D1
                D2 -> Digit.D2
                D3 -> Digit.D3
                D4 -> Digit.D4
                D5 -> Digit.D5
                D6 -> Digit.D6
                D7 -> Digit.D7
                D8 -> Digit.D8
                else -> Digit.D9
            }
            digits.push(digit)
        }
        return digits
    }

    override fun toString(): String {
        return if (this lessThan TEN === True) {
                 if (this equals D0 === True) "0"
            else if (this equals D1 === True) "1"
            else if (this equals D2 === True) "2"
            else if (this equals D3 === True) "3"
            else if (this equals D4 === True) "4"
            else if (this equals D5 === True) "5"
            else if (this equals D6 === True) "6"
            else if (this equals D7 === True) "7"
            else if (this equals D8 === True) "8"
            else "9"
        } else {
            toDigits().reduce("") { str, d -> d.toString() + str }
        }
    }
}