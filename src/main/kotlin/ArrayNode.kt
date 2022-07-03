class ArrayNode<T>(var element: T? = null, leftChild: ArrayNode<T>? = null, rightChild: ArrayNode<T>? = null) {
    var isLeftChild = false
    var parent: ArrayNode<T>? = null
    var leftChild: ArrayNode<T>? = null
        set(node) {
            if (node != null) {
                node.isLeftChild = true
                node.parent = this
            }
            field = node
        }
    var rightChild: ArrayNode<T>? = null
        set(node) {
            if (node != null) {
                node.isLeftChild = false
                node.parent = this
            }
            field = node
        }

    val hasParent get() = parent != null
    val isLeaf get() = leftChild == null && rightChild == null

    init {
        this.rightChild = rightChild
        this.leftChild = leftChild
    }

    fun copy() : ArrayNode<T> {
        val newNode = ArrayNode(element)
        if (leftChild != null) {
            newNode.leftChild = leftChild!!.copy()
        }

        if (rightChild != null) {
            newNode.rightChild = rightChild!!.copy()
        }

        return newNode
    }

    /*
    The left most descendant of this node
     */
    val firstNode: ArrayNode<T> get() {
        var node = this
        while (true) {
            node = node.leftChild ?: return node
        }
    }

    /*
    The leaf node immediately to the right of this node
     */
    val nextNode: ArrayNode<T>? get() {
        var nextNode = this

        while (true) {
            if (nextNode.hasParent) {
                if (nextNode.isLeftChild) {
                    return nextNode.parent?.rightChild?.firstNode
                } else {
                    nextNode = nextNode.parent!!
                }
            } else {
                // Reached root, so there is no next node
                return null
            }
        }
    }
}