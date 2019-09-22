package uk.co.slightlymore.fpinscala.datastructures
import scala.annotation.tailrec

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
  
  // ex 3.5
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(x, xs) => if (f(x)) List.dropWhile(xs, f) else l
    
    // alternatively we can use a pattern guard here. `case PATTERN if(CONDITION) => result`
    // TODO: this feels more idiomatic - is it? 
    // case Cons(x, xs) if (f(x)) => List.dropWhile(xs, f)
    // case _ => l 
  }
  
  // We can create a curried function which then helps the compiler with type inference
  // List.dropWhile2(List(1, 2))(x => x > 2)
  // or List.dropWhile2(List(1, 2))(_ > 2)
  // whereas originally: List.dropWhile(List(1, 2), (x: Int) => x > 2)
  def dropWhile2[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Cons(x, xs) if f(x) => dropWhile2(xs)(f)
    case _ => l
  }
  
  
  def append[A](a1: List[A], a2: List[A]): List[A] = a1 match {
    case Nil => a2
    case Cons(x, xs) => Cons(x, append(xs, a2)) 
  }
  
  // ex 3.6
  // (naive recursive method)
  def init[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(x, Nil) => Nil
    case Cons(x, xs) => Cons(x, init(xs))
  }
  
  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = as match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f)) 
  }
  
  @tailrec
  def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = as match {
    case Nil => z
    case Cons(x, xs) => foldLeft(xs, f(z, x))(f)
  }
  
  def length[A](as: List[A]): Int =
    foldRight(as, 0)((_, acc) => acc + 1)

  // the * here indicates variadic argument. It's treated as Seq[A]
  def apply[A](as: A*):  List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}