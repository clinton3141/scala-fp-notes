package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.datastructures.option._

object Chapter4 {
  def mean(xs: Seq[Double]): Option[Double] = if (xs.isEmpty) None else Some(xs.sum / xs.length)
  
  def variance(xs: Seq[Double]): Option[Double] = mean(xs).flatMap(x_bar => mean(xs.map(x => math.pow(x - x_bar, 2))))
  
  def lift[A, B](f: A => B): Option[A] => Option[B] = (a: Option[A]) => a.map(f) // or _.map(f)
  
  def main(args: Array[String]): Unit = {
    def add = (x: Int) => x + 1
    println(lift(add)(Some(1)))
  }
}