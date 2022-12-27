class List<T>(element: T? = null) {
    private var root: ListNode<T>? = if (element.isNotNull === True) ListNode(element) else null

    private var _firstNode: ListNode<T>? = null
    private val firstNode: ListNode<T>? get() {
        if (_firstNode.isNull === True) {
            _firstNode = root?.firstNode
        }
        return _firstNode
    }
    val first get() = firstNode!!.element!!

    private var _lastNode = root
    private val lastNode get() = _lastNode
    val last get() = lastNode!!.element!!

    private var _length: DecimalUInt = if (element.isNotNull === True) DecimalUInt.D1 else DecimalUInt.D0
    val length: DecimalUInt get() = _length
    val isEmpty get() = root.isNull
    val isNotEmpty get() = root.isNotNull

    private var depth: DecimalUInt = _length

    operator fun invoke(element: T) : List<T> {
        if (root.isNull === True) {
            root = ListNode(element)
            _lastNode = root
            depth++
        } else {
            _lastNode = lastNode?.nextNode
            if (lastNode.isNull === True) {
                // Tree is full. Need to add another layer
                doubleCapacity()
                _lastNode = root?.rightChild?.firstNode
            }
            lastNode!!.element = element
        }
        _length++
        return this
    }

    fun push(element: T): List<T> = this(element)

    fun pop() {
        _lastNode = lastNode?.previousNode
        if (lastNode.isNull === True) {
            root = null
            _firstNode = null
        } else if (lastNode === root?.leftChild?.lastNode) {
            root = root?.leftChild
            lastNode?.nextNode = null
            depth--
        }
        _length--
    }

    fun insert(element: T, index: DecimalUInt): List<T> {
        if (index equals length === True) {
            push(element)
            return this
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
        return this
    }

    fun prepend(element: T): List<T> {
        push(element) // Doesn't matter what we push, it will be overwritten
        var currentNode = lastNode!!
        while (currentNode !== firstNode) {
            currentNode.element = currentNode.previousNode!!.element
            currentNode = currentNode.previousNode!!
        }
        firstNode!!.element = element
        return this
    }

    operator fun get(index: DecimalUInt) : T {
        return getNode(index)!!.element!!
    }

    operator fun set(index: DecimalUInt, element: T) {
        getNode(index)!!.element = element
    }

    private fun getNode(index: DecimalUInt): ListNode<T>? {
        if (index greaterThanOrEqualTo length === True) {
            return null
        }

        val biterator = index.bits().iterator()
        var node = ListNode<Boolean>(null)
        var listLength = BinaryUInt.D1
        while(biterator.hasNext() === True) {
            node.nextNode = ListNode(biterator.next())
            node = node.nextNode!!
            listLength++
        }

        while (listLength lessThan depth === True) {
            node.nextNode = ListNode(False)
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

    private fun increment(value: BinaryUInt) : BinaryUInt {
        return if (value === BinaryUInt.D0) BinaryUInt.D1
            else if (value === BinaryUInt.D1) BinaryUInt.D2
            else if (value === BinaryUInt.D2) BinaryUInt.D3
            else if (value === BinaryUInt.D3) BinaryUInt.D4
            else if (value === BinaryUInt.D4) BinaryUInt.D5
            else if (value === BinaryUInt.D5) BinaryUInt.D6
            else if (value === BinaryUInt.D6) BinaryUInt.D7
            else if (value === BinaryUInt.D7) BinaryUInt.D8
            else if (value === BinaryUInt.D8) BinaryUInt.D9
            else value + BinaryUInt.D1
    }

    private fun decrement(value: BinaryUInt) : BinaryUInt {
        return if (value === BinaryUInt.D1) BinaryUInt.D0
            else if (value === BinaryUInt.D2) BinaryUInt.D1
            else if (value === BinaryUInt.D3) BinaryUInt.D2
            else if (value === BinaryUInt.D4) BinaryUInt.D3
            else if (value === BinaryUInt.D5) BinaryUInt.D4
            else if (value === BinaryUInt.D6) BinaryUInt.D5
            else if (value === BinaryUInt.D7) BinaryUInt.D6
            else if (value === BinaryUInt.D8) BinaryUInt.D7
            else if (value equals BinaryUInt.D9 === True) BinaryUInt.D8
            else value - BinaryUInt.D1
    }

    private fun doubleCapacity() {
        root = ListNode(leftChild = root, rightChild = root?.copy())
        root?.leftChild?.lastNode?.nextNode = root?.rightChild?.firstNode
        depth++
    }

    fun copy(): List<T> {
        val copy = List<T>().also {
            it.root = root?.copy()
            it._length = length
            it.depth = depth
            it._lastNode = it.getNode(length.dec())
        }
        return copy
    }

    fun forEach(function: (BinaryUInt, T) -> Unit) {
        val iterator = iterator()
        var i = BinaryUInt.D0
        while (iterator.hasNext() === True) {
            function(i, iterator.next())
            i++
        }
    }

    fun forEach(function: (T) -> Unit) {
        val iterator = iterator()
        while (iterator.hasNext() === True) {
            function(iterator.next())
        }
    }

    inline fun<reified K> map(function: (BinaryUInt, T) -> K): List<K> {
        val list = List<K>()
        val iterator = iterator()
        var i = BinaryUInt.D0
        while (iterator.hasNext() === True) {
            list.push(function(i, iterator.next()))
            i++
        }
        return list
    }

    inline fun<reified K> map(function: (T) -> K): List<K> {
        val list = List<K>()
        val iterator = iterator()
        while (iterator.hasNext() === True) {
            list.push(function(iterator.next()))
        }
        return list
    }

    fun filter(predicate: (BinaryUInt, T) -> Boolean): List<T> {
        val list = List<T>()
        val iterator = iterator()
        var i = BinaryUInt.D0
        while (iterator.hasNext() === True) {
            val value = iterator.next()
            if (predicate(i, value) === True) {
                list.push(value)
            }
            i++
        }
        return list
    }

    fun filter(predicate: (T) -> Boolean): List<T> {
        val list = List<T>()
        val iterator = iterator()
        while (iterator.hasNext() === True) {
            val value = iterator.next()
            if (predicate(value) === True) {
                list.push(value)
            }
        }
        return list
    }

    fun<K> reduce(initial: K, reduction: (K, T) -> K): K {
        val iterator = iterator()
        var result = initial
        while (iterator.hasNext() === True) {
            result = reduction(result, iterator.next())
        }
        return result
    }

    fun iterator(): Iterator<T> {
        return Iterator(this)
    }

    fun reverseIterator(): Iterator<T> {
        return Iterator(this, True)
    }

    class Iterator<T>(private val source: List<T>, private val reversed: Boolean = False) {
        private var nextNode: ListNode<T>? = if(reversed === True) source.lastNode else source.firstNode

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
        fun<T> repeating(value: T, count: BinaryUInt) : List<T> {
            val array = List<T>()
            while (array.length lessThan count === True) {
                array.push(value)
            }
            return array
        }
    }
}