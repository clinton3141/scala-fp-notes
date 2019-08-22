package uk.co.slightlymore.fpinscala.datastructures

// sealed trait means that implementations can only be declared in this file 
sealed trait List[+A]

// this is possible because A is covariant. Because Nothing is a subtype of everything,
// List[Nothing] is a subtype of List[A]. 
case object Nil extends List[Nothing] 
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }
  
  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(d, ds) => d * product(ds)
  }
  
  def apply[A](as: A*):  List[A] = 
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}