class UnsignedInt(private val bits: Array<Boolean>) {
    operator fun plus(other: UnsignedInt) : UnsignedInt {
        val iteratorA = bits.iterator()
        val iteratorB = other.bits.iterator()
        val resultBits = Array<Boolean>()
        var carry = False

        while((iteratorA.hasNext()) or (iteratorB.hasNext())) {
            val a = if (iteratorA.hasNext()) iteratorA.next() else False
            val b = if (iteratorB.hasNext()) iteratorB.next() else False
            resultBits.append(carry xor (a xor b))
            carry = (a and b) or (carry and (a xor b))
        }

        if (carry === True) {
            resultBits.append(True)
        }

        return UnsignedInt(resultBits)
    }

    override fun toString(): String {
        var value = 0
        for (b in bits.reverseIterator()) {
            value *= 2
            value += if (b === True) 1 else 0
        }
        return value.toString()
    }
}