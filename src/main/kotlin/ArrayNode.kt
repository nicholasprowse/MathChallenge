class ArrayNode<T>(
    var element: T? = null,
    leftChild: ArrayNode<T>? = null,
    rightChild: ArrayNode<T>? = null
) {
    private var parent: ArrayNode<T>? = null
    var leftChild: ArrayNode<T>? = null
        set(node) {
            node?.parent = this
            field = node
        }
    var rightChild: ArrayNode<T>? = null
        set(node) {
            node?.parent = this
            field = node
        }

    private var _nextNode: ArrayNode<T>? = null
    var nextNode: ArrayNode<T>? get() = _nextNode
        set(node) {
            node?._previousNode = this
            _nextNode = node
        }

    private var _previousNode: ArrayNode<T>? = null
    var previousNode: ArrayNode<T>? get() = _previousNode
        set(node) {
            node?._nextNode = this
            _previousNode = node
        }

//    val hasParent get() = parent != null
//    val isLeaf get() = leftChild.isNull and rightChild.isNull

    init {
        this.rightChild = rightChild
        this.leftChild = leftChild
    }

    fun copy() : ArrayNode<T> {
        val newNode = ArrayNode(element)
        newNode.leftChild = leftChild?.copy()
        newNode.rightChild = rightChild?.copy()
        newNode.leftChild?.lastNode?.nextNode = newNode.rightChild?.firstNode
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
    The right most descendant of this node
     */
    val lastNode: ArrayNode<T> get() {
        var node = this
        while (true) {
            node = node.rightChild ?: return node
        }
    }
}