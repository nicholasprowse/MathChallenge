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

    private var _length: UnsignedInt = if (element.isNotNull === True) D1 else D0
    val length: UnsignedInt get() = _length
    val isEmpty get() = root.isNull
    val isNotEmpty get() = root.isNotNull

    fun append(element: T) {
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
    }

    fun pop() {
        _lastNode = lastNode?.previousNode
        if (lastNode.isNull === True) {
            root = null
        } else if (lastNode === root?.leftChild?.lastNode) {
            root = root?.leftChild
        }
        decrementLength()
    }

    private fun incrementLength() {
        _length =
            if (_length === D0) D1
            else if (_length === D1) D2
            else if (_length === D2) D3
            else if (_length === D3) D4
            else if (_length === D4) D5
            else if (_length === D5) D6
            else if (_length === D6) D7
            else if (_length === D7) D8
            else if (_length === D8) D9
            else _length + D1
    }

    private fun decrementLength() {
        _length =
                 if (_length === D1) D0
            else if (_length === D2) D1
            else if (_length === D3) D2
            else if (_length === D4) D3
            else if (_length === D5) D4
            else if (_length === D6) D5
            else if (_length === D7) D6
            else if (_length === D8) D7
            else if (_length equals D9 === True) D8
            else _length - D1
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

        val ONE: UnsignedInt
        val TWO: UnsignedInt

        init {
            var array = Array(True)
            ONE = UnsignedInt(array)
            array._length = ONE

            array = Array(False)
            array.append(True)
            TWO = UnsignedInt(array)
            array._length = TWO
        }
    }
}