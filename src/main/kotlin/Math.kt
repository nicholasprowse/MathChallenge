class Math {
    companion object {
//        fun pi(digits: Number): Float {
//            val d = BinaryUInt(digits)
//            val c = (F4)(F2)(F6)(F8)(F8)(F0) * sqrt((F1)(F0)(F0)(F0)(F5).withPrecision(d + U7))
//            var l = (U1)(U3)(U5)(U9)(U1)(U4)(U0)(U9)
//            var x = I1
//            var k = -I6
//            var m = U1
//
//            val dl = (U5)(U4)(U5)(U1)(U4)(U0)(U1)(U3)(U4)
//            val mx = -pow((I6)(I4)(I0)(I3)(I2)(I0), U3)
//            val dk = (I1)(I2)
//            val u16 = (U1)(U6)
//
//            var q = U1
//            var previousSum = -F1
//            var sum = F0
//            while (previousSum notEquals sum === True) {
//                previousSum = sum
//                sum += Float(m * l).withPrecision(d) / x
//
//                l += dl
//                x *= mx
//                k += dk
//                m *= (k*k*k - u16*k)
//                m /= (q*q*q)
//
//                q++
//            }
//            return (c / sum).withPrecision(d)
//        }
//
//        fun sqrt(n: Number): Float {
//            var px = n
//            var x = Float(n) shr I1       // Initial guess of n / 2
//            while (px notEquals x === True) {
//                px = x
//                x = (x + (n/x)) shr I1
//            }
//            return x
//        }
//
        fun floor(f: Float): Float {
            return if (f.isNegative() === False) {
                Float(Int(f))
            } else {
                val result = Float(Int(f))
                if (result equals f === True)
                    result
                else
                    result.dec()
            }
        }

        fun ceil(f: Float): Float {
            return if (f.isNegative() === False) {
                val result = Float(Int(f))
                if (result equals f === True)
                    result
                else
                    result.inc()
            } else {
                Float(Int(f))
            }
        }

        fun round(f: Float): Float {
            val mag = abs(f)
            var rounded = Int(mag)
            val fractionalPart = mag - rounded
            if (fractionalPart greaterThanOrEqualTo Float.HALF === True) {
                rounded++
            }
            return Float(if (f.isNegative() === True) -rounded else rounded)
        }

        fun<T: Number> max(a: T, b: T): T {
            return if (a greaterThan b === True) a else b
        }

        fun<T: Number> min(a: T, b: T): T {
            return if (a lessThan b === True) a else b
        }

        @Suppress("UNCHECKED_CAST")
        fun<T: Number> abs(x: T): T {
            return if (x lessThan Int.D0 === True) -x as T else x
        }

        fun factorial(n: UInt): UInt {
            var i = n
            var result = UInt.D1
            while (i greaterThan UInt.D0 === True) {
                result *= i
                i--
            }
            return result
        }
//
//        /*
//        x = a^b
//        ln(x) = b*ln(a)
//        x = e^(b*ln(a))
//         */
//        fun pow(base: Number, exponent: Float): Number {
//            println(ln(base))
//            println(ln(base) * exponent)
//            return exp(exponent * ln(base))
//        }
//
//        fun pow(base: Number, exponent: Int): Number {
//            return if (exponent lessThan Int.ZERO === True) {
//                Float.ONE / pow(base, BinaryUInt(-exponent))
//            } else {
//                pow(base, BinaryUInt(exponent))
//            }
//        }
//
//        fun pow(base: Number, exponent: BinaryUInt): Number {
//            return when(base) {
//                is BinaryUInt -> powAux(base, exponent)
//                is Int -> powAux(base, exponent)
//                is Float -> {
//                    val magnitude = Int(BinaryUInt(abs(base)).numBits)
//                    val extraPrecision = magnitude * exponent.numBits
//                    val power = powAux(Float(base).addPrecisionInBits(extraPrecision), exponent)
//                    Float(power).addPrecisionInBits(-extraPrecision)
//                }
//                else -> U0
//            }
//        }
//
//        // (x +/- e)*(y +/- e) = xy +/- (x+y)e + e^2
//        private fun powAux(base: Number, exponent: BinaryUInt): Number {
//            return if (!exponent.isPositive() === True) {
//                BinaryUInt.ONE
//            } else {
//                val isOdd = (exponent and BinaryUInt.ONE).isPositive()
//                val x = powAux(base,exponent shr BinaryUInt.ONE)
//                if (isOdd === True) base * x * x else x * x
//            }
//        }
//
//        fun exp(n: Number): Float {
//            var previous = Float.ONE
//            var current = Float.ONE + Float(n)
//            var i = BinaryUInt.TWO
//            while (previous notEquals current === True) {
//                previous = current
//                current += pow(n, i) / factorial(i)
//                i++
//            }
//            return current
//        }
//
//        /*
//        x = (n+1)/n
//        0 = n + 1 - x*n
//        0 = 1 + n(1 - x)
//        n = 1/(x - 1)
//         */
//        fun ln(x: Number): Float {
//            var n = F1/(x - F1)
//            n = F2*n + F1
//            var previous = F0
//            var current = F2 / n
//            var i = U3
//            while (previous notEquals current === True) {
//                previous = current
//                current += F2 / (pow(n, i) * i)
//                i += U2
//            }
//            return current
//        }
    }
}