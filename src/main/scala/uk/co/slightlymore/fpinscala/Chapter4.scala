package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.datastructures.{Option, Some, None}
import uk.co.slightlymore.fpinscala.datastructures.{List => CustomList}

object Chapter4 {
  def mean(xs: Seq[Double]): Option[Double] = if (xs.isEmpty) None else Some(xs.sum / xs.length)
  
  def variance(xs: Seq[Double]): Option[Double] = mean(xs).flatMap(x_bar => mean(xs.map(x => math.pow(x - x_bar, 2))))
  
  def main(args: Array[String]): Unit = {
    def add = (x: Int) => x + 1
    println(Option.lift(add)(Some(1)))
    
    println(Option.sequenceCustom(CustomList(Some(1), Some(2))))
    println(Option.sequenceCustom(CustomList(Some(1), None, Some(3))))
    
    println("Sequence")
    println(Option.sequence(List(Some(1), Some(2))))
    println(Option.sequence(List(Some(1), None, Some(3))))
    println(Option.sequence(List()))
    println(Option.sequence(Nil))

    println("traverse")
    println(Option.traverse(List(1, 2))(x => Some(1 + x)))
    println(Option.traverse(List(1, 2))(x => Some(1 + x)))
    
    println("sequence via traverse")
    println(Option.sequenceViaTraverse(List(Some(1), None)))
    println(Option.sequenceViaTraverse(List(Some(1), Some(2))))
  }
}