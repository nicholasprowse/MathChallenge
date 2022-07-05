class UnsignedInt(private val bits: Array<Boolean>) {
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

    operator fun minus(other: UnsignedInt) : UnsignedInt {
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

        // Remove leading zeros
        while (resultBits.last === False) {
            resultBits.pop()
            if (resultBits.isEmpty === True) {
                return D0
            }
        }

        return UnsignedInt(resultBits)
    }

    infix fun equals(other: UnsignedInt?) : Boolean {
        if (other.isNull === True) {
            return False
        }
        val iteratorA = bits.iterator()
        val iteratorB = other!!.bits.iterator()
        while (iteratorA.hasNext() or iteratorB.hasNext() === True) {
            val a = if (iteratorA.hasNext() === True) iteratorA.next() else False
            val b = if (iteratorB.hasNext() === True) iteratorB.next() else False
            if (a xor b === True) {
                return False
            }
        }
        return True
    }

    // This is needed to suppress a warning about the above method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): Int { throw Exception() }

    operator fun times(other: UnsignedInt) : UnsignedInt {
        return this
    }

    fun compareTo(other: UnsignedInt) : Boolean {
        return True
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
        var value = 0
        val iterator = bits.reverseIterator()
        while (iterator.hasNext() === True) {
            value *= 2
            value += if (iterator.next() === True) 1 else 0
        }
        return value.toString()
    }
}