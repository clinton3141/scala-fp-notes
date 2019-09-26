package uk.co.slightlymore.fpinscala.datastructures.option

import uk.co.slightlymore.fpinscala.datastructures.{List => CustomList} 

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(v) => Some(f(v))
  }

  def flatMap[B](f: A => Option[B]): Option[B] = map(f).getOrElse(None)
  
  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(v) => v
  }

  def orElse[B >: A](ob: => Option[B]): Option[B] = map(x => Some(x)).getOrElse(ob)

  def filter(f: A => Boolean): Option[A] = flatMap(x => if (f(x)) Some(x) else None)
}
case class Some[A](value: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def lift[A, B](f: A => B): Option[A] => Option[B] = (a: Option[A]) => a.map(f) // or _.map(f)

  def sequenceCustom[A](a: CustomList[Option[A]]): Option[CustomList[A]] = {
    CustomList.foldLeft(a, Some(CustomList[A]()): Option[CustomList[A]])((acc, option) => option match {
      case None => None
      case Some(x) => acc.map(xs => CustomList.append(xs, x)) 
    });
  }
  
  // first attempt
  def map2_using_match[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = (a, b) match {
    case (Some(aa), Some(bb)) => Some(f(aa, bb))
    case _ => None
  }
  
  // second attempt - presumably better (more FP) because it's using flatMap? 
  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = a.flatMap(a_ => b.map(b_ => f(a_, b_)))

  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
//    a.foldRight(Some(Nil): Option[List[A]])((option, acc) => map2(option, acc)((h, t) => h :: t))
    a.foldRight(Some(Nil): Option[List[A]])(map2(_, _)(_ :: _))
  }

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = a match {
    case Nil => Some(Nil)
//    case h :: t => map2(f(h), traverse(t)(f))((h, t) => h :: t)
    case h :: t => map2(f(h), traverse(t)(f))(_ :: _)
  }
  
  def sequenceViaTraverse[A](a: List[Option[A]]): Option[List[A]] = traverse(a)(x => x)
}