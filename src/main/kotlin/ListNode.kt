class ListNode<T>(
    var element: T? = null,
    leftChild: ListNode<T>? = null,
    rightChild: ListNode<T>? = null
) {
    private var parent: ListNode<T>? = null
    var leftChild: ListNode<T>? = null
        set(node) {
            node?.parent = this
            field = node
        }
    var rightChild: ListNode<T>? = null
        set(node) {
            node?.parent = this
            field = node
        }

    private var _nextNode: ListNode<T>? = null
    var nextNode: ListNode<T>? get() = _nextNode
        set(node) {
            node?._previousNode = this
            _nextNode = node
        }

    private var _previousNode: ListNode<T>? = null
    var previousNode: ListNode<T>? get() = _previousNode
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

    fun copy() : ListNode<T> {
        val newNode = ListNode(element)
        newNode.leftChild = leftChild?.copy()
        newNode.rightChild = rightChild?.copy()
        newNode.leftChild?.lastNode?.nextNode = newNode.rightChild?.firstNode
        return newNode
    }

    /*
    The left most descendant of this node
     */
    val firstNode: ListNode<T> get() {
        var node = this
        while (true) {
            node = node.leftChild ?: return node
        }
    }

    /*
    The right most descendant of this node
     */
    val lastNode: ListNode<T> get() {
        var node = this
        while (true) {
            node = node.rightChild ?: return node
        }
    }
}