package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.datastructures.option._

object Chapter4 {
  def mean(xs: Seq[Double]): Option[Double] = if (xs.isEmpty) None else Some(xs.sum / xs.length)
  
  def main(args: Array[String]): Unit = {
    
  }
}