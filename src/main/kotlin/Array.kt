class Array<T> : Iterable<T> {
    private var root: ArrayNode<T>? = null
    private var lastNode: ArrayNode<T>? = null



    fun append(element: T) {
        if (root == null) {
            root = ArrayNode(element)
            lastNode = root
        } else {
            lastNode = lastNode!!.nextNode
            if (lastNode == null) {
                // Tree is full. Need to add another layer
                root = ArrayNode(leftChild = root, rightChild = root!!.copy())
                lastNode = root!!.rightChild!!.firstNode
            }
            lastNode!!.element = element
        }
    }

    override fun iterator(): Iterator<T> {
        return ArrayIterator(this)
    }

    private class ArrayIterator<T>(private val source: Array<T>) : Iterator<T> {
        private var nextNode: ArrayNode<T>? = source.root?.firstNode
        override fun hasNext(): Boolean {
            return nextNode != null
        }

        override fun next(): T {
            val element = nextNode!!.element
            nextNode = if (nextNode == source.lastNode) {
                null
            } else {
                nextNode!!.nextNode
            }
            return element!!
        }

    }
}