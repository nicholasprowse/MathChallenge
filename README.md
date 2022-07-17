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
  
Checkout the [wiki](https://github.com/nicholasprowse/MathChallenge/wiki/Math-Challenge) to see how I've done it
