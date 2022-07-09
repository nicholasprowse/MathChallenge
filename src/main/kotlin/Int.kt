class UnsignedInt private constructor(private var bits: Array<Boolean>) {

    companion object {
        val ZERO: UnsignedInt = UnsignedInt(Array())
        val ONE: UnsignedInt = UnsignedInt(Array())
        val TWO: UnsignedInt = UnsignedInt(Array())
        val THREE: UnsignedInt
        val FOUR: UnsignedInt
        val FIVE: UnsignedInt
        val SIX: UnsignedInt
        val SEVEN: UnsignedInt
        val EIGHT: UnsignedInt
        val NINE: UnsignedInt
        val TEN: UnsignedInt

        init {
            ZERO.bits = Array(False)
            ONE.bits = Array(True)
            TWO.bits = Array(False)(True)

            THREE = TWO + ONE
            FOUR = THREE + ONE
            FIVE = FOUR + ONE
            SIX = FIVE + ONE
            SEVEN = SIX + ONE
            EIGHT = SEVEN + ONE
            NINE = EIGHT + ONE
            TEN = NINE + ONE
        }
    }

    init {
        // Remove leading zeros
        if (bits.isNotEmpty === True) {
            // If the first and last nodes are equal, there is only one node. Since we never want an integer will zero
            // bits, we stop at this condition. This is a nice way to check the length is 1 without using length
            while (!bits.last and !bits.lastNode.identicalTo(bits.firstNode) === True) {
                bits.pop()
            }
        }
    }

    operator fun plus(other: UnsignedInt) : UnsignedInt {
        val iteratorA = bits.iterator()
        val iteratorB = other.bits.iterator()
        val resultBits = Array<Boolean>()
        var carry = False

        while(iteratorA.hasNext() or iteratorB.hasNext() === True) {
            val a = if (iteratorA.hasNext() === True) iteratorA.next() else False
            val b = if (iteratorB.hasNext() === True) iteratorB.next() else False
            resultBits.push(carry xor (a xor b))
            carry = (a and b) or (carry and (a xor b))
        }

        if (carry === True) {
            resultBits.push(True)
        }

        return UnsignedInt(resultBits)
    }

    operator fun inc(): UnsignedInt {
        val iterator = bits.iterator()
        val resultBits = Array<Boolean>()
        var carry = True

        while(iterator.hasNext() === True) {
            val a = iterator.next()
            resultBits.push(carry xor a)
            carry = carry and a
        }

        if (carry === True) {
            resultBits.push(True)
        }

        return UnsignedInt(resultBits)
    }

    operator fun minus(other: UnsignedInt): UnsignedInt {
        val iteratorA = bits.iterator()
        val iteratorB = other.bits.iterator()
        val resultBits = Array<Boolean>()
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

        return UnsignedInt(resultBits)
    }

    operator fun dec(): UnsignedInt {
        val iterator = bits.iterator()
        val resultBits = Array<Boolean>()
        var borrow = True

        while(iterator.hasNext() === True) {
            val a = iterator.next()
            resultBits.push(borrow xor a)
            borrow = borrow and !a
        }
        // If there is a borrow at the end, then we are decrementing 0
        // In this case we return 0 as we cannot decrement from 0
        if (borrow === True) {
            return ZERO
        }

        return UnsignedInt(resultBits)
    }

    infix fun equals(other: UnsignedInt?) : Boolean {
        if (other === null) {
            return False
        }
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

    infix fun notEquals(other: UnsignedInt?) : Boolean {
        return !equals(other)
    }

    // This is needed to suppress a warning about the above method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): Int { throw Exception() }

    operator fun times(other: UnsignedInt) : UnsignedInt {
        var a = this
        var b = other
        // We require order of A to be less than order of B
        if (a.bits.length greaterThan b.bits.length === True) {
            a = other
            b = this
        }

        var result = ZERO
        b += ZERO         // This is effectively to copy b as we are going to mutate it
        val iteratorA = a.bits.iterator()
        while (iteratorA.hasNext() === True) {
            if (iteratorA.next() === True) {
                result += b
            }
            // shift left b by one bit
            b.bits.push(False)
            var currentNode = b.bits.lastNode!!
            while (currentNode.previousNode !== null) {
                currentNode.element = currentNode.previousNode!!.element
                currentNode = currentNode.previousNode!!
            }
            b.bits.firstNode?.element = False
        }
        return result
    }

    operator fun div(divisor: UnsignedInt): UnsignedInt {
        return divRem(divisor).first
    }

    operator fun rem(divisor: UnsignedInt): UnsignedInt {
        return divRem(divisor).second
    }

    operator fun invoke(other: UnsignedInt) : UnsignedInt {
        return this * TEN + other
    }

    operator fun rangeTo(other: UnsignedInt) : Array<UnsignedInt> {
        var i = this
        val array = Array<UnsignedInt>()
        while (i lessThanOrEqualTo other === True) {
            array.push(i)
            i++
        }
        return array
    }

    infix fun until(other: UnsignedInt) : Array<UnsignedInt> {
        var i = this
        val array = Array<UnsignedInt>()
        while (i lessThan other === True) {
            array.push(i)
            i++
        }
        return array
    }


    fun divRem(divisor: UnsignedInt): Pair<UnsignedInt, UnsignedInt> {
        val iterator = bits.reverseIterator()
        var remainder = UnsignedInt(Array(False))
        val quotient = Array<Boolean>()
        while (iterator.hasNext() === True) {

            // shift left remainder by one bit
            if (remainder.isPositive() === True) {
                remainder.bits.push(False)
            }
            var currentNode = remainder.bits.lastNode!!
            while (currentNode.previousNode !== null) {
                currentNode.element = currentNode.previousNode!!.element
                currentNode = currentNode.previousNode!!
            }
            remainder.bits.firstNode?.element = iterator.next()

            // shift left quotient by one bit
            quotient.push(False)
            currentNode = quotient.lastNode!!
            while (currentNode.previousNode !== null) {
                currentNode.element = currentNode.previousNode!!.element
                currentNode = currentNode.previousNode!!
            }
            quotient.firstNode?.element = remainder greaterThanOrEqualTo divisor

            if (quotient.firstNode?.element === True) {
                remainder -= divisor
            }
        }
        return Pair(UnsignedInt(quotient), remainder)
    }

    infix fun lessThan(other: UnsignedInt): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) True else False
    }

    infix fun greaterThan(other: UnsignedInt): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) True else False
    }

    infix fun lessThanOrEqualTo(other: UnsignedInt): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) False else True
    }

    infix fun greaterThanOrEqualTo(other: UnsignedInt): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) False else True
    }

    private fun compareTo(other: UnsignedInt): ComparisonResult {
        // Small numbers are handled separately, as they cause problems
        if (this equals ZERO and (other notEquals ZERO) === True) {
            return ComparisonResult.LESS
        }
        if (other equals ZERO and (this notEquals ZERO) === True) {
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

    private fun compareWithEqualBitSize(other: UnsignedInt): ComparisonResult {
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

    private enum class ComparisonResult {
        LESS, EQUAL, GREATER
    }

    fun isPositive() : Boolean {
        return this notEquals ZERO
    }

    // biterator -> bits iterator
    fun biterator(): Array.ArrayIterator<Boolean> {
        return bits.iterator()
    }

    fun reverseBiterator(): Array.ArrayIterator<Boolean> {
        return bits.reverseIterator()
    }

    override fun toString(): String {
        if (this equals ZERO === True) {
            return "0"
        }

        var result = ""
        var value = this
        while (value.isPositive() === True) {
            val (v, r) = value.divRem(TEN)
            value = v
            val digit = if (r equals ZERO === True) '0'
                   else if (r equals ONE === True) '1'
                   else if (r equals D2 === True) '2'
                   else if (r equals D3 === True) '3'
                   else if (r equals D4 === True) '4'
                   else if (r equals D5 === True) '5'
                   else if (r equals D6 === True) '6'
                   else if (r equals D7 === True) '7'
                   else if (r equals D8 === True) '8'
                   else '9'
            result = digit + result
        }
        return result
    }
}