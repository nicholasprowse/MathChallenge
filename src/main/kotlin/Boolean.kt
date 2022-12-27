enum class Boolean {
    True, False;
//    companion object {
//        val  True = Boolean()
//        val False = Boolean()
//    }

    infix fun or(other: Boolean) = if (this === True) True else other
    infix fun and(other: Boolean) = if (this === True) other else False
    infix fun xor(other: Boolean) = if (this === other) False else True
    operator fun not() = if (this === True) False else True
    override fun toString() = if (this === True) "true" else "false"
}
