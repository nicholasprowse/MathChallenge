class Array<T>(element: T? = null) {
    private var root: ArrayNode<T>? = if (element.isNotNull === True) ArrayNode(element) else null

    private var _firstNode: ArrayNode<T>? = null
    val firstNode: ArrayNode<T>? get() {
        if (_firstNode.isNull === True) {
            _firstNode = root?.firstNode
        }
        return _firstNode
    }
    val first get() = firstNode!!.element!!

    private var _lastNode = root
    val lastNode get() = _lastNode
    val last get() = lastNode!!.element!!

    private var _length: UnsignedInt = if (element.isNotNull === True) UnsignedInt.ONE else UnsignedInt.ZERO
    val length: UnsignedInt get() = _length
    val isEmpty get() = root.isNull
    val isNotEmpty get() = root.isNotNull

    fun append(element: T) : Array<T> {
        if (root.isNull === True) {
            root = ArrayNode(element)
            _lastNode = root
        } else {
            _lastNode = lastNode?.nextNode
            if (lastNode.isNull === True) {
                // Tree is full. Need to add another layer
                doubleCapacity()
                _lastNode = root?.rightChild?.firstNode
            }
            lastNode!!.element = element
        }
        incrementLength()
        return this
    }

    fun pop() {
        _lastNode = lastNode?.previousNode
        if (lastNode.isNull === True) {
            root = null
            _firstNode = null
        } else if (lastNode === root?.leftChild?.lastNode) {
            root = root?.leftChild
            lastNode?.nextNode = null
        }
        decrementLength()
    }

    private fun incrementLength() {
        _length =
                 if (_length === UnsignedInt.ZERO ) UnsignedInt.ONE
            else if (_length === UnsignedInt.ONE  ) UnsignedInt.TWO
            else if (_length === UnsignedInt.TWO  ) UnsignedInt.THREE
            else if (_length === UnsignedInt.THREE) UnsignedInt.FOUR
            else if (_length === UnsignedInt.FOUR ) UnsignedInt.FIVE
            else if (_length === UnsignedInt.FIVE ) UnsignedInt.SIX
            else if (_length === UnsignedInt.SIX  ) UnsignedInt.SEVEN
            else if (_length === UnsignedInt.SEVEN) UnsignedInt.EIGHT
            else if (_length === UnsignedInt.EIGHT) UnsignedInt.NINE
            else _length + UnsignedInt.ONE
    }

    private fun decrementLength() {
        _length =
                 if (_length === UnsignedInt.ONE  ) UnsignedInt.ZERO
            else if (_length === UnsignedInt.TWO  ) UnsignedInt.ONE
            else if (_length === UnsignedInt.THREE) UnsignedInt.TWO
            else if (_length === UnsignedInt.FOUR ) UnsignedInt.THREE
            else if (_length === UnsignedInt.FIVE ) UnsignedInt.FOUR
            else if (_length === UnsignedInt.SIX  ) UnsignedInt.FIVE
            else if (_length === UnsignedInt.SEVEN) UnsignedInt.SIX
            else if (_length === UnsignedInt.EIGHT) UnsignedInt.SEVEN
            else if (_length equals UnsignedInt.NINE === True) UnsignedInt.EIGHT
            else _length - UnsignedInt.ONE
    }

    private fun doubleCapacity() {
        root = ArrayNode(leftChild = root, rightChild = root?.copy())
        root?.leftChild?.lastNode?.nextNode = root?.rightChild?.firstNode
    }

    fun iterator(): ArrayIterator<T> {
        return ArrayIterator(this)
    }

    fun reverseIterator(): ArrayIterator<T> {
        return ArrayIterator(this, True)
    }

    class ArrayIterator<T>(private val source: Array<T>, private val reversed: Boolean = False) {
        private var nextNode: ArrayNode<T>? = if(reversed === True) source.lastNode else source.firstNode

        fun hasNext(): Boolean {
            return nextNode.isNotNull
        }

        fun next(): T {
            val element = nextNode?.element
            nextNode = if (!reversed and nextNode.identicalTo(source.lastNode) === True) {
                null
            } else if (reversed and nextNode.identicalTo(source.firstNode) === True) {
                null
            } else {
                if(reversed === True) nextNode?.previousNode else nextNode?.nextNode
            }
            return element!!
        }
    }

    override fun toString(): String {
        val iterator = iterator()
        var result = "["
        while (iterator.hasNext() === True) {
            result += iterator.next()
            if (iterator.hasNext() === True) {
                result += ","
            }
        }
        return "$result]"
    }

    companion object {
        fun<T> repeating(value: T, count: UnsignedInt) : Array<T> {
            val array = Array<T>()
            while (count.isPositive() === True) {
                array.append(value)
            }
            return array
        }
    }
}