# Math Challenge
A Math library built entirely from booleans

The goal of this project is to create a library of basic math functions using absolutely no types or classes, except the ones I define. 
This means even primitive types and arrays are forbidden, as are all operators, except reference equality (`===`). Arrays are also not allowed, 
so collections must be built from scratch without any preexisting functionalty.

## Rules:
  - No types are allows except the ones I define. This excludes everything, even primitive types. There are two exceptions to this 
    - The `toString` method is allowed to use `Char` and `String` internally, and is allowed to return a `String`. `toString` is only allowed to 
    be called from other `toString` methods, or when printing an object
    - Kotlin Boolean primitives can be implicitly created in condition and loop statements, as Kotlin does not allow use of if and while statements 
    without a primitive `Boolean` as the condition. All if/while statements should be of the form `if/while(<expression 1> === <expression 2>)`
  - No external classes can be referenced, and no external functions can be called. This includes builtin functions, even operators 
  (except reference equality, `===`)

## Goals
  - To be able to compute the following to arbitrary precision
    - Basic arithmetic: addition, subtraction, multiplication, division, floor division, mod, exponents
    - pi and e
    - exponential function, natural logarithm, and logarithms to any base
    - Trigonometric functions: sin, cos, tan, sec, cosec, cotan
    - Inverse trigonometric functions: asin, acos, atan, asec, acosec, acotan
    - Hyperbolic trig functions: sinh, cosh, tanh, asinh, acosh, atanh
    - Root finder to polynomials
    - Root finder for arbitrary function
  - Random number generator: Random int in range and random float of arbitrary precision
  - There is no requirement on speed. Due to the complexity of implementing all of this with no builtin types, it is not expected to be
  performant, however it must be precise and accurate

## Progress
At the time of writing, I have successfully created the following classes, each with all the standard functionality
- Boolean
- List<T>
- UInt (unsigned int)
- Int (signed int)
- Float
- Some basing math operations such as floor, ceiling, round, max, min and factorial

The three numeric types are of arbitrary size, meaning they can be as large as needed. This means enormously large numbers can be computed with no overflow, and decimals can have very high precision. The only limitation is the computers memory and computing speed

# Explanation

## Boolean

The first step is to create a Boolean class with a True and False instance. The Boolean class has a private constructor, so the only instance of this class that will ever exist are True and False. Note that this class has no properties. It doesn't need any, because all we need to be able to do is check if an instance of Boolean is equivalent to True or False

    class Boolean private constructor() {
        companion object {
            val True = Boolean()
            val False = Boolean()
        }
        ...
    }

The boolean operators can all be implemented using basic properties of if statements. The boolean operators cannot be overloaded (except not), so I have opted for named infix functions instead. We also add a toString() method to aid in debugging and outputting results

    infix fun or(other: Boolean) : Boolean {
        return if (this === True) True else other
    }

    infix fun and(other: Boolean) : Boolean {
        return if (this === True) other else False
    }

    infix fun xor(other: Boolean) : Boolean {
        return if (this === other) False else True
    }

    operator fun not() : Boolean {
        return if (this === True) False else True
    }

    override fun toString(): String {
        return if (this === True) "true" else "false"
    }

## List

Now we have a Boolean class, with all the typical operators. That was just the warmup. Now comes the hard part of creating lists and integers. The challenge here is that you cannot create one in isolation. Lists depend on integers as they have an integer length, and they are indexed by integers, while integers are represented by an list of bits. We need to be very careful when creating these classes that we don't create infinite recursive loops due to this circular dependency.

To start with, we create a very basic list. Before we have an integer class, our list cannot have a length, and we will not be able to index it. But how do we create an list without any reference to an array in the first place? A first thought would be to use a linked list. This is a data structure consisting of nodes, where each node has a value, and a reference to the next (and maybe previous) node. This allows us to store a collection of objects without the need for arrays or integers. We just need to keep a reference to the first node, then we can reach any other node by repeatedly moving to the next node. This would work, however, it poses a problem. In order to retrieve the 3254th element in the list, we have to traverse through 3254 nodes just to get it. For large lists, this will slow us down a lot, especially since indexing a list is such a fundamental and common task.

