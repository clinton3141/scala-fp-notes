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
  
  def sumL(l: List[Int]): Int = foldLeft(l, 0)((acc: Int, x: Int) => acc + x)
  
  def productL(l: List[Int]): Int = foldLeft(l, 1)((acc: Int, x: Int) => acc * x)
  
  def lengthL(l: List[Int]): Int = foldLeft(l, 0)((acc: Int, _) => acc + 1)
  
  def length[A](as: List[A]): Int =
    foldRight(as, 0)((_, acc) => acc + 1)
    
  def reverse[A](l: List[A]): List[A] = 
    foldLeft(l, Nil: List[A])((acc: List[A], x: A) => Cons(x, acc))
    
  def foldRightUsingLeft[A, B](l: List[A], z: B)(f: (A, B) => B): B = 
    foldLeft(reverse(l), z)((acc, x) => f(x, acc))
    
  def append[A](l: List[A], a: A): List[A] = 
    foldRight(l, List(a))((acc, x) => Cons(acc, x))
    
  def concat[A](ls: List[List[A]]): List[A] =
    foldRight(ls, List[A]())(append)
    
  def add1(l: List[Int]): List[Int] = 
    foldRight(l, List[Int]())((i, acc) => Cons(i + 1, acc))
    
  def doubleToString(l: List[Double]): List[String] = 
    foldRight(l, List[String]())((d, acc) => Cons(d.toString(), acc))
    
  def map[A, B](l: List[A])(f: A => B): List[B] =
    foldRight(l, List[B]())((x, acc) => Cons(f(x), acc))

  def filter[A](l: List[A])(f: A => Boolean): List[A] = 
    foldRight(l, List[A]())((x, acc) => f(x) match {
      case false => acc
      case _ => Cons(x, acc)
    })
    
  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] =
    concat(map(as)(f))
    // equivalently
    //    foldRight(map(as)(f), List[B]())(append)
    
  def filterViaFlatMap[A](ls: List[A])(f: A => Boolean): List[A] = 
    flatMap(ls)(l => if(f(l)) List(l) else Nil)
  
  def addListsPairwise(as: List[Int], bs: List[Int]): List[Int] = (as, bs) match {
    case (_, Nil) => Nil
    case (Nil, _) => Nil
    case (Cons(x, xs), Cons(y, ys)) => Cons(x + y, addListsPairwise(xs, ys))
  }
  
  // question asked to generalise addListsPairwise as zipWith. No mention of how far to
  // take generalisation so I've gone all the way with 3 type parameters
  def zipWith[A, B, C](as: List[A], bs: List[B])(f: (A, B) => C): List[C] = (as, bs) match {
    case (_, Nil) => Nil
    case (Nil, _) => Nil
    case (Cons(x, xs), Cons(y, ys)) => Cons(f(x, y), zipWith(xs, ys)(f))
  }
  
  @tailrec
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = {
    @tailrec
    def startsWith(list: List[A], prefix: List[A]): Boolean = (list, prefix) match { 
      case (_, Nil) => true
      case (Cons(fh, ft), Cons(ph, pt)) => fh == ph && startsWith(ft, pt)
      case _ => false
    }
    sup match {
      case Nil => false
      case _ if startsWith(sup, sub) => true
      case Cons (x, xs) => hasSubsequence(xs, sub)
    }
  }
    

  // the * here indicates variadic argument. It's treated as Seq[A]
  def apply[A](as: A*):  List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}