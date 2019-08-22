package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.datastructures._

object Chapter3 {
  def main(args: Array[String]): Unit = {
    val intList: List[Int] = List(1, 2, 3)
    println(List.sum(intList))
    
    val doubleList: List[Double] = List(1, 2, 3)
    println(List.product(doubleList))
    
    // because List[+A] is covariant, Nil can be a list of any type,
    // so has all methods
    val nilList = Nil
    println(List.sum(nilList))
    println(List.product(nilList))
  }
}