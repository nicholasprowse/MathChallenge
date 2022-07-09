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

    private var depth: UnsignedInt = _length

    operator fun invoke(element: T) : Array<T> {
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
        _length = increment(_length)
        return this
    }

    fun push(element: T) {
        this(element)
    }

    fun pop() {
        _lastNode = lastNode?.previousNode
        if (lastNode.isNull === True) {
            root = null
            _firstNode = null
        } else if (lastNode === root?.leftChild?.lastNode) {
            root = root?.leftChild
            lastNode?.nextNode = null
            depth = decrement(depth)
        }
        _length = decrement(_length)
    }

    operator fun get(index: UnsignedInt) : T {
        if (index greaterThanOrEqualTo length === True) {
            return null!!
        }

        val biterator = index.biterator()
        var node = ArrayNode(biterator.next())      // UnsignedInt biterator is guaranteed to have at least one element
        var listLength = UnsignedInt.ONE
        while(biterator.hasNext() === True) {
            node.nextNode = ArrayNode(biterator.next())
            node = node.nextNode!!
            listLength++
        }

        while (listLength lessThan depth === True) {
            node.nextNode = ArrayNode(False)
            node = node.nextNode!!
            listLength++
        }

        var treeNode = if (node.element === True) root?.rightChild else root?.leftChild
        while (node.previousNode.isNotNull === True) {
            node = node.previousNode!!
            treeNode = if (node.element === True) treeNode?.rightChild else treeNode?.leftChild
        }

        return treeNode!!.element!!
    }

    private fun increment(value: UnsignedInt) : UnsignedInt {
        return   if (value === UnsignedInt.ZERO ) UnsignedInt.ONE
            else if (value === UnsignedInt.ONE  ) UnsignedInt.TWO
            else if (value === UnsignedInt.TWO  ) UnsignedInt.THREE
            else if (value === UnsignedInt.THREE) UnsignedInt.FOUR
            else if (value === UnsignedInt.FOUR ) UnsignedInt.FIVE
            else if (value === UnsignedInt.FIVE ) UnsignedInt.SIX
            else if (value === UnsignedInt.SIX  ) UnsignedInt.SEVEN
            else if (value === UnsignedInt.SEVEN) UnsignedInt.EIGHT
            else if (value === UnsignedInt.EIGHT) UnsignedInt.NINE
            else value + UnsignedInt.ONE
    }

    private fun decrement(value: UnsignedInt) : UnsignedInt {
        return   if (value === UnsignedInt.ONE  ) UnsignedInt.ZERO
            else if (value === UnsignedInt.TWO  ) UnsignedInt.ONE
            else if (value === UnsignedInt.THREE) UnsignedInt.TWO
            else if (value === UnsignedInt.FOUR ) UnsignedInt.THREE
            else if (value === UnsignedInt.FIVE ) UnsignedInt.FOUR
            else if (value === UnsignedInt.SIX  ) UnsignedInt.FIVE
            else if (value === UnsignedInt.SEVEN) UnsignedInt.SIX
            else if (value === UnsignedInt.EIGHT) UnsignedInt.SEVEN
            else if (value equals UnsignedInt.NINE === True) UnsignedInt.EIGHT
            else value - UnsignedInt.ONE
    }

    private fun doubleCapacity() {
        root = ArrayNode(leftChild = root, rightChild = root?.copy())
        root?.leftChild?.lastNode?.nextNode = root?.rightChild?.firstNode
        depth = increment(depth)
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
                result += ", "
            }
        }
        return "$result]"
    }

    companion object {
        fun<T> repeating(value: T, count: UnsignedInt) : Array<T> {
            val array = Array<T>()
            while (array.length lessThan count === True) {
                array.push(value)
            }
            return array
        }
    }
}