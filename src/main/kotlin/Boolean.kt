class Boolean private constructor() {
    companion object {
        val True = Boolean()
        val False = Boolean()
    }

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
}
