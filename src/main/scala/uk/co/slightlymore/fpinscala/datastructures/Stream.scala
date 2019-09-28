package main.scala.uk.co.slightlymore.fpinscala.datastructures

import scala.annotation.tailrec

sealed trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h()) // we have to evaluate the thunk to get the value for Some
  }
}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  // A convention is that "smart constructors" are lower cased version of the type name.
  // It's smart because it's slightly different signature to the "real" Cons used in 
  // pattern matching. Here it's memoizing the by-name arguments
  def cons[A](head: => A, tail: => Stream[A]): Stream[A] = {
    lazy val h = head // memoize the args as lazy values to avoid repeated evaluation
    lazy val t = tail
    Cons(() => h, () => t) // must provide explicit thunks due to scala limitations
  }
  
  // this smart constructor does nothing fancy, but annotates Empty as Stream[A] 
  // for better type inference
  def empty[A]: Stream[A] = Empty
  
  def apply[A](as: A*): Stream[A] =
    // note that thunks are created - () => as.head and () => apply(as.tail) for call by name in cons
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
}