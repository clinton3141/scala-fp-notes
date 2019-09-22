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
    
    // should be List("new head", "tail1", "tail2")
    println(List.setHead("new head", List("old head", "tail1", "tail2")))
    
    // should be Nil because we can't replace a head which doesn't exist
    println(List.setHead("new head", List()))
    
    // ex 3.4
    println("Ex 3.4");
    println(List.drop(Nil, 5)) // should be Nil
    println(List.drop(List(1), 2)) // should be Nil
    println(List.drop(List(1, 2, 3), 2)) // should be List(3)
    println(List.drop(List(1, 2, 3), 3)) // should be Nil
    
    // ex 3.5
    println("Ex 3.5");
    println(List.dropWhile(List(), (x: Int) => x > 2)) // should be Nil
    println(List.dropWhile(List(1,2,3,4), (x: Int) => x > 2)) // should be List(1, 2, 3, 4)
    println(List.dropWhile(List(1,2,3,4), (x: Int) => x <= 2)) // should be List(3, 4)
    println(List.dropWhile2(List(1, 2, 3, 4))(_ > 2))
    
    println("Ex 3.6")
    println(List.init(List(1))) // should be Nil
    println(List.init(List(1, 2))) // should be List(1)
    println(List.init(List(1, 2, 3))) // should be List(1, 2)
    
    println("Ex 3.8")
    // calling with Nil and Cons returns the original list
    println(List.foldRight(List(1, 2, 3), Nil:List[Int])(Cons(_, _)))
  }
}