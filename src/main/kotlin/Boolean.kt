class Boolean private constructor() {
    companion object {
        val True = Boolean()
        val False = Boolean()
    }

    infix fun or(other: Boolean) : Boolean {
        if (this === True) {
            return True
        }
        if (other === True) {
            return True
        }
        return False
    }

    infix fun and(other: Boolean) : Boolean {
        if (this === True) {
            if (other === True) {
                return True
            }
        }
        return False
    }

    infix fun xor(other: Boolean) : Boolean {
        if (this === other) {
            return False
        }
        return True
    }

    operator fun not() : Boolean {
        if (this === True) {
            return False
        }
        return True
    }

    override fun toString(): String {
        return if (this === True) "true" else "false"
    }
}