A better option is to use a binary tree. Each node has two children: a left and a right child. Only the nodes at the bottom of the tree have values associated with them. e.g. Say we have a list with 8 elements. The root node has two children, each of which have two children. That is the root has 4 grandchildren. Each of these has 2 children, for a total of 8 great grandchildren. Since there are 8 nodes on this layer, there is no need for further layers. So, within each of these bottom nodes we store an element. 

But why is this better? Say we want to retrieve element with index 5. This is the 5th element from the left on the bottom row of the tree. Starting from the root node, and traversing down through the children, we will find that we need to go right, then left, then right. Given an index, how do we know the sequence of lefts and rights to get to the element? Listing them all out for the case of a list of size 8 given us
- 0: LLL
- 1: LLR
- 2: LRL
- 3: LRR
- 4: RLL
- 5: RLR
- 6: RRL
- 7: RRR

Notice a pattern? It is simply the binary representation of the index. So for any index, we start with the most significant bit and iterate through to the least significant bit. For each 0 bit, move to the nodes left child. For each 1 bit, move to the nodes right child. Now, we can see why this is superior to a linked list. To get the 8th element in a linked list, we would have to traverse through 8 nodes. However, with the binary tree, we only have to go through 3. It only gets better for larger lists. For a list of 1 million elements, the binary tree only requires 20 node traversals to get any element, but for a linked list we may have to go through a million nodes! 

The one downside to this strategy is that it is not as easy to go from one element to the next. With a linked list, if you had one node, it is trivial to get the next. But with this structure, you would have to traverse up the tree until the next node shares an ancestor with the current one, and then you have to traverse back down. This is doable, but a much simpler approach is to use the linked list concept in our tree. We still have the same tree setup, but each node on the bottom layer has a reference to the next and previous node. That way it is possible to iterate through the elements very easily, like you would with a linked list, but it is still effiecient to retrieve an element by an index.

### ListNode&lt;T>

We start by creating a class to represent each node. Each node contains an element, and two children, each of which is also a node. All of these are optional, however, when we create our List class, we will enforce the rule that each node will have either an element, or both children, but cannot have an element and children.

    class ListNode<T>(var element: T? = null, leftChild: ListNode<T>? = null, rightChild: ListNode<T>? = null ) {
        ...
    }

I have not specified the left and right children as instance variables, since it is convenient to have a custom setter for them to ensure the parent is always set correctly

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

    init {
        this.rightChild = rightChild
        this.leftChild = leftChild
    }

As mentioned earlier, we also want references to the next and previous nodes to make iteration efficient. Again, we have custom setters to make sure that every time we set the next node, the previous node on the other node is set accordingly. In this case, we need private backing variables to ensure we do not get in an infinite loop setting the next/previous nodes

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

Each node can contain many children. Starting from a given node, you can move through the children until you reach the bottom layer (indicated by null children, or alternatively, a non null element). This bottom layer contains the elements in the list. When it comes to creating the List class, we will find it convenient to have access to the first and last nodes that are children of a given node. That is, for some node, a reference to the left most and right most children of the node on the bottom layer of the tree. It is fairly straight forward to create getters for these using recursion. If the node has no children, it is both the first and last node. Otherwise, a nodes first child is the same as the left childs first node, and an analogous statement can be said of the last node

    val firstNode: ListNode<T> get() = leftChild?.firstNode ?: this
    val lastNode: ListNode<T> get() = rightChild?.firstNode ?: this

One final thing required for the node is the ability to make a copy of it. This will be needed when it comes to resizing a list. Again, this is fairly straight forward using recursion, simply be creating a new node with the same element, and setting the left and right children to be copies of the existing nodes left and right children. The only thing we need to be careful about is ensuring the next and previous node references are all set correctly. Assuming the left and right children are copied properly (next and previous references set), there will only be one link that needs to be set. The link from the last node on the left side, to the first node on the right side. It turns out, these are the only nodes we need to link in this method. All the other nodes will be properly linked in recursive calls to copy. One thing to note here is that we are not copying the elements. If the elements are mutable objects, this could in theory pose a problem. However, due to the way this method is going to be used in the List class, this will not be an issue, as all the duplicated elements will ultimately be replaced with another object

    fun copy() : ListNode<T> {
        val newNode = ListNode(element)
        newNode.leftChild = leftChild?.copy()
        newNode.rightChild = rightChild?.copy()
        newNode.leftChild?.lastNode?.nextNode = newNode.rightChild?.firstNode
        return newNode
    }
    
### List&lt;T>
