package uk.co.slightlymore.fpinscala.datastructures.option

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(v) => Some(f(v))
  }

  def flatMap[B](f: A => Option[B]): Option[B] = this.map(f).getOrElse(None)
  
  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(v) => v
  }

  def orElse[B >: A](ob: => Option[B]): Option[B] = this.map(x => Some(x)).getOrElse(ob)

  def filter(f: A => Boolean): Option[A] = this.flatMap(x => if (f(x)) Some(x) else None)
  
  // first attempt
  def map2_using_match[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = (a, b) match {
    case (Some(aa), Some(bb)) => Some(f(aa, bb))
    case _ => None
  }
  
  // second attempt - presumably better (more FP) because it's using flatMap? 
  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = a.flatMap(a_ => b.map(b_ => f(a_, b_)))
}
case class Some[A](value: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def lift[A, B](f: A => B): Option[A] => Option[B] = (a: Option[A]) => a.map(f) // or _.map(f)
}