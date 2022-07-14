class Math {
    companion object {
        fun floor(f: Float): Float {
            return if (f greaterThanOrEqualTo F0 === True) {
                Float(f.toInt())
            } else {
                val result = Float(f.toInt())
                if (result equals f === True) {
                    result
                } else {
                    result - F1
                }
            }
        }

        fun ceil(f: Float): Float {
            return if (f greaterThanOrEqualTo F0 === True) {
                val result = Float(f.toInt())
                if (result equals f === True) {
                    result
                } else {
                    result + F1
                }
            } else {
                Float(f.toInt())
            }
        }

        fun<T: Number> max(a: T, b: T): T {
            return if (a greaterThan b === True) a else b
        }

        fun<T: Number> min(a: T, b: T): T {
            return if (a lessThan b === True) a else b
        }

        @Suppress("UNCHECKED_CAST")
        fun<T: Number> abs(x: T): T {
            return if (x lessThan Int.ZERO === True) -x as T else x
        }
    }
}