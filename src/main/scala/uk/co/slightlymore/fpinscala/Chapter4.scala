package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.datastructures.option._

object Chapter4 {
  def mean(xs: Seq[Double]): Option[Double] = if (xs.isEmpty) None else Some(xs.sum / xs.length)
  
  def variance(xs: Seq[Double]): Option[Double] = mean(xs).flatMap(x_bar => mean(xs.map(x => math.pow(x - x_bar, 2))))
  
  def main(args: Array[String]): Unit = {
    def add = (x: Int) => x + 1
    println(Option.lift(add)(Some(1)))
  }
}