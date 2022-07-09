class Array<T>(element: T? = null) {
    private var root: ArrayNode<T>? = if (element.isNotNull === True) ArrayNode(element) else null

    private var _firstNode: ArrayNode<T>? = null
    private val firstNode: ArrayNode<T>? get() {
        if (_firstNode.isNull === True) {
            _firstNode = root?.firstNode
        }
        return _firstNode
    }
    val first get() = firstNode!!.element!!

    private var _lastNode = root
    private val lastNode get() = _lastNode
    val last get() = lastNode!!.element!!

    private var _length: UInt = if (element.isNotNull === True) UInt.ONE else UInt.ZERO
    val length: UInt get() = _length
    val isEmpty get() = root.isNull
    val isNotEmpty get() = root.isNotNull

    private var depth: UInt = _length

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

    fun insert(element: T, index: UInt) {
        if (index equals length === True) {
            push(element)
            return
        }

        val node = getNode(index)
        // Shift everything after the node to the left
        push(element) // Doesn't matter what we push, it will be overwritten
        var currentNode = lastNode!!
        while (currentNode !== node) {
            currentNode.element = currentNode.previousNode!!.element
            currentNode = currentNode.previousNode!!
        }
        node.element = element
    }

    fun prepend(element: T) {
        push(element) // Doesn't matter what we push, it will be overwritten
        var currentNode = lastNode!!
        while (currentNode !== firstNode) {
            currentNode.element = currentNode.previousNode!!.element
            currentNode = currentNode.previousNode!!
        }
        firstNode!!.element = element
    }

    operator fun get(index: UInt) : T {
        return getNode(index).element!!
    }

    operator fun set(index: UInt, element: T) {
        getNode(index).element = element
    }

    private fun getNode(index: UInt): ArrayNode<T> {
        if (index greaterThanOrEqualTo length === True) {
            return null!!
        }

        val biterator = index.biterator()
        var node = ArrayNode<Boolean>(null)
        var listLength = UInt.ZERO
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

        var treeNode = root
        while (node.element.isNotNull === True) {
            treeNode = if (node.element === True) treeNode?.rightChild else treeNode?.leftChild
            node = node.previousNode!!
        }

        return treeNode!!
    }

    private fun increment(value: UInt) : UInt {
        return   if (value === UInt.ZERO ) UInt.ONE
            else if (value === UInt.ONE  ) UInt.TWO
            else if (value === UInt.TWO  ) UInt.THREE
            else if (value === UInt.THREE) UInt.FOUR
            else if (value === UInt.FOUR ) UInt.FIVE
            else if (value === UInt.FIVE ) UInt.SIX
            else if (value === UInt.SIX  ) UInt.SEVEN
            else if (value === UInt.SEVEN) UInt.EIGHT
            else if (value === UInt.EIGHT) UInt.NINE
            else value + UInt.ONE
    }

    private fun decrement(value: UInt) : UInt {
        return   if (value === UInt.ONE  ) UInt.ZERO
            else if (value === UInt.TWO  ) UInt.ONE
            else if (value === UInt.THREE) UInt.TWO
            else if (value === UInt.FOUR ) UInt.THREE
            else if (value === UInt.FIVE ) UInt.FOUR
            else if (value === UInt.SIX  ) UInt.FIVE
            else if (value === UInt.SEVEN) UInt.SIX
            else if (value === UInt.EIGHT) UInt.SEVEN
            else if (value equals UInt.NINE === True) UInt.EIGHT
            else value - UInt.ONE
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
        fun<T> repeating(value: T, count: UInt) : Array<T> {
            val array = Array<T>()
            while (array.length lessThan count === True) {
                array.push(value)
            }
            return array
        }
    }
}