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
  
  def tail[A](xs: List[A]): List[A] = xs match {
    case Nil => Nil
    case Cons(_, xs) => xs
  }

  def setHead[A](head: A, xs: List[A]): List[A] = xs match {
    case Nil => Nil
    case Cons(_, xs) => Cons(head, xs)
  }
  
  // ex 3.4
  def drop[A](l: List[A], n: Int): List[A] = (l, n) match {
    case (Nil, _) => Nil
    case (_, 0) => l
    case (Cons(x, xs), n) => List.drop(xs, n - 1)
  }
  
  // the * here indicates variadic argument. It's treated as Seq[A]
  def apply[A](as: A*):  List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}