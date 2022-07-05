# Math Challenge
A Math library built entirely from booleans

The goal of this project is to create a library of basic math functions using absolutely no types or classes, except the ones I define. 
This means even primitive types and arrays are forbidden, as are all operators, except reference equality (`===`). Arrays are also not allowed, 
so collections must be built from scratch without any preexisting functionalty.

At the moment, I am not certain if this is even possible to do without arrays, but I have ideas about how to achieve it so I believe that this will be possible

## Rules:
  - No types are allows except the ones I define. This excludes everything, even primitive types. There are two exceptions to this 
    - The `toString` method is allowed to use `Char` and `String` internally, and is allowed to return a `String`. `toString` is only allowed to 
    be called from other `toString` methods, or when printing an object
    - Kotlin Boolean primitives can be implicitly created in condition and loop statements, as Kotlin does not allow use of if and while statements 
    without a primitive `Boolean` as the condition. All if/while statements should be of the form `if/while(<Boolean expression> === True)` or
    `if/while(<Boolean expression> === False)`
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
    
