class Array<T>(element: T? = null) : Iterable<T> {
    private var root: ArrayNode<T>? = if (element.isNotNull === True) ArrayNode(element) else null

    private var _firstNode: ArrayNode<T>? = null
    val firstNode: ArrayNode<T>? get() {
        if (_firstNode.isNull === True) {
            _firstNode = root?.firstNode
        }
        return _firstNode
    }
    private var _lastNode: ArrayNode<T>? = root
    val lastNode: ArrayNode<T>? get() = _lastNode

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
    }

    private fun doubleCapacity() {
        root = ArrayNode(leftChild = root, rightChild = root?.copy())
        root?.leftChild?.lastNode?.nextNode = root?.rightChild?.firstNode
    }

    override fun iterator(): Iterator<T> {
        return ArrayIterator(this)
    }

    fun reverseIterator(): Iterator<T> {
        return ArrayIterator(this, True)
    }

    private class ArrayIterator<T>(private val source: Array<T>, private val reversed: Boolean = False) : Iterator<T> {
        private var nextNode: ArrayNode<T>? = if(reversed === True) source.lastNode else source.firstNode

        override fun hasNext(): kotlin.Boolean {
            return nextNode.isNotNull === True
        }

        override fun next(): T {
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
}