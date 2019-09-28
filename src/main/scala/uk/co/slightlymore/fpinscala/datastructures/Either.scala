package main.scala.uk.co.slightlymore.fpinscala.datastructures

sealed trait Either[+E, +A] {
  def map[B](f: A => B): Either[E, B] = this match {
    case Left(l) => Left(l)
    case Right(r) => Right(f(r))
  }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
    case Left(l) => Left(l)
    case Right(r) => f(r)
  }

  def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B] = this match {
    case Right(_) => this
    case _ => b
  }

  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = this.flatMap(a => b.map(f(a, _)))
  
  def map2_comprehension[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = 
    for {
      a <- this
      bb <- b
    } yield(f(a, bb))
}

case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[Nothing, A]

object Either {
  def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] = ???
  def traverse[E, A, B](as: List[A])(f: A => Either[E, B]): Either[E, List[B]] = as match {
    case Nil => Right(Nil)
  		// case h :: t => f(h).flatMap(head => traverse(t)(f).map(tail => head :: tail))
    // Note: the above flatMap => traverse... is equivalent to map2. Watch out for these! So:
    case h :: t => f(h).map2(traverse(t)(f))((head, tail) => head :: tail)
  }

  def Try[A](a: => A): Either[Exception, A] = try { Right(a) } catch { case e: Exception => Left(e) }  
}