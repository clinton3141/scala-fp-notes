package uk.co.slightlymore.fpinscala.datastructures

import scala.annotation.tailrec

sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  def size[A](tree: Tree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(left, right) => 1 + size(left) + size(right)
  }
  
  def maximum(tree: Tree[Int]): Int = tree match {
    case Leaf(x) => x
    case Branch(Leaf(x), Leaf(y)) => x max y
    case Branch(Leaf(x), right) => x max maximum(right)
    case Branch(left, Leaf(y)) => y max maximum(left)
    case Branch(left, right) => maximum(left) max maximum(right)
  }
  
  def depth[A](tree: Tree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(left, right) => 1 + (depth(left) max depth(right))
  }
  
  def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
    case Leaf(x) => Leaf(f(x))
    case Branch(left, right) => Branch(map(left)(f), map(right)(f))
  }
  
  def fold[A, B](tree: Tree[A])(f: A => B)(g: (B, B) => B): B = tree match {
    case Leaf(x) => f(x)
    case Branch(left, right) => g(fold(left)(f)(g), fold(right)(f)(g))
  }
  
  def size2[A](tree: Tree[A]): Int =
    fold(tree)(l => 1)((left, right) => 1 + left + right)
    
  def depth2[A](tree: Tree[A]): Int =
    fold(tree)(l => 1)((left, right) => 1 + (left max right))
   
  def maximum2(tree: Tree[Int]): Int =
    fold(tree)(l => l)((left, right) => left max right)
    
  def map2[A, B](tree: Tree[A])(f: A => B): Tree[B] =
    fold(tree)(l => Leaf(f(l)): Tree[B])((left, right) => Branch(left, right))
}
