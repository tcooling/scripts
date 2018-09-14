/**
 * @const _pipe - Given two functions, f and g and curried arguments (...args), pass
 * the result of f(...args) into g, returning the resulting function
 *
 * @param {function} f - The first function
 * @param {function} g - The second function
 *
 * @return {function} - Return a function composition of calling g with the result
 * of f(...args)
 */
const _pipe = (f, g) => (...args) => g(f(...args));

/**
 * @const pipe - Given any number of functions, run those functions on a curried
 * input.
 *
 * e.g. pipe(addOne, multiplyBy5)(2)
 *
 * The above will return 15 (assuming addOne and multiplyBy5 are implemented)
 *
 * @param {function} fns - Any number of functions
 *
 * @return {Any} - The result of running the provided functions on an argument
 */
const pipe = (...fns) => fns.reduce(_pipe);