abstract class Number {
    abstract operator fun unaryMinus(): Number
    abstract operator fun plus(n: Number): Number
    abstract operator fun inc(): Number
    abstract operator fun minus(n: Number): Number
    abstract operator fun dec(): Number
    abstract operator fun times(n: Number): Number
    abstract operator fun div(n: Number): Number
    abstract operator fun rem(n: Number): Number
    abstract infix fun equals(n: Number?) : Boolean
    abstract operator fun invoke(n: Number) : Number
    abstract fun compareTo(n: Number): ComparisonResult

    infix fun notEquals(n: Number?) : Boolean {
        return !equals(n)
    }

    operator fun rangeTo(n: Number) : List<Number> {
        var i = this
        val list = List<Number>()
        while (i lessThanOrEqualTo n === True) {
            list.push(i)
            i++
        }
        return list
    }

    infix fun until(other: Number) : List<Number> {
        var i = this
        val list = List<Number>()
        while (i lessThan other === True) {
            list.push(i)
            i++
        }
        return list
    }

    infix fun lessThan(other: Number): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) True else False
    }

    infix fun greaterThan(other: Number): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) True else False
    }

    infix fun lessThanOrEqualTo(other: Number): Boolean {
        return if (this.compareTo(other) === ComparisonResult.GREATER) False else True
    }

    infix fun greaterThanOrEqualTo(other: Number): Boolean {
        return if (this.compareTo(other) === ComparisonResult.LESS) False else True
    }


    // This is needed to suppress a warning about the equals method.
    // It is not used as it violates the rules of the challenge
    override fun equals(other: Any?): kotlin.Boolean { throw Exception() }
    override fun hashCode(): kotlin.Int { throw Exception() }
}