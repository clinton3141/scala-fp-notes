package uk.co.slightlymore.fpinscala

import scala.annotation.tailrec

object Chapter2 {

  def abs(x: Int) = if (x < 0) -x else x
  
  def formatResult(name: String, n: Int, f: Int => Int) = {
    val msg = "The %s of %d is %d"
    msg.format(name, n, f(n))
  }
  
  def factorial(n: Int): Int = {
    @tailrec
    def go(n: Int, acc: Int): Int = {
      if(n == 1) acc
      else go(n - 1, n * acc)
    }
    go(n, 1)
  }
  
  // ex 2.1
  def fib(n: Int): Int = {
     @tailrec
     def go(i: Int, acc: Int, prev: Int): Int =
       if (i == n) acc
       else go(i + 1, acc + prev, acc)
    go(0, 0, 1)
  }
  
  // ex 2.2
  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    @tailrec
    def loop(i: Int): Boolean = {
      if (i >= as.size - 1) true
      else if (!ordered(as(i), as(i + 1))) false
      else loop(i + 1)
    }
    loop(0)
  }
  
  // section 2.6 Types puzzle
  def partial1[A, B, C](a: A, f: (A,B) => C): B => C =
    b => f(a, b)
  
  // ex 2.3
  def curry[A, B, C](f: (A, B) => C): A => (B => C) =
    a => b => f(a, b)
  
  // ex 2.4
  def uncurry[A, B, C](f: A => B => C): (A, B) => C =
    (a, b) => f(a)(b)
  
  // ex 2.5. Note that scale provides `andThen` and `compose`, so `g andThen f` and `f compose g`
  def compose[A, B, C](f: B => C, g: A => B): A => C =
    a => f(g(a))
  
  def main(args: Array[String]): Unit = {
    println(formatResult("fibonacci", 12, fib))
    println(formatResult("factorial", 7, factorial))
    println(formatResult("absolute value", -12, abs))
    println(isSorted(Array(1), (x: Int, y: Int) => x < y))
    println(isSorted(Array(1, 2, 3, 4), (x: Int, y: Int) => x < y))
    println(isSorted(Array(2, 1), (x: Int, y: Int) => x < y))
    println(isSorted(Array(1, 2, 3, 2), (x: Int, y: Int) => x < y))
  }
}