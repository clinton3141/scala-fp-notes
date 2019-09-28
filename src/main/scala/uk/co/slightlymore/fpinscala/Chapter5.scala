package uk.co.slightlymore.fpinscala

import main.scala.uk.co.slightlymore.fpinscala.datastructures.Stream

object Chapter5 {
  def main(args: Array[String]): Unit = {
    println(Stream(1, 2, 3, 4) take(0) toList)
    println(Stream(1) take(1) toList)
    println(Stream(1, 2, 3, 4, 5) take(2) toList)
    
    println(Stream(1, 2, 3, 4, 5) drop(2) toList)
    
    println("takeWhile")
    println(Stream(1, 2, 3, 4, 5).takeWhile(_ < 3).toList)
    println(Stream(1, 2, 3, 4, 5).takeWhileViaFoldRight(_ < 3).toList)
    println(Stream(1, 2, 3, 4, 5).takeWhile(_ > 3).toList)
    println(Stream(1, 2, 3, 4, 5).takeWhileViaFoldRight(_ > 3).toList)
    
    println(Stream(1, 2, 3, 4, 5).forAll(_ > 0))
    println(Stream(1, 2, 3, 4, 5).forAll(_ < 0))
    
    println(Stream(1, 2, 3, 4, 5).headOptionViaFoldRight)
    println(Stream().headOptionViaFoldRight)
  }
}