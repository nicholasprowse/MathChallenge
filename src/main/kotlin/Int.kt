import java.math.BigInteger

class UnsignedInt(private val bits: Array<Boolean>) {

    init {
        // Remove leading zeros
        while (bits.last === False) {
            bits.pop()
            if (bits.isEmpty === True) {
                bits.append(False)
                break
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
            resultBits.append(carry xor (a xor b))
            carry = (a and b) or (carry and (a xor b))
        }

        if (carry === True) {
            resultBits.append(True)
        }

        return UnsignedInt(resultBits)
    }

    operator fun inc(): UnsignedInt {
        val iterator = bits.iterator()
        val resultBits = Array<Boolean>()
        var carry = True

        while(iterator.hasNext() === True) {
            val a = iterator.next()
            resultBits.append(carry xor a)
            carry = carry and a
        }

        if (carry === True) {
            resultBits.append(True)
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
            resultBits.append(borrow xor (a xor b))
            borrow = (!a and b) or (borrow and (!a xor b))
        }
        // If there is a borrow at the end, then B is greater than A
        // In this case we return 0 as this is the closest unsigned integer to the true answer
        if (borrow === True) {
            return D0
        }

        return UnsignedInt(resultBits)
    }

    operator fun dec(): UnsignedInt {
        val iterator = bits.iterator()
        val resultBits = Array<Boolean>()
        var borrow = True

        while(iterator.hasNext() === True) {
            val a = iterator.next()
            resultBits.append(borrow xor a)
            borrow = borrow and !a
        }
        // If there is a borrow at the end, then we are decrementing 0
        // In this case we return 0 as we cannot decrement from 0
        if (borrow === True) {
            return D0
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

        var result = D0
        b += D0         // This is effectively to copy b as we are going to mutate it
        val iteratorA = a.bits.iterator()
        while (iteratorA.hasNext() === True) {
            if (iteratorA.next() === True) {
                result += b
            }
            // shift left b by one bit
            b.bits.append(False)
            var currentNode = b.bits.lastNode!!
            while (currentNode.previousNode !== null) {
                currentNode.element = currentNode.previousNode!!.element
                currentNode = currentNode.previousNode!!
            }
            b.bits.firstNode?.element = False
        }
        return result
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
        if (this equals D0 and other.isPositive() === True) {
            return ComparisonResult.LESS
        }
        if (other equals D0 and isPositive() === True) {
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
            return bits.length.compareTo(other.bits.length)
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
        val iterator = bits.iterator()
        while(iterator.hasNext() === True) {
            if (iterator.next() === True) {
                return True
            }
        }
        return False
    }

    override fun toString(): String {
        var value = BigInteger.valueOf(0)
        val iterator = bits.reverseIterator()
        while (iterator.hasNext() === True) {
            value = value.multiply(BigInteger.TWO)

            value = value.add(if (iterator.next() === True) BigInteger.ONE else BigInteger.ZERO)
        }
        return value.toString()
    }
}