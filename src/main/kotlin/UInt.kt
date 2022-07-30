class UInt private constructor(private var bits: List<Boolean>): Number() {

    companion object {
        val  ZERO: UInt = UInt(List())
        val   ONE: UInt = UInt(List())
        val   TWO: UInt = UInt(List())
        val THREE: UInt
        val  FOUR: UInt
        val  FIVE: UInt
        val   SIX: UInt
        val SEVEN: UInt
        val EIGHT: UInt
        val  NINE: UInt
        val   TEN: UInt

        init {
            ZERO.bits = List()
            ONE.bits = List(True)
            TWO.bits = List(False)(True)

            THREE = TWO + ONE
            FOUR = THREE + ONE
            FIVE = FOUR + ONE
            SIX = FIVE + ONE
            SEVEN = SIX + ONE
            EIGHT = SEVEN + ONE
            NINE = EIGHT + ONE
            TEN = NINE + ONE
        }

        private fun valueOf(n: Number): List<Boolean> {
            return when(n) {
                is UInt -> n.bits
                is Int -> n.toUInt().bits
                is Float -> n.toInt().toUInt().bits
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

    override operator fun invoke(n: Number) : UInt {
        return this * TEN + n
    }

    override operator fun unaryMinus() : UInt {
        return ZERO
    }

    override operator fun plus(n: Number) : UInt {
        val other = UInt(n)
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

        return UInt(resultBits)
    }

    override operator fun inc(): UInt {
        var carry = True
        val newBits = bits.map { i ->
            val bit = carry xor i
            carry = carry and i
            bit
        }

        if (carry === True) {
            newBits.push(True)
        }

        return UInt(newBits)
    }

    override operator fun minus(n: Number): UInt {
        val other = UInt(n)
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
            return ZERO
        }

        return UInt(resultBits)
    }

    override operator fun dec(): UInt {
        var borrow = True
        val newBits = bits.map { i ->
            val bit = borrow xor i
            borrow = borrow and !i
            bit
        }

        // If there is a borrow at the end, then we are decrementing 0
        // In this case we return 0 as we cannot decrement from 0
        return if (borrow === True) ZERO else UInt(newBits)
    }

    override infix fun equals(n: Number?) : Boolean {
        if (n === null) {
            return False
        }

        val other = UInt(n)
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

    override operator fun times(n: Number) : UInt {
        val other = UInt(n)
        var a = this
        var b = other
        // We require order of A to be less than order of B
        if (a.bits.length greaterThan b.bits.length === True) {
            a = other
            b = this
        }

        var result = ZERO
        b = UInt(b.bits.copy())
        a.bits.forEach { i ->
            if (i === True) {
                result += b
            }
            // shift b left by one bit
            b.bits.prepend(False)
        }
        return result
    }

    override operator fun div(n: Number): UInt {
        return divRem(UInt(n)).first
    }

    override operator fun rem(n: Number): UInt {
        return divRem(UInt(n)).second
    }

    fun divRem(divisor: UInt): Pair<UInt, UInt> {
        val iterator = bits.reverseIterator()
        var remainder = UInt(List())
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
        return Pair(UInt(quotient), remainder)
    }

    override fun compareTo(n: Number): ComparisonResult {
        val other = UInt(n)
        // Small numbers are handled separately, as they cause problems
        if (bits.isEmpty and other.bits.isNotEmpty === True) {
            return ComparisonResult.LESS
        }
        if (other.bits.isEmpty and bits.isNotEmpty === True) {
            return ComparisonResult.GREATER
        }
        if (this equals ONE and (other notEquals ONE) === True) {
            return ComparisonResult.LESS
        }
        if (other equals ONE and (this notEquals ONE) === True) {
            return ComparisonResult.GREATER
        }
        return if (bits.length equals other.bits.length === True) {
            compareWithEqualBitSize(other)
        } else {
            bits.length.compareTo(other.bits.length)
        }
    }

    private fun compareWithEqualBitSize(other: UInt): ComparisonResult {
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
    
    override infix fun shr(n: Number): UInt {
        val amount = UInt(n)
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
        return UInt(newBits)
    }

    override infix fun shl(n: Number): UInt {
        val amount = UInt(n)
        val newBits = List.repeating(False, amount)
        bits.forEach { i -> newBits.push(i) }
        return UInt(newBits)
    }

    infix fun and(n: Number): UInt {
        val iteratorA = bits.iterator()
        val iteratorB = UInt(n).bits.iterator()
        val resultBits = List<Boolean>()

        while (iteratorA.hasNext() and iteratorB.hasNext() === True) {
            resultBits.push(iteratorA.next() and iteratorB.next())
        }

        return UInt(resultBits)
    }

    // biterator -> bits iterator
    fun biterator(): List.Iterator<Boolean> {
        return bits.iterator()
    }

    fun reverseBiterator(): List.Iterator<Boolean> {
        return bits.reverseIterator()
    }

    override fun toDigits(base: UInt): List<UInt> {
        val digits = List<UInt>()
        if (bits.isEmpty === True) {
            return digits
        }

        var value = this
        while (value.isPositive() === True) {
            val (v, r) = value.divRem(base)
            value = v
            digits.push(r)
        }
        return digits
    }

    override fun toString(): String {
        return if (this lessThan TEN === True) {
                 if (this equals ZERO  === True) "0"
            else if (this equals ONE   === True) "1"
            else if (this equals TWO   === True) "2"
            else if (this equals THREE === True) "3"
            else if (this equals FOUR  === True) "4"
            else if (this equals FIVE  === True) "5"
            else if (this equals SIX   === True) "6"
            else if (this equals SEVEN === True) "7"
            else if (this equals EIGHT === True) "8"
            else "9"
        } else {
            toDigits().reduce("") { str, d -> d.toString() + str }
        }
    }
}