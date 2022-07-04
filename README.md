# Math Challenge
A Math library built entirely from booleans

The goal of this project is to create a library of basic math functions using no types except `Boolean`, 
and using no external classes including all built in Kotlin classes. Kotlin interfaces are allowed, as these don't provide new funtionality,
they just force you to conform to the interface. Arrays are also not allowed, so collections must be built from scratch without any preexisting 
functionalty.

At the moment, I am not certain if this is even possible to do without arrays, but I have ideas about how to achieve it so I believe that this will be possible

## Rules:
  - The only type allowed is `Boolean`. This means no `Array`, no `Int`, no `Double`, etc. 
  - There is one exception to this. The `toString` method is allowed to use `Char` and `String` internally, and is allowed to return a `String`. 
  `toString` is only allowed to be called from other `toString` methods, or when printing an object. Thus, exluding printing, the only type 
  used will be `Boolean`
  - No external classes are allowed to be used, including built in Kotlin classes. No builtin functions are allows to be used, except operators
  - Interfaces are allowed

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
  - There is no requirement on speed. Due to the complexity of implementing all of this with only `Boolean` value, it is not expected to be
  performant, however it must be precise and accurate
    
