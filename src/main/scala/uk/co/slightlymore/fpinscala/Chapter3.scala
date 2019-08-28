package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.datastructures._

object Chapter3 {
  def main(args: Array[String]): Unit = {
    val intList: List[Int] = List(1, 2, 3)
    println(List.sum(intList))
    
    val doubleList: List[Double] = List(1, 2, 3)
    println(List.product(doubleList))
    
    // because List[+A] is covariant, Nil can be a list of any type,
    // so has all methods
    val nilList = Nil
    println(List.sum(nilList))
    println(List.product(nilList))
    
    // ex 3.1: what's the output?
    val x = List(1, 2, 3, 4, 5) match {
      case Cons(x, Cons(2, Cons(4, _))) => x // no match - missing 3
      case Nil => 42 // no match - not nil
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y // match - x = 1, y = 2, _ = Cons(5, Nil)
      case Cons(h, t) => h + List.sum(t) // no match - already matched. But would do so if no previous match
      case _ => 101 // no match - already matched. But would do so if no previous match
    }
    println(x)
    
    val x3_2 = List(1, 2, 3)
    println(List.tail(x3_2))
  }
}