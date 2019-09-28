package main.scala.uk.co.slightlymore.fpinscala.datastructures

import scala.annotation.tailrec

// TODO: why is this needed here? It seems to be importing object Stream, meaning that 
// `cons` and `empty` are available as methods within the Stream trait. What is going 
// on under the hood?
import Stream._

sealed trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h()) // we have to evaluate the thunk to get the value for Some
  }

  def toListNaive: List[A] = this match {
    case Empty => Nil
    case Cons(h, t) => h() :: (t().toListNaive)
  }

  def toList: List[A] = {
    @tailrec
    def loop(next: Stream[A], acc: List[A]): List[A] = {
      next match {
        case Cons(h, t) => loop(t(), h() :: acc) 
        case _ => acc
      }
    }

    // loop builds the list in reverse, so need to reverse again
    loop(this, Nil).reverse
  }

  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if n > 0 => cons(h(), t() take(n - 1))
    case _ => empty
  }

  // this is tail recursive, but requires to be final so that it cannot be overridden
  @tailrec
  final def drop(n: Int): Stream[A] = this match {
    case Cons(_, t) if n > 0 => t() drop(n - 1)
    case _ => this
  }

  final def takeWhile(f: A => Boolean): Stream[A] = this match {
    case Cons(h, t) if f(h()) => cons(h(), t() takeWhile(f))
    case _ => empty
  }
  
  def exists(p: A => Boolean): Boolean = this match {
    case Cons(h, t) => p(h()) || t().exists(p)
    case _ => false
  }
  
  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h, t) => f(h(), t().foldRight(z)(f)) // because the second arg is by name, it is only evaluated if required
    case _ => z
  }
  
  def forAll(p: A => Boolean): Boolean = 
    foldRight(true)((next, acc) => acc && p(next))
  
  def forAll2(p: A => Boolean): Boolean = exists(!p(_))
  
  def takeWhileViaFoldRight(f: A => Boolean): Stream[A] = 
    foldRight(empty: Stream[A])((next, acc) => if (f(next)) cons(next, acc) else empty)
    
  def headOptionViaFoldRight: Option[A] =
    foldRight(None: Option[A])((next, _) => Some(next))
    
  def map[B](f: A => B): Stream[B] =
    foldRight(empty[B])((h, t) => cons(f(h), t))
  
  def filter(f: A => Boolean): Stream[A] =
    foldRight(empty[A])((h, t) => if (f(h)) cons(h, t) else t)
  
  def append[AA >: A](a: => Stream[AA]): Stream[AA] =
    foldRight(a)(cons(_, _))
  
  def flatMap[B](f: A => Stream[B]): Stream[B] =
    foldRight(empty[B])(f(_) append _)
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